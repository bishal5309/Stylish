package com.example.stylish;

import android.content.Intent;
import android.os.Bundle;
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

    // Member Variables Declare
    private TextInputEditText etEmail, etPassword;
    private TextInputLayout emailLayout, passLayout;
    private MaterialButton btnLogin;
    private TextView tvForgot, tvSignUp;
    private ImageView ivGoogle, ivApple, ivFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // --- View Initialization ---
        // Input layouts (Error dekhabor jonno)
        emailLayout = findViewById(R.id.emailLayout);
        passLayout = findViewById(R.id.passLayout);

        // Edit Texts (Data ber korar jonno)
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Buttons & TextViews
        btnLogin = findViewById(R.id.btnLogin);
        tvForgot = findViewById(R.id.tvForgot);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Social Icons
        ivGoogle = findViewById(R.id.ivGoogle);
        ivApple = findViewById(R.id.ivApple);
        ivFacebook = findViewById(R.id.ivFacebook);

        // --- Window Insets (System bar padding logic) ---
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

        // --- Click Listeners ---

        // Login Button logic with Validation
        // Login Button logic with Validation
        // --- onCreate-er bhetore login logic ---

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Resetting hints and colors to original (Ager moto normal kora)
            emailLayout.setHint("Username or Email");
            passLayout.setHint("Password");

            // Normal color (Greyish) set kora
            int normalColor = android.graphics.Color.parseColor("#A8A8A8");
            emailLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(normalColor));
            passLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(normalColor));

            // Error color (Red)
            int errorColor = android.graphics.Color.RED;

            if (email.isEmpty()) {
                emailLayout.setHint("Email is required!");
                emailLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(errorColor));
                emailLayout.requestFocus();
            } else if (password.isEmpty()) {
                passLayout.setHint("Password is required!");
                passLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(errorColor));
                passLayout.requestFocus();
            } else if (password.length() < 6) {
                passLayout.setHint("Must be 6+ characters");
                passLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(errorColor));
                etPassword.setText("");
                passLayout.requestFocus();
            } else {
        /* ====================================================
           DATABASE WORK STARTS HERE (Firebase/API)
           ====================================================
        */
                Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
            }
        });

// --- TextWatcher-eo color reset logic add korte hobe ---

        etEmail.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailLayout.setHint("Username or Email");
                emailLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#676767")));
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        etPassword.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passLayout.setHint("Password");
                passLayout.setHintTextColor(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#676767")));
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(android.text.Editable s) {}
        });
        // Forgot Password Activity-te jawar jonno
        tvForgot.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotActivity.class);
            startActivity(intent);
        });

        // Sign Up Activity-te jawar jonno
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        // Social Logins Click Handling
        ivGoogle.setOnClickListener(v -> Toast.makeText(this, "Google Login Clicked", Toast.LENGTH_SHORT).show());
        ivApple.setOnClickListener(v -> Toast.makeText(this, "Apple Login Clicked", Toast.LENGTH_SHORT).show());
        ivFacebook.setOnClickListener(v -> Toast.makeText(this, "Facebook Login Clicked", Toast.LENGTH_SHORT).show());
    }
}