    package com.example.agrionion;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;

    import java.util.HashMap;
    import java.util.Map;

    public class SignUp extends AppCompatActivity {

        EditText etFullName, etEmail, etAddress, etAge, etPassword, etConfirmPassword;
        ImageView ivTogglePassword, ivToggleConfirmPassword;
        Button btnSignUp;
        ProgressBar progressBar;
        RadioGroup rgFarmer;

        String URL = "http://10.0.2.2:8000/register";

        private boolean isPasswordVisible = false;
        private boolean isConfirmPasswordVisible = false;
        private String farmerStatus = ""; // To store the selected option

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

            // Initialize UI elements
            etFullName = findViewById(R.id.etFullName);
            etEmail = findViewById(R.id.etEmail);
            etAddress = findViewById(R.id.etAddress);
            etAge = findViewById(R.id.etAge);
            etPassword = findViewById(R.id.etPassword);
            etConfirmPassword = findViewById(R.id.etConfirmPassword);
            ivTogglePassword = findViewById(R.id.ivTogglePassword);
            ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);
            btnSignUp = findViewById(R.id.btnSignUp);
            progressBar = findViewById(R.id.progressBar);
            rgFarmer = findViewById(R.id.rgFarmer);

            // Toggle Password Visibility
            setupPasswordToggle(etPassword, ivTogglePassword, true);
            setupPasswordToggle(etConfirmPassword, ivToggleConfirmPassword, false);

            // Handle Sign Up button click
            btnSignUp.setOnClickListener(v -> {
                String fullName = etFullName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String age = etAge.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // Get selected RadioButton value
                // Get selected RadioButton value
                int selectedId = rgFarmer.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(SignUp.this, "Please select if you are a farmer!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedText = selectedRadioButton.getText().toString().toLowerCase();
                    farmerStatus = selectedText.equals("yes") ? "1" : "0"; // Convert to 1 or 0
                }


                // Validate inputs
                if (!validateInputs(fullName, email, address, age, password, confirmPassword)) {
                    return;
                }

                // Show progress bar and disable button during registration
                progressBar.setVisibility(View.VISIBLE);
                btnSignUp.setEnabled(false);

                new android.os.Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnSignUp.setEnabled(true);
                }, 3000); // Simulated 3-second loading

                // Register user
                registerUser(fullName, email, address, age, password, farmerStatus);
            });
        }

        // ✅ Toggle Password Visibility
        private void setupPasswordToggle(EditText editText, ImageView toggleButton, boolean isPasswordField) {
            toggleButton.setOnClickListener(v -> {
                if (isPasswordField) {
                    isPasswordVisible = !isPasswordVisible;
                    updatePasswordVisibility(editText, toggleButton, isPasswordVisible);
                } else {
                    isConfirmPasswordVisible = !isConfirmPasswordVisible;
                    updatePasswordVisibility(editText, toggleButton, isConfirmPasswordVisible);
                }
            });
        }

        private void updatePasswordVisibility(EditText editText, ImageView toggleButton, boolean isVisible) {
            if (isVisible) {
                editText.setTransformationMethod(null);
                toggleButton.setImageResource(R.drawable.eye_svgrepo_com); // Visible eye icon
            } else {
                editText.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
                toggleButton.setImageResource(R.drawable.hide_close_eye_eye_svgrepo_com); // Hidden eye icon
            }
            editText.setSelection(editText.getText().length()); // Keep cursor at the end
        }

        // ✅ Input Validation
        private boolean validateInputs(String fullName, String email, String address, String age, String password, String confirmPassword) {
            if (fullName.isEmpty() || email.isEmpty() || address.isEmpty() || age.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!age.matches("\\d+")) {
                Toast.makeText(this, "Age must be a number!", Toast.LENGTH_SHORT).show();
                return false;
            }
            int ageInt = Integer.parseInt(age);
            if (ageInt < 18 || ageInt > 100) {
                Toast.makeText(this, "Age must be between 18 and 100!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }

        // ✅ Register User with Lumen API

        private void registerUser(String fullName, String email, String address, String age, String password, String farmerStatus) {
            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest request = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        Toast.makeText(SignUp.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, MainActivity.class));
                        finish();
                    },
                    error -> {
                        // Proper error handling
                        String errorMessage = "Registration Failed! ";
                        if (error.networkResponse != null) {
                            errorMessage += "Error Code: " + error.networkResponse.statusCode + "\n";
                            errorMessage += "Response: " + new String(error.networkResponse.data);
                        } else {
                            errorMessage += "Check your internet connection.";
                        }

                        // Show error message in a Toast
                        Toast.makeText(SignUp.this, errorMessage, Toast.LENGTH_LONG).show();

                        // Log the error for debugging
                        android.util.Log.e("Volley Error", errorMessage);
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("full_name", fullName);
                    params.put("email", email);
                    params.put("address", address);
                    params.put("age", age);
                    params.put("password", password);
                    params.put("farmer_status", farmerStatus); // Add farmer status
                    return params;
                }
            };

            queue.add(request);
        }


    }
