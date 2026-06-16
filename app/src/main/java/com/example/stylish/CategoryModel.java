package com.example.stylish;

public class CategoryModel {
    private String title;
    private int imageResId;

    public CategoryModel(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
}