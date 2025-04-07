package com.example.agrionion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER = "rememberMe";

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
        CheckBox checkBoxRememberMe = findViewById(R.id.checkBox);
        ImageView togglePassword = findViewById(R.id.ivTogglePassword);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load saved email if "Remember Me" was checked
        if (sharedPreferences.getBoolean(KEY_REMEMBER, false)) {
            etEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
            checkBoxRememberMe.setChecked(true);
        }

        checkBoxRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked) {
                editor.putString(KEY_EMAIL, etEmail.getText().toString());
                editor.putBoolean(KEY_REMEMBER, true);
            } else {
                editor.remove(KEY_EMAIL);
                editor.putBoolean(KEY_REMEMBER, false);
            }
            editor.apply();
        });

        togglePassword.setOnClickListener(v -> {
            if (etPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.eye_svgrepo_com);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.hide_close_eye_eye_svgrepo_com);
            }
            etPassword.setSelection(etPassword.length());
        });

        SignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });

        ForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this , CustomerHomepage.class);
            startActivity(intent);
            /*
            String email = etEmail.getText().toString().trim();
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

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (!jsonResponse.has("farmer_status")) {
                            Toast.makeText(MainActivity.this, "Error: Farmer status is missing!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String farmerStatus = jsonResponse.getString("farmer_status");
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        if ("1".equals(farmerStatus)) {
                            startActivity(new Intent(MainActivity.this, Homepage.class));
                        } else {
                            startActivity(new Intent(MainActivity.this, CustomerHomepage.class));
                        }
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "Error parsing server response", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Network error: " + error.toString(), Toast.LENGTH_LONG).show();
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