package com.example.stylish;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.widget.NestedScrollView;

public class ScrollAnimationHelper {

    private MotionLayout motionLayout;
    private View contentLayout;
    public boolean isHeaderCollapsed = false;
    public boolean isAnimating = false;

    // Constructor: Jeta diye MotionLayout ar Content ke chinabo
    public ScrollAnimationHelper(MotionLayout motionLayout, View contentLayout) {
        this.motionLayout = motionLayout;
        this.contentLayout = contentLayout;
    }

    // Scroll Track korar Main Logic
    @SuppressLint("ClickableViewAccessibility")
    public void setupScrollTracking(NestedScrollView nestedScrollView) {
        if (nestedScrollView == null || motionLayout == null) return;

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isAnimating) return true;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float currentY = event.getY();
                        float deltaY = startY - currentY;

                        // COLLAPSE
                        if (!isHeaderCollapsed && deltaY > 15) {
                            startHeaderAnimation();
                            return true;
                        }

                        // EXPAND
                        if (isHeaderCollapsed && deltaY < -15 && v.getScrollY() <= 0) {
                            reverseHeaderAnimation();
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= 0 && isHeaderCollapsed && !isAnimating) {
                    reverseHeaderAnimation();
                }
            }
        });
    }

    // Header Choto kora
    public void startHeaderAnimation() {
        if (!isHeaderCollapsed && !isAnimating) {
            isAnimating = true;
            isHeaderCollapsed = true;
            motionLayout.transitionToEnd();

            if (contentLayout != null) {
                // Ekhane value -115f thakle content Search Bar-er pichone dhuke jay
                // Eita komiye -60f ba -50f kore daw (Mene check kore dekho koto-tuku thik lage)
                contentLayout.animate()
                        .translationY(-10f) // <--- Ei value ta komale item nicha thakbe
                        .setDuration(300)
                        .withEndAction(() -> isAnimating = false)
                        .start();
            } else {
                isAnimating = false;
            }
        }
    }

    // Header Boro kora
    public void reverseHeaderAnimation() {
        if (isHeaderCollapsed && !isAnimating) {
            isAnimating = true;
            isHeaderCollapsed = false;
            motionLayout.transitionToStart();
            if (contentLayout != null) {
                contentLayout.animate().translationY(0f)
                        .setDuration(300)
                        .withEndAction(() -> isAnimating = false)
                        .start();
            } else {
                isAnimating = false;
            }
        }
    }
}