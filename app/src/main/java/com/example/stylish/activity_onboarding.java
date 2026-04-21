package com.example.stylish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class activity_onboarding extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView tvNext, tvPrev, tvSkip, tvPageCounter;
    private OnboardingAdapter adapter;
    private ArrayList<OnboardingItem> items;
    private LoginHelper loginHelper; // Declare globally

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginHelper = new LoginHelper(this);

        // --- SESSION LOGIC START ---
        if (loginHelper.isLoggedIn()) {
            // ১. যদি ইউজার লগইন থাকে, সরাসরি মেইন অ্যাক্টিভিটি
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return; // নিচের কোড আর রান হবে না
        } else if (!loginHelper.isFirstTimeLaunch()) {
            // ২. যদি লগইন না থাকে কিন্তু অনবোর্ডিং আগে দেখে থাকে, তবে সরাসরি লগইন স্ক্রিন
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        // --- SESSION LOGIC END ---

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);

        // EdgeToEdge Padding Setup
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // View IDs Initialization
        viewPager = findViewById(R.id.viewPager);
        tvNext = findViewById(R.id.tvNext);
        tvPrev = findViewById(R.id.tvPrev);
        tvSkip = findViewById(R.id.tvSkip);
        tvPageCounter = findViewById(R.id.tvPageCounter);
        DotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);

        setupOnboardingItems();

        adapter = new OnboardingAdapter(items);
        viewPager.setAdapter(adapter);
        dotsIndicator.attachTo(viewPager);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvPageCounter.setText((position + 1) + "/" + items.size());

                if (position == 0) {
                    tvPrev.setVisibility(View.INVISIBLE);
                    tvNext.setText("Next");
                } else if (position == items.size() - 1) {
                    tvPrev.setVisibility(View.VISIBLE);
                    tvNext.setText("Get Started");
                } else {
                    tvPrev.setVisibility(View.VISIBLE);
                    tvNext.setText("Next");
                }
            }
        });

        // Next/Get Started Button Click
        tvNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                navigateToLogin();
            }
        });

        // Skip Button Click
        tvSkip.setOnClickListener(v -> navigateToLogin());

        // Prev Button Click
        tvPrev.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        });
    }

    // লগইন স্ক্রিনে যাওয়ার কমন মেথড
    private void navigateToLogin() {
        loginHelper.setFirstTimeLaunch(false); // অনবোর্ডিং শেষ, তাই ফলস করে দিলাম
        startActivity(new Intent(activity_onboarding.this, LoginActivity.class));
        finish();
    }

    private void setupOnboardingItems() {
        items = new ArrayList<>();
        items.add(new OnboardingItem(R.drawable.onboard1, "Choose Products", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint."));
        items.add(new OnboardingItem(R.drawable.onboard2, "Make Payment", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint."));
        items.add(new OnboardingItem(R.drawable.onboard3, "Get Your Order", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint."));
    }
}