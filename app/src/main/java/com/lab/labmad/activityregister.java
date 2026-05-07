package com.lab.labmad;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class activityregister extends AppCompatActivity {

    // Move this to a class field and initialize it
    private final ActivityResultLauncher<String> uploadLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    // Update the UI to show a file was selected
                    TextView status = findViewById(R.id.tv_upload_status);
                    status.setText("Selected: " + uri.getLastPathSegment());
                    status.setTextColor(ContextCompat.getColor(this, R.color.blue));

                    Toast.makeText(this, "File ready for upload!", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Create your list of text
        String[] programs = {"ADPAI", "Computer Science", "Cyber Security", "Data Science"};

        // 2. Find the view
        AutoCompleteTextView dropDown = findViewById(R.id.dropdown_menu);

        // 3. Use the system's built-in layout (android.R.layout.simple_list_item_1)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                programs
        );

        // 4. Set the adapter
        dropDown.setAdapter(adapter);

        // 1. Find the RadioGroup
        RadioGroup genderGroup = findViewById(R.id.rg_gender);

        // 2. Get the ID of the selected Radio Button
        int selectedId = genderGroup.getCheckedRadioButtonId();

        // 3. Check if something was actually selected
        if (selectedId != -1) {
            // Find the specific button using the returned ID
            RadioButton selectedRadioButton = findViewById(selectedId);

            // Get the text (e.g., "Male")
            String selectedValue = selectedRadioButton.getText().toString();

            Toast.makeText(this, "Selected: " + selectedValue, Toast.LENGTH_SHORT).show();
        }

        // Set up the upload button listener
        findViewById(R.id.btn_upload_files).setOnClickListener(v -> {
            uploadLauncher.launch("*/*");
        });
    }
}
