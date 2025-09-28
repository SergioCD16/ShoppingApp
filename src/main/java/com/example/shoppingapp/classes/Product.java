package com.example.shoppingapp.classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Product {
    private String Title;
    private String Category;
    // It can be: (Components) (Computers) (Smartphones) (TV and audio) (Consoles and Videogames)
    private String Description;
    private byte[] Picture;
    private float Price;
    private int Stock;
    private int ProductID;
    private LocalDateTime EntryDate;

    public Product(String title, String category, String description, byte[] picture, float price, int stock, int productID,  LocalDateTime entryDate) {
        this.Title = title;
        this.Category = category;
        this.Description = description;
        this.Picture = picture;
        this.Price = price;
        this.Stock = stock;
        this.ProductID = productID;
        this.EntryDate = entryDate;
    }

    public Product(String title, String category, String description, byte[] picture, float price, int stock) {
        this.Title = title;
        this.Category = category;
        this.Description = description;
        this.Picture = picture;
        this.Price = price;
        this.Stock = stock;
        this.ProductID = 0;
        this.EntryDate = LocalDateTime.now();
    }

    public String getTitle() {
        return this.Title;
    }
    public String getCategory() {
        return this.Category;
    }
    public String getDescription() {
        return this.Description;
    }
    public byte[] getPicture() {
        return this.Picture;
    }
    public float getPrice() {
        return this.Price;
    }
    public int getStock() {
        return this.Stock;
    }
    public int getProductID() {
        return this.ProductID;
    }
    public String getEntryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return this.EntryDate.format(formatter);
    }

    public void setTitle(String title) {
        this.Title = title;
    }
    public void setCategory(String category) {
        this.Category = category;
    }
    public void setDescription(String description) {
        this.Description = description;
    }
    public void setPicture(byte[] picture) {
        this.Picture = picture;
    }
    public void setPrice(float price) {
        this.Price = price;
    }
    public void setStock(int stock) {
        this.Stock = stock;
    }
    public void setProductID(int productID) {
        this.ProductID = productID;
    }
    public void setEntryDate(LocalDateTime entryDate) {
        this.EntryDate = entryDate;
    }

    @Override
    public String toString() {
        return super.toString() + ("Title: " + (this.Title) +  ", Category: "
                + (this.Category) + ", Description: " + (this.Description) + ", Price: " + (this.Price) + ", Stock: " +
                (this.Stock) + ", Entry Date: { " + (this.EntryDate.toString()) + " }");
    }

    /**
     * Returns the mean value of a Product's Reviews
     **/
    // Reescribir con MySQL
//    public static Float getReviewAverage(Product product) {
//        ArrayList<Review> ReviewList = product.getReviews();
//        Float sum = 0f, mean;
//        Review reviewi;
//        int marki;
//
//        if (ReviewList.isEmpty()) {
//            return 0f;
//        }
//        for (int i = 0; i <= ReviewList.size() - 1; i++) {
//            reviewi = ReviewList.get(i);
//            marki = reviewi.getMark();
//            sum += marki;
//        }
//        mean = sum/ReviewList.size();
//        return mean;
//    }
}
