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
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddMyProduct extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivSelectedImage,btnBack;
    private Uri imageUri;
    private boolean isImageSelected = false; // Track image selection

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

        btnBack.setOnClickListener(v -> finish());

        // Upload Image Button Click
        btnUploadImage.setOnClickListener(v -> openImageChooser());

        // Add Product Button Click
        btnAddProduct.setOnClickListener(v -> validateAndAddProduct());
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

        if (plantName.isEmpty() || description.isEmpty() || price.isEmpty() || stocks.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isImageSelected) {
            Toast.makeText(this, "Please upload an image before adding the product", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with adding the product (e.g., upload to Firebase or database)
        Toast.makeText(this, "Product Added Successfully!", Toast.LENGTH_SHORT).show();
    }
}