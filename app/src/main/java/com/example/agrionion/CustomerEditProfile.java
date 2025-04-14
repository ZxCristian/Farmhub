package com.example.agrionion;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;

public class CustomerEditProfile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 100;

    private ImageView btnBack;
    private ShapeableImageView imgProfile;
    private EditText etFullName;
    private EditText etUsername;
    private EditText etAddress;
    private EditText etContact;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private ImageView ivEyePassword;
    private ImageView ivEyeConfirmPassword;
    private Button btnConfirm;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_edit_profile);

        // Initialize views
        initializeViews();

        // Set click listeners
        setupClickListeners();

        // Load existing user data
        loadUserData();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        imgProfile = findViewById(R.id.imgProfile);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        ivEyePassword = findViewById(R.id.ivEyePassword);
        ivEyeConfirmPassword = findViewById(R.id.ivEyeConfirmPassword);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        imgProfile.setOnClickListener(v -> checkStoragePermission());

        ivEyePassword.setOnClickListener(v -> togglePasswordVisibility());

        ivEyeConfirmPassword.setOnClickListener(v -> toggleConfirmPasswordVisibility());

        btnConfirm.setOnClickListener(v -> saveProfileChanges());
    }

    private void loadUserData() {
        // TODO: Replace with actual user data retrieval
        etFullName.setText("Cristian D. Zarate");
        etUsername.setText("cristian_z");
        etAddress.setText("123 Farm Road, Countryside");
        etContact.setText("1234567890");
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        if (isPasswordVisible) {
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivEyePassword.setImageResource(R.drawable.eye_svgrepo_com);
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivEyePassword.setImageResource(R.drawable.hide_close_eye_eye_svgrepo_com);
        }
        etPassword.setSelection(etPassword.getText().length());
    }

    private void toggleConfirmPasswordVisibility() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        if (isConfirmPasswordVisible) {
            etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivEyeConfirmPassword.setImageResource(R.drawable.eye_svgrepo_com);
        } else {
            etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivEyeConfirmPassword.setImageResource(R.drawable.hide_close_eye_eye_svgrepo_com);
        }
        etConfirmPassword.setSelection(etConfirmPassword.getText().length());
    }

    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        STORAGE_PERMISSION_CODE);
            } else {
                openImagePicker();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE);
            } else {
                openImagePicker();
            }
        }
    }

    private void openImagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
    }

    private void saveProfileChanges() {
        String fullName = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Enhanced validation
        if (fullName.isEmpty() || username.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate contact number (basic 10-digit check)
        if (!contact.matches("\\d{10}")) {
            Toast.makeText(this, "Please enter a valid 10-digit contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Password validation
        if (!password.isEmpty()) {
            if (password.length() < 8) {
                Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // TODO: Implement actual profile update logic
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                                Manifest.permission.READ_MEDIA_IMAGES :
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Storage permission is needed to select a profile image", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Storage permission denied. You can enable it in app settings.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                // Use Glide or Picasso for better image handling
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Scale down bitmap to prevent memory issues
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                imgProfile.setImageBitmap(scaledBitmap);
                bitmap.recycle(); // Free original bitmap
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}