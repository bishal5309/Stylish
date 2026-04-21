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

// এই ইমপোর্টটি আপডেট করা হয়েছে
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class activity_onboarding extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView tvNext, tvPrev, tvSkip, tvPageCounter;
    private OnboardingAdapter adapter;
    private ArrayList<OnboardingItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // এখানে DotsIndicator ক্লাসটি ব্যবহার করা হয়েছে
        DotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);

        // Setup Onboarding Items
        setupOnboardingItems();

        // Setup Adapter
        adapter = new OnboardingAdapter(items);
        viewPager.setAdapter(adapter);

        // Setup Dots Indicator
        dotsIndicator.attachTo(viewPager);

        // ViewPager Page Change Logic
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Update Page Counter (e.g., 1/3)
                tvPageCounter.setText((position + 1) + "/3");

                // Update Buttons based on position
                if (position == 0) {
                    tvPrev.setVisibility(View.INVISIBLE); // First page
                    tvNext.setText("Next");
                } else if (position == items.size() - 1) {
                    tvPrev.setVisibility(View.VISIBLE); // Last page
                    tvNext.setText("Get Started");
                } else {
                    tvPrev.setVisibility(View.VISIBLE); // Middle page
                    tvNext.setText("Next");
                }
            }
        });

        // Next Button Click Logic
        tvNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                // Go to Login Screen
                startActivity(new Intent(activity_onboarding.this, LoginActivity.class));
                finish();
            }
        });

        // Prev Button Click Logic
        tvPrev.setOnClickListener(v -> {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        });

        // Skip Button Click Logic
        tvSkip.setOnClickListener(v -> {
            startActivity(new Intent(activity_onboarding.this, LoginActivity.class));
            finish();
        });
    }

    private void setupOnboardingItems() {
        items = new ArrayList<>();
        // Make sure your images are in res/drawable with these names
        items.add(new OnboardingItem(R.drawable.onboard1, "Choose Products", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit."));
        items.add(new OnboardingItem(R.drawable.onboard2, "Make Payment", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit."));
        items.add(new OnboardingItem(R.drawable.onboard3, "Get Your Order", "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit."));
    }
}