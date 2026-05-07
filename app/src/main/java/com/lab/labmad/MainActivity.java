package com.lab.labmad;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare View variables
    private EditText editName, editEmail, editPassword;
    private RadioGroup radioGroupGender;
    private Spinner spinnerCountry;
    private CheckBox checkTerms;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views mapping ID from XML
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        checkTerms = findViewById(R.id.checkTerms);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set Click Listener for Submit Button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmitForm();
            }
        });
    }

    private void validateAndSubmitForm() {
        // Retrieve input data as strings
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        // 1. Validate Full Name (Not Empty)
        if (TextUtils.isEmpty(name)) {
            editName.setError("Name is required");
            editName.requestFocus();
            return;
        }

        // 2. Validate Email (Not Empty & Valid Format)
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Valid Email is required");
            editEmail.requestFocus();
            return;
        }

        // 3. Validate Password (Length Check)
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editPassword.setError("Password must be at least 6 characters");
            editPassword.requestFocus();
            return;
        }

        // 4. Validate Gender Selection
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedGenderBtn = findViewById(selectedGenderId);
        String gender = selectedGenderBtn.getText().toString();

        // 5. Validate Country Selection (Spinner)
        int countryPosition = spinnerCountry.getSelectedItemPosition();
        if (countryPosition == 0) { // Index 0 represents the "Select Country" placeholder
            Toast.makeText(this, "Please select a country", Toast.LENGTH_SHORT).show();
            return;
        }
        String country = spinnerCountry.getSelectedItem().toString();

        // 6. Validate Terms Checkbox
        if (!checkTerms.isChecked()) {
            Toast.makeText(this, "You must agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        // 7. Success Output
        // If all validations pass, concatenate the collected data and display via Toast
        String successMessage = "Registration Successful!\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Gender: " + gender + "\n" +
                "Country: " + country;

        Toast.makeText(MainActivity.this, successMessage, Toast.LENGTH_LONG).show();
    }
}
