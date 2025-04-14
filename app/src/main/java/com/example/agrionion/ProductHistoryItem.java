package com.example.agrionion;

public class ProductHistoryItem {
    private String productID;
    private String productName;
    private String date;
    private double price;
    private String buyer;
    private String quantity;

    public ProductHistoryItem(String productID, String productName, String date, double price, String buyer, String quantity) {
        this.productID = productID;
        this.productName = productName;
        this.date = date;
        this.price = price;
        this.buyer = buyer;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getBuyer() {
        return buyer;
    }
    public String getQuantity(){return quantity;}
}
