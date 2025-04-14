package com.example.agrionion;

public class PreBuy{
    private String id;
    private String name;
    private String date;
    private double price;
    private String buyer;

    public PreBuy(String id, String name, String date, double price, String buyer) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.buyer = buyer;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    // Setters (if needed)
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
