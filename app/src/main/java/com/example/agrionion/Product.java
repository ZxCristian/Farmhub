package com.example.agrionion;

import java.util.UUID;

public class Product {
    private String id;  // Unique ID
    private String name;
    private String weight;
    private String seller;
    private int imageResId;

    // Constructor with ID (used for adding existing products)
    public Product(String id, String name, String weight, String seller, int imageResId) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.seller = seller;
        this.imageResId = imageResId;
    }

    // Constructor without ID (generates a unique ID)
    public Product(String name, String weight, String seller, int imageResId) {
        this.id = UUID.randomUUID().toString(); // Automatically generate unique ID
        this.name = name;
        this.weight = weight;
        this.seller = seller;
        this.imageResId = imageResId;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getWeight() { return weight; }
    public String getSeller() { return seller; }
    public int getImageResId() { return imageResId; }

    // Setters (if needed)
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setWeight(String weight) { this.weight = weight; }
    public void setSeller(String seller) { this.seller = seller; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
}
