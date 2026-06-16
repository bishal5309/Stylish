package com.example.stylish;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataProvider {

    // 1. Mens ক্যাটাগরির ডাটা
    public static List<ProductModel> getMensData() {
        List<ProductModel> list = new ArrayList<>();
        list.add(new ProductModel("Mens Starry", "Mens Starry Sky Print Shirt", "₹399", "1,52,344", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Black Winter Jacket", "Casual cotton-padded jacket", "₹499", "6,890", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Denim Shirt", "Classic Blue Denim Shirt for Men", "₹899", "12,400", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Check Shirt", "Formal Checkered Cotton Shirt", "₹599", "8,200", R.drawable.ic_launcher_background));
        return list;
    }

    // 2. Womens ক্যাটাগরির ডাটা
    public static List<ProductModel> getWomensData() {
        List<ProductModel> list = new ArrayList<>();
        list.add(new ProductModel("Black Dress", "Solid Black Dress for Women", "₹2,000", "5,23,456", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Pink Embroidered", "EARTHEN Rose Pink Tiered Max", "₹1,900", "45,678", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Flare Dress", "Floral Print Tiered Midi Dress", "₹1,990", "3,35,566", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Silk Saree", "Traditional Indian Pure Silk Saree", "₹4,500", "1,200", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Black Dress", "Solid Black Dress for Women", "₹2,000", "5,23,456", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Pink Embroidered", "EARTHEN Rose Pink Tiered Max", "₹1,900", "45,678", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Flare Dress", "Floral Print Tiered Midi Dress", "₹1,990", "3,35,566", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Silk Saree", "Traditional Indian Pure Silk Saree", "₹4,500", "1,200", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Black Dress", "Solid Black Dress for Women", "₹2,000", "5,23,456", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Pink Embroidered", "EARTHEN Rose Pink Tiered Max", "₹1,900", "45,678", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Flare Dress", "Floral Print Tiered Midi Dress", "₹1,990", "3,35,566", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Silk Saree", "Traditional Indian Pure Silk Saree", "₹4,500", "1,200", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Black Dress", "Solid Black Dress for Women", "₹2,000", "5,23,456", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Pink Embroidered", "EARTHEN Rose Pink Tiered Max", "₹1,900", "45,678", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Flare Dress", "Floral Print Tiered Midi Dress", "₹1,990", "3,35,566", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Silk Saree", "Traditional Indian Pure Silk Saree", "₹4,500", "1,200", R.drawable.ic_launcher_background));
        return list;
    }

    // 3. Kids ক্যাটাগরির ডাটা
    public static List<ProductModel> getKidsData() {
        List<ProductModel> list = new ArrayList<>();
        list.add(new ProductModel("Cartoon T-Shirt", "Pure Cotton Printed T-Shirt", "₹249", "15,600", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Kids Jeans", "Soft Denim Comfortable Jeans", "₹599", "4,300", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Baby Frock", "Cute Pink Princess Frock", "₹799", "2,100", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Boys Suit", "Gentleman Party Wear Suit", "₹1,499", "980", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Kids Jeans", "Soft Denim Comfortable Jeans", "₹599", "4,300", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Baby Frock", "Cute Pink Princess Frock", "₹799", "2,100", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Boys Suit", "Gentleman Party Wear Suit", "₹1,499", "980", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Kids Jeans", "Soft Denim Comfortable Jeans", "₹599", "4,300", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Baby Frock", "Cute Pink Princess Frock", "₹799", "2,100", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Boys Suit", "Gentleman Party Wear Suit", "₹1,499", "980", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Kids Jeans", "Soft Denim Comfortable Jeans", "₹599", "4,300", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Baby Frock", "Cute Pink Princess Frock", "₹799", "2,100", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Boys Suit", "Gentleman Party Wear Suit", "₹1,499", "980", R.drawable.ic_launcher_background));
        return list;
    }

    // 4. Beauty ক্যাটাগরির ডাটা
    public static List<ProductModel> getBeautyData() {
        List<ProductModel> list = new ArrayList<>();
        list.add(new ProductModel("Lipstick Set", "Matte Finish Long Lasting", "₹899", "25,300", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Face Serum", "Vitamin C Brightening Serum", "₹550", "45,100", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Eye Palette", "12 Color Shimmer Eye Shadow", "₹1,200", "12,500", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Skin Cream", "Hydrating Night Care Cream", "₹450", "85,600", R.drawable.ic_launcher_background));
        return list;
    }

    // 5. Fashion (Accessories) ক্যাটাগরির ডাটা
    public static List<ProductModel> getFashionData() {
        List<ProductModel> list = new ArrayList<>();
        list.add(new ProductModel("Leather Bag", "Premium Handcrafted Handbag", "₹2,500", "1,344", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Sunglasses", "UV Protected Aviator Style", "₹1,200", "5,670", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Quartz Watch", "Silver Chain Luxury Watch", "₹3,999", "2,100", R.drawable.ic_launcher_background));
        list.add(new ProductModel("Gold Necklace", "Minimalist 18k Gold Plated", "₹1,500", "4,500", R.drawable.ic_launcher_background));
        return list;
    }
}