package com.example.agrionion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titleText = findViewById(R.id.detailTitle);
        TextView messageText = findViewById(R.id.detailMessage);
        TextView timestampText = findViewById(R.id.detailTimestamp);
        Button pdfButton = findViewById(R.id.pdfButton);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        String timestamp = intent.getStringExtra("timestamp");
        String pdfUrl = intent.getStringExtra("pdfUrl");

        titleText.setText(title);
        messageText.setText(message); // Full message displayed here
        timestampText.setText(timestamp);

        if (pdfUrl != null && !pdfUrl.isEmpty()) {
            pdfButton.setVisibility(Button.VISIBLE);
            pdfButton.setOnClickListener(v -> {
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                startActivity(pdfIntent);
            });
        } else {
            pdfButton.setVisibility(Button.GONE);
        }
    }
}