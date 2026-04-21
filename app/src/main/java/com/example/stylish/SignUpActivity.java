package com.example.stylish;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.ImageView;
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

public class SignUpActivity extends AppCompatActivity {

    // --- Variable Declaration ---
    private TextInputLayout nameLayout, emailLayout, passLayout, confirmPassLayout;
    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private MaterialButton btnCreateAccount;
    private TextView tvLogin, tvPolicy;
    private ImageView ivGoogle, ivApple, ivFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // --- View Initialization ---
        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passLayout = findViewById(R.id.passLayout);
        confirmPassLayout = findViewById(R.id.confirmPassLayout);

        etName = findViewById(R.id.etName); // Full Name field initialize
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvLogin = findViewById(R.id.tvLogin);
        tvPolicy = findViewById(R.id.tvPolicy);

        ivGoogle = findViewById(R.id.ivGoogle);
        ivApple = findViewById(R.id.ivApple);
        ivFacebook = findViewById(R.id.ivFacebook);

        // --- Window Insets (Padding logic) ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int customPadding = (int) (24 * getResources().getDisplayMetrics().density);
            v.setPadding(
                    systemBars.left + customPadding,
                    systemBars.top + customPadding,
                    systemBars.right + customPadding,
                    systemBars.bottom + customPadding
            );
            return insets;
        });

        // --- Policy Text Styling (Register in Pink) ---
        String text = "By clicking the <font color='#F83758'>Register</font> button, you agree<br>to the public offer";
        tvPolicy.setText(Html.fromHtml(text));

        // --- Create Account Button Click ---
        btnCreateAccount.setOnClickListener(v -> {
            validateInputs();
        });

        // --- Login link click ---
        tvLogin.setOnClickListener(v -> {
            finish();
        });

        // --- TextWatchers ---
        setupTextWatchers();
    }

    private void validateInputs() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String confirmPass = etConfirmPassword.getText().toString().trim();

        // Error reset
        resetError(nameLayout, "Full Name");
        resetError(emailLayout, "Enter your email");
        resetError(passLayout, "Password");
        resetError(confirmPassLayout, "Confirm Password");

        // --- Validation Logic ---
        if (name.isEmpty()) {
            setErrorStyle(nameLayout, "Name is required!");
        } else if (email.isEmpty()) {
            setErrorStyle(emailLayout, "Email is required!");
        } else if (pass.isEmpty()) {
            setErrorStyle(passLayout, "Password is required!");
        } else if (pass.length() < 6) {
            setErrorStyle(passLayout, "Password must be 6+ characters");
        } else if (!pass.equals(confirmPass)) {
            setErrorStyle(confirmPassLayout, "Passwords do not match!");
        } else {
            // ============================================================
            // DATABASE / SERVER CODE STARTS HERE
            // ============================================================
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setErrorStyle(TextInputLayout layout, String errorMsg) {
        layout.setHint(errorMsg);
        layout.setHintTextColor(ColorStateList.valueOf(Color.RED));
        layout.requestFocus();
    }

    private void resetError(TextInputLayout layout, String originalHint) {
        layout.setHint(originalHint);
        layout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#676767")));
    }

    private void setupTextWatchers() {
        etName.addTextChangedListener(new SimpleTextWatcher(nameLayout, "Enter your name"));
        etEmail.addTextChangedListener(new SimpleTextWatcher(emailLayout, "Enter your email"));
        etPassword.addTextChangedListener(new SimpleTextWatcher(passLayout, "Password"));
        etConfirmPassword.addTextChangedListener(new SimpleTextWatcher(confirmPassLayout, "Confirm Password"));
    }

    private class SimpleTextWatcher implements TextWatcher {
        private TextInputLayout layout;
        private String hint;

        SimpleTextWatcher(TextInputLayout layout, String hint) {
            this.layout = layout;
            this.hint = hint;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            resetError(layout, hint);
        }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    }
}