package com.example.stylish;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotActivity extends AppCompatActivity {

    private TextInputLayout emailLayout;
    private TextInputEditText etEmail;
    private MaterialButton btnSubmit;
    private TextView tvInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot);

        // --- Initialization ---
        emailLayout = findViewById(R.id.emailLayout);
        etEmail = findViewById(R.id.etEmail);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvInstruction = findViewById(R.id.tvInstruction);

        // --- Window Insets (Padding logic) ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int customPadding = (int) (24 * getResources().getDisplayMetrics().density);
            v.setPadding(systemBars.left + customPadding, systemBars.top + customPadding, systemBars.right + customPadding, systemBars.bottom + customPadding);
            return insets;
        });

        // --- Instruction Text with Red Asterisk (*) ---
        String instruction = "<font color='#F83758'>*</font> We will send you a message to set or reset your new password";
        tvInstruction.setText(Html.fromHtml(instruction));

        etEmail.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailLayout.setHint("Enter your email address");
                emailLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#676767")));
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        // --- Submit Button Click ---
        btnSubmit.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                emailLayout.setHint("Email is required!");
                emailLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                etEmail.requestFocus();
            } else {
                // ============================================================
                // DATABASE / FIREBASE CODE STARTS HERE
                // ------------------------------------------------------------
                // Tumi ekhane Firebase password reset email-er code likhbe.
                // ============================================================
                Toast.makeText(this, "Reset Link Sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}