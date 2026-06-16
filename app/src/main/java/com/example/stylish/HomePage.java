package com.example.stylish;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.textfield.TextInputLayout;

public class HomePage extends AppCompatActivity {

    ViewPager2 viewPager;

    // ======== HEADER ITEMS ========
    ImageView btnMenu, profileImage, headerLogo;
    CardView headerProfile;

    // ======== SEARCH BAR ========
    CardView searchCardView;
    TextInputLayout searchInputLayout;
    EditText etSearch;

    // ======== SORT, FILTER & TITLES ========
    RelativeLayout bottomSortFilterLayout;
    LinearLayout btnSort, btnFilter;
    LinearLayout stickyActionsLayout;
    ImageView stickyBtnSort, stickyBtnFilter;

    // NEW: ক্যাটাগরি টাইটেল কন্ট্রোল করার জন্য TextView
    TextView tvFeaturedTitle;
    TextView stickyItemCount;

    // ======== BOTTOM NAVIGATION ========
    LinearLayout navHome, navWishlist, navSearch, navSetting;
    CardView fabCart;
    ImageView cartIconInside; // FAB er bhetorer icon

    ImageView navHomeIcon, navWishlistIcon, navSearchIcon, navSettingIcon;
    TextView navHomeText, navWishlistText, navSearchText, navSettingText;

    // ======== ANIMATION HELPER ========
    public ScrollAnimationHelper animationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        // System UI (Status bar/Nav bar) er shathe padding fix kora
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews(); // shob views khuje ber kora

        MotionLayout motionLayout = findViewById(R.id.top_header_root);
        animationHelper = new ScrollAnimationHelper(motionLayout, viewPager);

        setupClickListeners();
        setupViewPager();




        // নতুন Back Press লজিক (অ্যান্ড্রয়েড ১৩+ সাপোর্টেড)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // ১. হোম ফ্র্যাগমেন্ট খুঁজে বের করা (ViewPager2 এর প্রথম ফ্র্যাগমেন্ট "f0" ট্যাগ পায়)
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("f0");

                if (homeFragment != null && homeFragment.getView() != null) {
                    View productGrid = homeFragment.getView().findViewById(R.id.product_recycler_view);

                    // ২. চেক করো এখন কি প্রোডাক্ট গ্রিড দেখা যাচ্ছে?
                    if (productGrid != null && productGrid.getVisibility() == View.VISIBLE) {
                        // ৩. যদি গ্রিড থাকে, তবে হোমে ফিরে যাও
                        homeFragment.backToHome();
                    } else {
                        // ৪. যদি নরমাল হোম পেজে থাকে, তবে অ্যাপ বন্ধ করো বা আগের অ্যাক্টিভিটিতে যাও
                        setEnabled(false); // এই কলব্যাকটি সাময়িকভাবে বন্ধ করো
                        getOnBackPressedDispatcher().onBackPressed(); // ডিফল্ট ব্যাক প্রেস ট্রিগার করো
                        setEnabled(true); // আবার এনাবল করো পরের বারের জন্য
                    }
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);

        btnMenu = findViewById(R.id.btn_menu);
        headerLogo = findViewById(R.id.header_logo);
        headerProfile = findViewById(R.id.header_profile);
        profileImage = findViewById(R.id.profile_image);

        searchCardView = findViewById(R.id.search_card_view);
        searchInputLayout = findViewById(R.id.search_input_layout);
        etSearch = findViewById(R.id.et_search);

        bottomSortFilterLayout = findViewById(R.id.bottom_sort_filter_layout);
        btnSort = findViewById(R.id.btn_sort);
        btnFilter = findViewById(R.id.btn_filter);

        // NEW: Title TextView gulo initialize kora holo
        tvFeaturedTitle = findViewById(R.id.tv_featured_title);
        stickyItemCount = findViewById(R.id.sticky_item_count);

        stickyActionsLayout = findViewById(R.id.sticky_actions_layout);
        stickyBtnSort = findViewById(R.id.sticky_btn_sort);
        stickyBtnFilter = findViewById(R.id.sticky_btn_filter);

        // Bottom Nav Initialize
        navHome = findViewById(R.id.nav_home);
        navWishlist = findViewById(R.id.nav_wishlist);
        navSearch = findViewById(R.id.nav_search);
        navSetting = findViewById(R.id.nav_setting);
        fabCart = findViewById(R.id.fab_cart);
        cartIconInside = findViewById(R.id.cart_icon_inside);

