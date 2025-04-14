package com.example.agrionion;

public class CustomerPreOrderModel {
    private String orderId;
    private String productName;
    private String date;
    private int quantity;
    private double amount;

    public CustomerPreOrderModel(String orderId, String productName, String date, int quantity, double amount) {
        this.orderId = orderId;
        this.productName = productName;
        this.date = date;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }
}