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

public class AddPreOrder extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ImageView ivSelectedImage, btnBack;
    private Button btnUploadImage, btnAddPreOrder;
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
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        etStocks = findViewById(R.id.etStocks);

        btnBack.setOnClickListener(v -> finish());
        // Upload Image Button
        btnUploadImage.setOnClickListener(v -> openImageChooser());

        // Add Pre Order Button
        btnAddPreOrder.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(AddPreOrder.this, "Pre-order added successfully!", Toast.LENGTH_SHORT).show();
                // TODO: Save data to Firebase or Database
            }
        });
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