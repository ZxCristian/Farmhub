package com.example.agrionion;

public class NotificationModel {
    private String title;
    private String message; // Full message
    private String timestamp;
    private String pdfUrl;

    public NotificationModel(String title, String message, String timestamp, String pdfUrl) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.pdfUrl = pdfUrl;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getTimestamp() { return timestamp; }
    public String getPdfUrl() { return pdfUrl; }
}