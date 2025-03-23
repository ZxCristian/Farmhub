package com.example.agrionion;

import android.app.DatePickerDialog;
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
import java.util.Calendar;

public class AddSchedule extends AppCompatActivity {

    private EditText etWaterDate, etInsecticideDate, etFertilizerDate, etHarvestDate, etPlantName, etPlantSeeds;
    private ImageView btnWaterCalendar, btnInsecticideCalendar, btnFertilizerCalendar, btnHarvestDate, btnBack;
    private Button btnSetSchedule, btnUploadImage;
    private ImageView ivSelectedImage;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri; // Store selected image URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        // Initialize EditTexts
        etWaterDate = findViewById(R.id.etWaterDate);
        etInsecticideDate = findViewById(R.id.etInsecticideDate);
        etFertilizerDate = findViewById(R.id.etFertilizerDate);
        etHarvestDate = findViewById(R.id.etHarvestDate);
        etPlantName = findViewById(R.id.etPlantName);
        etPlantSeeds = findViewById(R.id.etPlantSeeds);

        // Initialize Buttons
        btnWaterCalendar = findViewById(R.id.btnWaterCalendar);
        btnInsecticideCalendar = findViewById(R.id.btnInsecticideCalendar);
        btnFertilizerCalendar = findViewById(R.id.btnFertilizerCalendar);
        btnHarvestDate = findViewById(R.id.btnHarvestDate);
        btnBack = findViewById(R.id.btnBack);
        btnSetSchedule = findViewById(R.id.btnSetSchedule);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);

        // Set onClick listeners for date pickers
        btnWaterCalendar.setOnClickListener(v -> showDatePickerDialog(etWaterDate));
        btnInsecticideCalendar.setOnClickListener(v -> showDatePickerDialog(etInsecticideDate));
        btnFertilizerCalendar.setOnClickListener(v -> showDatePickerDialog(etFertilizerDate));
        btnHarvestDate.setOnClickListener(v -> showDatePickerDialog(etHarvestDate));

        // Back button functionality
        btnBack.setOnClickListener(v -> finish());

        // Handle Set Schedule button click
        btnSetSchedule.setOnClickListener(v -> {
            String waterDate = etWaterDate.getText().toString().trim();
            String insecticideDate = etInsecticideDate.getText().toString().trim();
            String fertilizerDate = etFertilizerDate.getText().toString().trim();
            String harvestDate = etHarvestDate.getText().toString().trim();
            String plantName = etPlantName.getText().toString().trim();
            String plantSeeds = etPlantSeeds.getText().toString().trim();

            // Perform validation
            if (plantName.isEmpty() || plantSeeds.isEmpty() || waterDate.isEmpty() ||
                    insecticideDate.isEmpty() || fertilizerDate.isEmpty() || harvestDate.isEmpty()) {
                Toast.makeText(AddSchedule.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (imageUri == null) {  // Check if an image has been selected
                Toast.makeText(AddSchedule.this, "Please upload an image", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddSchedule.this, "Schedule Set Successfully", Toast.LENGTH_SHORT).show();
                // Add logic to save data to the database or process further
            }
        });


        // Handle Image Upload
        btnUploadImage.setOnClickListener(v -> openImageChooser());
    }

    // Method to show DatePickerDialog
    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = year1 + "-" + String.format("%02d", month1 + 1) + "-" + String.format("%02d", dayOfMonth);
            editText.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    // Open image chooser
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // Handle result of image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
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
}
