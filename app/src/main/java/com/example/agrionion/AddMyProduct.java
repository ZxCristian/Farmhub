package com.example.agrionion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddMyProduct extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivSelectedImage, btnBack;
    private Uri imageUri;
    private boolean isImageSelected = false; // Track image selection

    private TextView tvProductId; // TextView for auto-generated Product ID
    private EditText etPlantName, etDescription, etPrice, etStocks;
    private Button btnUploadImage, btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_product); // Ensure this matches your XML file name

        btnBack = findViewById(R.id.btnBack);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        etPlantName = findViewById(R.id.etPlantName);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etStocks = findViewById(R.id.etStocks);
        tvProductId = findViewById(R.id.tvProductId); // Initialize Product ID TextView

        // Generate and set a 15-digit Product ID
        String generatedId = generateProductId();
        tvProductId.setText(generatedId); // Display only the 15-digit ID

        btnBack.setOnClickListener(v -> finish());

        // Upload Image Button Click
        btnUploadImage.setOnClickListener(v -> openImageChooser());

        // Add Product Button Click
        btnAddProduct.setOnClickListener(v -> validateAndAddProduct());
    }

    // Method to generate a 15-digit Product ID
    private String generateProductId() {
        // Get current timestamp (13 digits as of 2025)
        String timestamp = String.valueOf(System.currentTimeMillis());

        // Add a 2-digit prefix (e.g., "10") to make it 15 digits
        String prefix = "10"; // You can change this to any 2-digit number
        String baseId = prefix + timestamp; // Combines to 15 digits

        // Ensure it's exactly 15 digits
        if (baseId.length() > 15) {
            baseId = baseId.substring(0, 15); // Truncate if longer
        } else if (baseId.length() < 15) {
            // Pad with leading zeros if shorter (unlikely with this method)
            baseId = String.format("%015d", Long.parseLong(baseId));
        }

        return baseId;
    }

    // Open Gallery to Pick Image
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivSelectedImage.setImageBitmap(bitmap);
                ivSelectedImage.setVisibility(View.VISIBLE);
                isImageSelected = true; // Image is selected
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Validate Fields Before Adding Product
    private void validateAndAddProduct() {
        String plantName = etPlantName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String stocks = etStocks.getText().toString().trim();
        String productId = tvProductId.getText().toString().trim(); // Get the 15-digit ID directly

        // Check if any editable field is empty (Product ID is auto-generated)
        if (plantName.isEmpty() || description.isEmpty() || price.isEmpty() || stocks.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isImageSelected) {
            Toast.makeText(this, "Please upload an image before adding the product", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with adding the product (e.g., upload to Firebase or database)
        Toast.makeText(this, "Product Added Successfully! Product ID: " + productId, Toast.LENGTH_SHORT).show();
        finish();
    }
}