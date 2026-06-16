package com.example.stylish;

public class ProductModel {
    private String title;
    private String description;
    private String price;
    private String ratingCount;
    private int imageResId; // আপাতত লোকাল ইমেজের জন্য

    public ProductModel(String title, String description, String price, String ratingCount, int imageResId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.ratingCount = ratingCount;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getRatingCount() { return ratingCount; }
    public int getImageResId() { return imageResId; }
}