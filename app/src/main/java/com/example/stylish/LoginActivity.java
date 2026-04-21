package com.example.stylish;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private TextInputLayout emailLayout, passLayout;
    private MaterialButton btnLogin;
    private TextView tvForgot, tvSignUp;
    private ImageView ivGoogle, ivApple, ivFacebook;

    // --- Tomar LoginHelper declare kora ---
    private LoginHelper loginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- Session Check (Login thakle auto MainActivity-te jabe) ---
        loginHelper = new LoginHelper(this);
        if (loginHelper.isLoggedIn()) {
            goToHome();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // View Initialization
        emailLayout = findViewById(R.id.emailLayout);
        passLayout = findViewById(R.id.passLayout);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgot = findViewById(R.id.tvForgot);
        tvSignUp = findViewById(R.id.tvSignUp);
        ivGoogle = findViewById(R.id.ivGoogle);
        ivApple = findViewById(R.id.ivApple);
        ivFacebook = findViewById(R.id.ivFacebook);

        // Window Padding Logic
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int customPadding = (int) (24 * getResources().getDisplayMetrics().density);
            v.setPadding(systemBars.left + customPadding, systemBars.top + customPadding, systemBars.right + customPadding, systemBars.bottom + customPadding);
            return insets;
        });

        // --- Login Button Click Logic ---
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty()) {
                showError(emailLayout, "Email is required!");
            } else if (password.isEmpty()) {
                showError(passLayout, "Password is required!");
            } else if (password.length() < 6) {
                showError(passLayout, "Must be 6+ characters");
            } else {
                // --- Dummy Validation with LoginHelper ---
                if (loginHelper.validateDummyLogin(email, password)) {
                    loginHelper.setLoggedIn(true); // Session save holo
                    Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                    goToHome();
                } else {
                    Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    // Error color reset
                    emailLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                    passLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                }
            }
        });

        // TextWatchers (Type korle jeno error chole jay)
        setupTextWatchers();

        // Navigations
        tvForgot.setOnClickListener(v -> startActivity(new Intent(this, ForgotActivity.class)));
        tvSignUp.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));

        // Social Toasts
        ivGoogle.setOnClickListener(v -> Toast.makeText(this, "Google Login Clicked", Toast.LENGTH_SHORT).show());
        ivApple.setOnClickListener(v -> Toast.makeText(this, "Apple Login Clicked", Toast.LENGTH_SHORT).show());
        ivFacebook.setOnClickListener(v -> Toast.makeText(this, "Facebook Login Clicked", Toast.LENGTH_SHORT).show());
    }

    private void showError(TextInputLayout layout, String errorMsg) {
        layout.setHint(errorMsg);
        layout.setHintTextColor(ColorStateList.valueOf(Color.RED));
        layout.requestFocus();
    }

    private void setupTextWatchers() {
        etEmail.addTextChangedListener(new SimpleWatcher(emailLayout, "Username or Email"));
        etPassword.addTextChangedListener(new SimpleWatcher(passLayout, "Password"));
    }

    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // User back korle jeno abar login screen-e na ashe
    }

    // Custom TextWatcher Inner Class (Code clean rakhar jonno)
    private class SimpleWatcher implements TextWatcher {
        private TextInputLayout layout;
        private String hint;

        SimpleWatcher(TextInputLayout layout, String hint) {
            this.layout = layout;
            this.hint = hint;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            layout.setHint(hint);
            layout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#676767")));
        }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(Editable s) {}
    }
}