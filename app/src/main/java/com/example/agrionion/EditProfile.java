package com.example.agrionion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import com.google.android.material.imageview.ShapeableImageView;

public class EditProfile extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etPassword, etConfirmPassword;
    private ImageView ivTogglePassword, ivToggleConfirmPassword, btnBack;
    private ShapeableImageView imgProfile;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);  // ✅ Move this line to the top

        btnBack = findViewById(R.id.btnBack);
        imgProfile = findViewById(R.id.imgProfile);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        ivTogglePassword = findViewById(R.id.ivEyePassword);
        ivToggleConfirmPassword = findViewById(R.id.ivEyeConfirmPassword);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        btnBack.setOnClickListener(v -> finish());

        ivTogglePassword.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            etPassword.setInputType(isPasswordVisible
                    ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivTogglePassword.setImageResource(isPasswordVisible
                    ? R.drawable.eye_svgrepo_com
                    : R.drawable.hide_close_eye_eye_svgrepo_com);
            etPassword.setSelection(etPassword.getText().length());
        });

        ivToggleConfirmPassword.setOnClickListener(v -> {
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            etConfirmPassword.setInputType(isConfirmPasswordVisible
                    ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivToggleConfirmPassword.setImageResource(isConfirmPasswordVisible
                    ? R.drawable.eye_svgrepo_com
                    : R.drawable.hide_close_eye_eye_svgrepo_com);
            etConfirmPassword.setSelection(etConfirmPassword.getText().length());
        });

        btnConfirm.setOnClickListener(v -> {
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
        });

        imgProfile.setOnClickListener(v -> showImagePickerDialog());
    }

    private void showImagePickerDialog() {
        String[] options = {"Choose from Gallery", "Cancel"};

        new AlertDialog.Builder(this)
                .setTitle("Select Image")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) openGallery();
                    else dialog.dismiss();
                })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
