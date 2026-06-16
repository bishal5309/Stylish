package com.example.stylish;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator; // Indicator import

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // Views
    private RecyclerView productRecyclerView;
    private RecyclerView categoryListRecycler;
    private ViewPager2 bannerViewPager;
    private View homeMainLayout;
    private DotsIndicator dotsIndicator; // Indicator variable

    // Adapters
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private BannerAdapter bannerAdapter;

    // Data Lists
    private List<ProductModel> currentProductList = new ArrayList<>();
    private List<CategoryModel> categoryList = new ArrayList<>();
    private List<BannerModel> bannerList = new ArrayList<>();

    // Auto-scroll handler & Runnable
    private final Handler sliderHandler = new Handler(Looper.getMainLooper());
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (bannerViewPager != null && bannerList.size() > 0) {
                int currentItem = bannerViewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % bannerList.size();
                bannerViewPager.setCurrentItem(nextItem, true);
            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ১. ভিউগুলো খুঁজে বের করা
        productRecyclerView = view.findViewById(R.id.product_recycler_view);
        categoryListRecycler = view.findViewById(R.id.category_list_recycler);
        bannerViewPager = view.findViewById(R.id.banner_view_pager);
        homeMainLayout = view.findViewById(R.id.home_main_layout);
        dotsIndicator = view.findViewById(R.id.dotsIndicator); // Indicator initialize

        // ২. স্লাইডার সেটআপ
        setupBannerSlider();

        // ৩. ক্যাটাগরি লিস্ট সেটআপ
        setupCategoryRecyclerView();

        // ৪. প্রোডাক্ট গ্রিড সেটআপ
        setupProductRecyclerView();

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupBannerSlider() {
        bannerList.clear();
        // Placeholder images (tumi ekhane real image list pass korte paro)
        bannerList.add(new BannerModel(R.drawable.ic_launcher_background));
        bannerList.add(new BannerModel(R.drawable.ic_launcher_background));
        bannerList.add(new BannerModel(R.drawable.ic_launcher_background));

        bannerAdapter = new BannerAdapter(bannerList);
        bannerViewPager.setAdapter(bannerAdapter);

        // --- INDICATOR FUNCTIONAL KORA ---
        if (dotsIndicator != null) {
            dotsIndicator.setViewPager2(bannerViewPager);
        }

        // স্লাইডার সোয়াইপ কনফ্লিক্ট ফিক্স
        bannerViewPager.getChildAt(0).setOnTouchListener((v, event) -> {
            bannerViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        // ইউজার ইন্টারঅ্যাকশন হ্যান্ডেল করা (Auto-scroll synchronization)
        bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });
    }

    private void setupCategoryRecyclerView() {
        categoryList.clear();
        categoryList.add(new CategoryModel("Beauty", R.drawable.ic_launcher_background));
        categoryList.add(new CategoryModel("Fashion", R.drawable.ic_launcher_background));
        categoryList.add(new CategoryModel("Mens", R.drawable.ic_launcher_background));
        categoryList.add(new CategoryModel("Womens", R.drawable.ic_launcher_background));
        categoryList.add(new CategoryModel("Kids", R.drawable.ic_launcher_background));

        categoryAdapter = new CategoryAdapter(categoryList, this::onCategoryClicked);

        categoryListRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryListRecycler.setAdapter(categoryAdapter);
    }

    private void setupProductRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        productRecyclerView.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(getContext(), currentProductList);
        productRecyclerView.setAdapter(productAdapter);
    }

    public void onCategoryClicked(String categoryName) {
        if (homeMainLayout != null) homeMainLayout.setVisibility(View.GONE);
        if (productRecyclerView != null) productRecyclerView.setVisibility(View.VISIBLE);

        List<ProductModel> newData;
        // Data provider theke data load kora
        switch (categoryName) {
            case "Mens": newData = CategoryDataProvider.getMensData(); break;
            case "Womens": newData = CategoryDataProvider.getWomensData(); break;
            case "Kids": newData = CategoryDataProvider.getKidsData(); break;
            case "Beauty": newData = CategoryDataProvider.getBeautyData(); break;
            case "Fashion": newData = CategoryDataProvider.getFashionData(); break;
            default: newData = CategoryDataProvider.getMensData(); break;
        }

        if (productAdapter != null) productAdapter.updateList(newData);

        if (getActivity() instanceof HomePage) {
            HomePage homePage = (HomePage) getActivity();
            String itemCount = newData.size() + "+";
            homePage.updateCategoryView(true, categoryName, itemCount);

            if (homePage.animationHelper != null && !homePage.animationHelper.isHeaderCollapsed) {
                homePage.animationHelper.startHeaderAnimation();
            }
        }
    }

    public void backToHome() {
        if (homeMainLayout != null) homeMainLayout.setVisibility(View.VISIBLE);
        if (productRecyclerView != null) productRecyclerView.setVisibility(View.GONE);

        if (getActivity() instanceof HomePage) {
            HomePage homePage = (HomePage) getActivity();
            homePage.updateCategoryView(false, "", "");

            if (homePage.animationHelper != null && homePage.animationHelper.isHeaderCollapsed) {
                homePage.animationHelper.reverseHeaderAnimation();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}