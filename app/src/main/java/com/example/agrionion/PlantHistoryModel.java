package com.example.agrionion;

public class PlantHistoryModel {
    private String batchName;
    private String date;
    private String quantity;
    private int imageResId;  // Image resource ID

    public PlantHistoryModel(String batchName, String date, String quantity, int imageResId) {
        this.batchName = batchName;
        this.date = date;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public String getBatchName() { return batchName; }
    public String getDate() { return date; }
    public String getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }
}
