package com.example.agrionion;

public class Transaction {
    private String title;
    private String date;
    private String amount;
    private String buyerName; // Add this field

    public Transaction(String title, String date, String amount, String buyerName) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.buyerName = buyerName; // Initialize buyer name
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getBuyerName() {
        return buyerName;
    }
}
