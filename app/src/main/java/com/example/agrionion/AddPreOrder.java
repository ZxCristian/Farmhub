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

public class AddPreOrder extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ImageView ivSelectedImage, btnBack;
    private Button btnUploadImage, btnAddPreOrder;
    private TextView tvProductId;
    private EditText etPlantName, etDescription, etPrice, etStocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pre_order);

        btnBack = findViewById(R.id.btnBack);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnAddPreOrder = findViewById(R.id.btnAddPreOrder);
        etPlantName = findViewById(R.id.etPlantName);
        tvProductId = findViewById(R.id.tvProductId);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etStocks = findViewById(R.id.etStocks);

        // Auto-generate 15-digit Product ID
        String autoGeneratedProductId = generate15DigitProductId();
        tvProductId.setText(autoGeneratedProductId);
        tvProductId.setEnabled(false); // Make it read-only

        btnBack.setOnClickListener(v -> finish());

        // Upload Image Button
        btnUploadImage.setOnClickListener(v -> openImageChooser());

        // Add Pre Order Button
        btnAddPreOrder.setOnClickListener(v -> {
            if (validateInputs()) {
                String plantName = etPlantName.getText().toString().trim();
                String productId = tvProductId.getText().toString().trim(); // Auto-generated 15-digit value
                String description = etDescription.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                String stocks = etStocks.getText().toString().trim();

                Toast.makeText(AddPreOrder.this, "Pre-order added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // Method to generate a 15-digit Product ID
    private String generate15DigitProductId() {
        // Get current timestamp (typically 13 digits)
        String timestamp = String.valueOf(System.currentTimeMillis());
        // Pad with zeros or append a prefix to make it 15 digits
        String prefix = "10"; // Adds 2 digits to make it 15 (13 + 2)
        String productId = prefix + timestamp;

        // Ensure it’s exactly 15 digits (in case timestamp length varies in the future)
        if (productId.length() > 15) {
            productId = productId.substring(0, 15);
        } else if (productId.length() < 15) {
            productId = String.format("%15s", productId).replace(' ', '0'); // Pad with leading zeros
        }

        return productId;
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInputs() {
        if (etPlantName.getText().toString().trim().isEmpty()) {
            etPlantName.setError("Plant name is required");
            return false;
        }
        if (etDescription.getText().toString().trim().isEmpty()) {
            etDescription.setError("Description is required");
            return false;
        }
        if (etPrice.getText().toString().trim().isEmpty()) {
            etPrice.setError("Price is required");
            return false;
        }
        if (etStocks.getText().toString().trim().isEmpty()) {
            etStocks.setError("Available stocks are required");
            return false;
        }
        if (imageUri == null) {
            Toast.makeText(this, "Please upload an image!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}