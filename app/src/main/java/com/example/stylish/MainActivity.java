package com.example.stylish;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private LoginHelper loginHelper; // Declare globally

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loginHelper = new LoginHelper(this);
        // --- SESSION LOGIC START ---
        if (loginHelper.isLoggedIn()) {
            // ১. যদি ইউজার লগইন থাকে, সরাসরি মেইন অ্যাক্টিভিটি
            startActivity(new Intent(this, HomePage.class));
            finish();
            return; // নিচের কোড আর রান হবে না
        } else if (!loginHelper.isFirstTimeLaunch()) {
            // ২. যদি লগইন না থাকে কিন্তু অনবোর্ডিং আগে দেখে থাকে, তবে সরাসরি লগইন স্ক্রিন
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        // --- SESSION LOGIC END ---

    }
}