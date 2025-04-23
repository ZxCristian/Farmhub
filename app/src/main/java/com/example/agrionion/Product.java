package com.example.agrionion;

import java.util.UUID;

public class Product {
    private String id; // Unique ID
    private String name; // Product name
    private String weight; // Product weight
    private String seller; // Seller name
    private String address; // Seller address
    private int imageResId; // Resource ID for product image
    private String date; // Transaction date
    private double price; // Transaction price
    private String buyer; // Buyer name
    private String quantity; // Transaction quantity
    private String status; // Order status (e.g., PROCESSING, COMPLETED, CANCELLED)

    // Constructor with ID (for existing products)
    public Product(String id, String name, String address, String seller, String weight, int imageResId,
                   String date, double price, String buyer, String quantity, String status) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.address = address;
        this.seller = seller;
        this.imageResId = imageResId;
        this.date = date;
        this.price = price;
        this.buyer = buyer;
        this.quantity = quantity;
        this.status = status;
    }

    // Constructor without ID (generates a unique ID)
    public Product(String name, String address, String seller, String weight, int imageResId,
                   String date, double price, String buyer, String quantity, String status) {
        this.id = UUID.randomUUID().toString(); // Automatically generate unique ID
        this.name = name;
        this.weight = weight;
        this.address = address;
        this.seller = seller;
        this.imageResId = imageResId;
        this.date = date;
        this.price = price;
        this.buyer = buyer;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getWeight() { return weight; }
    public String getSeller() { return seller; }
    public String getAddress() { return address; }
    public int getImageResId() { return imageResId; }
    public String getDate() { return date; }
    public double getPrice() { return price; }
    public String getBuyer() { return buyer; }
    public String getQuantity() { return quantity; }
    public String getStatus() { return status; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setWeight(String weight) { this.weight = weight; }
    public void setSeller(String seller) { this.seller = seller; }
    public void setAddress(String address) { this.address = address; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
    public void setDate(String date) { this.date = date; }
    public void setPrice(double price) { this.price = price; }
    public void setBuyer(String buyer) { this.buyer = buyer; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
}