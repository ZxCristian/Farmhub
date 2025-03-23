package com.example.agrionion;

public class PreOrderItem {
    private String productName;
    private String productWeight;
    private String productSeller;
    private int imageResource;

    public PreOrderItem(String productName, String productWeight, String productSeller, int imageResource) {
        this.productName = productName;
        this.productWeight = productWeight;
        this.productSeller = productSeller;
        this.imageResource = imageResource;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public String getProductSeller() {
        return productSeller;
    }

    public int getImageResource() {
        return imageResource;
    }
}
