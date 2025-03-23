package com.example.agrionion;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find UI elements
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView SignUp = findViewById(R.id.SignUp);
        TextView ForgotPassword = findViewById(R.id.ForgotPassword);
        ProgressBar progressBar = findViewById(R.id.progressBar);


        ImageView togglePassword = findViewById(R.id.ivTogglePassword);

        togglePassword.setOnClickListener(v -> {
            if (etPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.eye_svgrepo_com); // Replace with your open-eye icon
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.hide_close_eye_eye_svgrepo_com); // Replace with your closed-eye icon
            }
            etPassword.setSelection(etPassword.length()); // Keeps cursor at the end
        });


        SignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);

        });

        ForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
            startActivity(intent);

        });


        // Handle Login Button Click
        btnLogin.setOnClickListener(v -> {

                    Intent intent = new Intent(MainActivity.this, Homepage.class);
                    startActivity(intent);

            /*String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Email is required");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Password is required");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);

            loginUser(email, password, progressBar, btnLogin);

             */
        });


    }

    private void loginUser(String email, String password, ProgressBar progressBar, Button btnLogin) {
        String url = "http://10.0.2.2:8000/login";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                    Log.d("LoginResponse", "Server Response: " + response); // Debugging line

                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        // Check if farmer_status exists in response
                        if (!jsonResponse.has("farmer_status")) {
                            Toast.makeText(MainActivity.this, "Error: Farmer status is missing!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String farmerStatus = jsonResponse.getString("farmer_status");
                        Log.d("LoginSuccess", "Farmer Status: " + farmerStatus);

                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        if ("1".equals(farmerStatus)) {
                            startActivity(new Intent(MainActivity.this, Homepage.class));
                            finish();
                        } else {
                            startActivity(new Intent(MainActivity.this, customerHomepage.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        Log.e("LoginError", "JSON Parsing error: " + e.getMessage());
                        Toast.makeText(MainActivity.this, "Error parsing server response", Toast.LENGTH_LONG).show();
                    }


    },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String errorMsg = new String(error.networkResponse.data);
                        Log.e("VolleyError", "Error " + statusCode + ": " + errorMsg);
                        Toast.makeText(MainActivity.this, "Error " + statusCode + ": " + errorMsg, Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("VolleyError", "Network error: " + error.toString());
                        Toast.makeText(MainActivity.this, "Network error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        queue.add(request);



    }

}