        navHomeIcon = findViewById(R.id.nav_home_icon);
        navHomeText = findViewById(R.id.nav_home_text);
        navWishlistIcon = findViewById(R.id.nav_wishlist_icon);
        navWishlistText = findViewById(R.id.nav_wishlist_text);
        navSearchIcon = findViewById(R.id.nav_search_icon);
        navSearchText = findViewById(R.id.nav_search_text);
        navSettingIcon = findViewById(R.id.nav_setting_icon);
        navSettingText = findViewById(R.id.nav_setting_text);
    }

    private void setupViewPager() {
        MainPagerAdapter adapter = new MainPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // ১. HOME বাটনে ক্লিকের লজিক আপডেট
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                if (viewPager.getCurrentItem() == 0) {
                    // ইউজার অলরেডি হোমে আছে, এখন চেক করো সে কি ক্যাটাগরি গ্রিডে আছে?
                    HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("f0");
                    if (homeFragment != null && homeFragment.getView() != null) {
                        View productGrid = homeFragment.getView().findViewById(R.id.product_recycler_view);
                        if (productGrid != null && productGrid.getVisibility() == View.VISIBLE) {
                            // যদি ক্যাটাগরি গ্রিড ওপেন থাকে, তবে ব্যাক টু হোম কল করো
                            homeFragment.backToHome();
                        }
                    }
                } else {
                    // অন্য পেজে থাকলে হোমে নিয়ে আসো
                    viewPager.setCurrentItem(0, true);
                }
            });
        }

        // বাকি বাটনগুলো আগের মতোই থাকবে
        if (navWishlist != null) navWishlist.setOnClickListener(v -> viewPager.setCurrentItem(1, true));
        if (fabCart != null) fabCart.setOnClickListener(v -> viewPager.setCurrentItem(2, true));
        if (navSearch != null) navSearch.setOnClickListener(v -> viewPager.setCurrentItem(3, true));
        if (navSetting != null) navSetting.setOnClickListener(v -> viewPager.setCurrentItem(4, true));

        // Swipe callback
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateBottomNavActiveColor(position);
            }
        });

        updateBottomNavActiveColor(0);
    }

    // ==========================================
    // DYNAMIC CATEGORY LOGIC (MAGIC FUNCTION)
    // ==========================================
    // Ei method ta Fragment theke call kora hobe jokhon user kono category te click korbe
    public void updateCategoryView(boolean isCategoryClicked, String categoryName, String itemCount) {
        MotionLayout motionLayout = findViewById(R.id.top_header_root);
        if (motionLayout == null) return;

        if (isCategoryClicked) {
            // ১. ক্যাটাগরিতে ক্লিক হয়েছে (যেমন: Mens)
            String dynamicText = itemCount + " Items in " + categoryName; // Ex: "200+ Items in Mens"

            // Normal abosthay All Featured er jaygay dekhabe
            if (tvFeaturedTitle != null) tvFeaturedTitle.setText(dynamicText);
            // Collapse hole search bar er niche sticky hoye dekhabe
            if (stickyItemCount != null) stickyItemCount.setText(dynamicText);

            // Java theke MotionScene er 'end' state e sticky_item_count ke VISIBLE kora hocche
            motionLayout.getConstraintSet(R.id.end).setVisibility(R.id.sticky_item_count, View.VISIBLE);
            motionLayout.getConstraintSet(R.id.end).setAlpha(R.id.sticky_item_count, 1f);

        } else {
            // ২. নরমাল হোম পেজে ব্যাক করলে (Category theke ber hole)
            if (tvFeaturedTitle != null) tvFeaturedTitle.setText("All Featured");

            // MotionScene er 'end' state e sticky_item_count ke abar GONE kora hocche (jate normal scrolle na ashe)
            motionLayout.getConstraintSet(R.id.end).setVisibility(R.id.sticky_item_count, View.GONE);
            motionLayout.getConstraintSet(R.id.end).setAlpha(R.id.sticky_item_count, 0f);
        }
    }

    private void updateBottomNavActiveColor(int position) {
        String activeColor = "#F83758";
        String inactiveColor = "#000000";
        String whiteColor = "#FFFFFF";

        // 1. Shob inactive koro
        resetNavItems(inactiveColor, whiteColor);

        // 2. Je page e achi sheita active koro
        switch (position) {
            case 0: // HOME
                setActiveItem(navHomeIcon, navHomeText, activeColor);
                setHeaderVisibility(View.VISIBLE);
                break;
            case 1: // WISHLIST
                setActiveItem(navWishlistIcon, navWishlistText, activeColor);
                setHeaderVisibility(View.VISIBLE);
                break;
            case 2: // CART
                if (fabCart != null) fabCart.setCardBackgroundColor(Color.parseColor(activeColor));
                if (cartIconInside != null) {
                    cartIconInside.setColorFilter(Color.parseColor(whiteColor));
                    cartIconInside.setBackgroundColor(Color.TRANSPARENT);
                }
                setHeaderVisibility(View.GONE);
                resetHeaderAnimation();
                break;
            case 3: // CHAT
                setActiveItem(navSearchIcon, navSearchText, activeColor);
                setHeaderVisibility(View.GONE);
                resetHeaderAnimation();
                break;
            case 4: // SETTING
                setActiveItem(navSettingIcon, navSettingText, activeColor);
                setHeaderVisibility(View.GONE);
                resetHeaderAnimation();
                break;
        }
    }

    private void resetNavItems(String inactiveColor, String whiteColor) {
        if(navHomeIcon != null) { navHomeIcon.setColorFilter(Color.parseColor(inactiveColor)); navHomeText.setTextColor(Color.parseColor(inactiveColor)); }
        if(navWishlistIcon != null) { navWishlistIcon.setColorFilter(Color.parseColor(inactiveColor)); navWishlistText.setTextColor(Color.parseColor(inactiveColor)); }
        if(navSearchIcon != null) { navSearchIcon.setColorFilter(Color.parseColor(inactiveColor)); navSearchText.setTextColor(Color.parseColor(inactiveColor)); }
        if(navSettingIcon != null) { navSettingIcon.setColorFilter(Color.parseColor(inactiveColor)); navSettingText.setTextColor(Color.parseColor(inactiveColor)); }

        if(fabCart != null) fabCart.setCardBackgroundColor(Color.parseColor(whiteColor));
        if(cartIconInside != null) {
            cartIconInside.setColorFilter(Color.parseColor(inactiveColor));
            cartIconInside.setBackgroundColor(Color.WHITE);
        }
    }

    private void setActiveItem(ImageView icon, TextView text, String color) {
        if (icon != null) icon.setColorFilter(Color.parseColor(color));
        if (text != null) text.setTextColor(Color.parseColor(color));
    }

    private void setHeaderVisibility(int visibility) {
        if (searchCardView != null) searchCardView.setVisibility(visibility);
        if (bottomSortFilterLayout != null) bottomSortFilterLayout.setVisibility(visibility);
    }

    private void resetHeaderAnimation() {
        if (animationHelper != null && animationHelper.isHeaderCollapsed) {
            animationHelper.reverseHeaderAnimation();
        }
    }

    private void setupClickListeners() {
        if(btnMenu != null) btnMenu.setOnClickListener(v -> showToast("Menu Drawer open hobe!"));
        if(headerProfile != null) headerProfile.setOnClickListener(v -> showToast("Profile Clicked!"));

        // ======== BOTTOM SORT & FILTER (Normal) ========
        if(btnSort != null) btnSort.setOnClickListener(v -> showToast("Sort Options Open Hobe!"));
        if(btnFilter != null) btnFilter.setOnClickListener(v -> showToast("Filter Page Open Hobe!"));

        // ======== STICKY SORT & FILTER (Collapsed) ========
        if(stickyBtnSort != null) stickyBtnSort.setOnClickListener(v -> showToast("Sticky Sort Clicked!"));
        if(stickyBtnFilter != null) stickyBtnFilter.setOnClickListener(v -> showToast("Sticky Filter Clicked!"));

        // ======== SEARCH BAR LOGIC ========
        if(etSearch != null) {
            // Focus ashole header collapse hobe, focus gele abar expand hobe
            etSearch.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus && !animationHelper.isHeaderCollapsed) {
                    animationHelper.startHeaderAnimation();
                }
                else if (!hasFocus && etSearch.getText().toString().trim().isEmpty() && animationHelper.isHeaderCollapsed) {
                    animationHelper.reverseHeaderAnimation();
                }
            });

            etSearch.setOnClickListener(v -> {
                if (!animationHelper.isHeaderCollapsed) animationHelper.startHeaderAnimation();
            });
        }
    }

    private void showToast(String message) {
        Toast.makeText(HomePage.this, message, Toast.LENGTH_SHORT).show();
    }

    // ==========================================
    // KEYBOARD & FOCUS LOGIC (TOUCH CONTROL)
    // ==========================================
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);

                // Jodi user screen er faka jaygay click kore (Search Bar er baire)
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {

                    // --- MAGIC CHECK: Sticky layout a click poreche kina ---
                    View stickyLayout = findViewById(R.id.sticky_actions_layout);
                    Rect stickyRect = new Rect();
                    if (stickyLayout != null) {
                        stickyLayout.getGlobalVisibleRect(stickyRect);
                    }

                    // Jodi Sticky Sort/Filter a click pore, tahole keyboard hide korbe na
                    if (stickyRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        return super.dispatchTouchEvent(event);
                    }

                    // Baki onno jaygay click korle focus shore jabe ebong keyboard hide hobe
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    // Kichu lekha na thakle header abar ager moto boro hobe
                    String searchText = ((EditText) v).getText().toString().trim();
                    if (searchText.isEmpty() && animationHelper != null && animationHelper.isHeaderCollapsed) {
                        animationHelper.reverseHeaderAnimation();
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }


}