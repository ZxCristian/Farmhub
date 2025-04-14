package com.example.agrionion;

public class CartItem {
    private String name;
    private String quantity;
    private String price;
    private String location;
    private String contactNumber;
    private int imageResId; // For the image resource ID (or you can use a URL if loading from the internet)

    public CartItem(String name, String quantity, String price, String location, String contactNumber, int imageResId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.location = location;
        this.contactNumber = contactNumber;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public String getPrice() { return price; }
    public String getLocation() { return location; }
    public String getContactNumber() { return contactNumber; }
    public int getImageResId() { return imageResId; }
}