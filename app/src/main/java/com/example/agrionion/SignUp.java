    package com.example.agrionion;

    import android.app.DatePickerDialog;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Spinner;
    import android.widget.Toast;
    import androidx.appcompat.app.AppCompatActivity;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import java.util.Calendar;
    import java.util.HashMap;
    import java.util.Map;

    public class SignUp extends AppCompatActivity {
        EditText etFirstName,etLastName,etAge,etBirthday,etEmail,etAddress,etCity,etState,etContact,etFAddress,etFCity,etFState , etPassword, etConfirmPassword;
        Spinner spinnerGender;
        ImageView ivTogglePassword, ivToggleConfirmPassword;
        Button btnSignUp;
        ProgressBar progressBar;
        RadioGroup rgFarmer;

        private static final int PICK_IMAGE_REQUEST = 1;
        private Uri imageUri;
        private ImageView imgProfile;
        String URL = "http://10.0.2.2:8000/register";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);




            imgProfile = findViewById(R.id.imgProfile);
            imgProfile.setOnClickListener(v -> openImagePicker());


            // Initialize UI
            etFirstName = findViewById(R.id.etFirstName);
            etLastName = findViewById(R.id.etLastname);
            spinnerGender = findViewById(R.id.spinnerGender);
            etAge = findViewById(R.id.etAge);
            etBirthday  = findViewById(R.id.etBirthday);
            etEmail = findViewById(R.id.etEmail);
            etContact = findViewById(R.id.etContact);
            etAddress = findViewById(R.id.etAddress);
            etCity = findViewById(R.id.etCity);
            etState = findViewById(R.id.etState);
            etFAddress =findViewById(R.id.etFAddress);
            etFCity = findViewById(R.id.etFCity);
            etFState = findViewById(R.id.etFState);
            etPassword = findViewById(R.id.etPassword);
            etConfirmPassword = findViewById(R.id.etConfirmPassword);
            ivTogglePassword = findViewById(R.id.ivTogglePassword);
            ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);
            btnSignUp = findViewById(R.id.btnSignUp);
            progressBar = findViewById(R.id.progressBar);
            rgFarmer = findViewById(R.id.rgFarmer);

            // Toggle Password Visibility
            setupPasswordToggle(etPassword, ivTogglePassword);
            setupPasswordToggle(etConfirmPassword, ivToggleConfirmPassword);


            etBirthday.setOnClickListener(v -> showDatePickerDialog());
        }


        private void openImagePicker() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();  // Get the selected image URI
                imgProfile.setImageURI(imageUri);  // Set the image in ImageView
            }
        }

        private void showDatePickerDialog() {
            // Get the current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create a DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Format the selected date and set it to etBirthday
                        String formattedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        etBirthday.setText(formattedDate);
                    },
                    year, month, day
            );

            // Show the DatePickerDialog
            datePickerDialog.show();


            // Handle Sign Up
            btnSignUp.setOnClickListener(v -> attemptSignUp());
        }

        private void attemptSignUp() {
            String firstname = etFirstName.getText().toString().trim();
            String lastname = etLastName.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();
            String age = etAge.getText().toString().trim();
            String birthday = etBirthday.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String contact =etContact.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String city = etCity.getText().toString().trim();
            String state = etState.getText().toString().trim();
            String farmaddress = etFAddress.getText().toString().trim();
            String farmcity = etFCity.getText().toString().trim();
            String farmstate = etFState.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // Validate radio button selection
            int selectedId = rgFarmer.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select if you are a farmer!", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedRadioButton = findViewById(selectedId);
            String farmerStatus = selectedRadioButton.getText().toString().equalsIgnoreCase("yes") ? "1" : "0";

            // Validate inputs
            if (!validateInputs(firstname,lastname,gender,age,birthday,email,contact,address,city,state,farmaddress,farmcity,farmstate, password, confirmPassword)) {
                return;
            }

            // Show loading
            progressBar.setVisibility(View.VISIBLE);
            btnSignUp.setEnabled(false);

            // Register user
            registerUser(firstname,lastname,gender,age,birthday,email,contact,address,city,state,farmaddress,farmcity,farmstate, password, farmerStatus);
        }




        private void registerUser(String firstname, String lastname, String gender, String age, String birthday, String email,
                                  String contact, String address, String city, String state, String farmaddress,
                                  String farmcity, String farmstate, String password, String farmerStatus) {

            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest request = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        progressBar.setVisibility(View.GONE);
                        btnSignUp.setEnabled(true);
                        Toast.makeText(SignUp.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, MainActivity.class));
                        finish();
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        btnSignUp.setEnabled(true);

                        String errorMessage = "Registration Failed! ";
                        if (error.networkResponse != null) {
                            errorMessage += "Error Code: " + error.networkResponse.statusCode + "\n";
                            errorMessage += "Response: " + new String(error.networkResponse.data);
                        } else {
                            errorMessage += "Check your internet connection.";
                        }

                        Toast.makeText(SignUp.this, errorMessage, Toast.LENGTH_LONG).show();
                    }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("firstname", firstname);
                    params.put("lastname", lastname);
                    params.put("gender", gender);
                    params.put("age", age);
                    params.put("birthday", birthday);
                    params.put("email", email);
                    params.put("contact", contact);
                    params.put("address", address);
                    params.put("city", city);
                    params.put("state", state);
                    params.put("farmaddress", farmaddress);
                    params.put("farmcity", farmcity);
                    params.put("farmstate", farmstate);
                    params.put("password", password); // Consider hashing it before sending
                    params.put("farmer_status", farmerStatus);
                    return params;
                }
            };

            queue.add(request);
        }


        private boolean validateInputs(String firstname, String lastname, String gender, String age, String birthday,
                                       String email, String contact, String address, String city, String state,
                                       String farmaddress, String farmcity, String farmstate, String password,
                                       String confirmPassword) {

            // Trim inputs to remove extra spaces
            firstname = firstname.trim();
            lastname = lastname.trim();
            gender = gender.trim();
            age = age.trim();
            birthday = birthday.trim();
            email = email.trim();
            contact = contact.trim();
            address = address.trim();
            city = city.trim();
            state = state.trim();
            farmaddress = farmaddress.trim();
            farmcity = farmcity.trim();
            farmstate = farmstate.trim();
            password = password.trim();
            confirmPassword = confirmPassword.trim();

            // Check for empty fields
            if (firstname.isEmpty() || lastname.isEmpty() || gender.isEmpty() || age.isEmpty() || birthday.isEmpty() ||
                    email.isEmpty() || contact.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() ||
                    farmaddress.isEmpty() || farmcity.isEmpty() || farmstate.isEmpty() || password.isEmpty() ||
                    confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Validate email format
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format!", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Validate age (must be a number and within range)
            if (!age.matches("\\d+")) {
                Toast.makeText(this, "Age must be a valid number!", Toast.LENGTH_SHORT).show();
                return false;
            }
            int ageInt = Integer.parseInt(age);
            if (ageInt < 18 || ageInt > 100) {
                Toast.makeText(this, "Age must be between 18 and 100!", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Validate contact number (must be numeric and 10-15 digits)
            if (!contact.matches("\\d{10,15}")) {
                Toast.makeText(this, "Invalid contact number! It should be 10 to 15 digits long.", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Validate password (minimum 6 characters)
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true; // All validations passed
        }


        private void setupPasswordToggle(EditText editText, ImageView toggleButton) {
            toggleButton.setOnClickListener(v -> {
                boolean isVisible = editText.getTransformationMethod() == null;
                editText.setTransformationMethod(isVisible ? new android.text.method.PasswordTransformationMethod() : null);
                toggleButton.setImageResource(isVisible ? R.drawable.hide_close_eye_eye_svgrepo_com : R.drawable.eye_svgrepo_com);
                editText.setSelection(editText.getText().length());
            });
        }
    }
