package com.example.agrionion;

public class CustomerOrder {
        private String orderId;
        private String productName;
        private double price;
        private int quantity;
        private String date;

        public CustomerOrder(String orderId, String productName, double price, int quantity, String date) {
            this.orderId = orderId;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.date = date;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getProductName() {
            return productName;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getDate() {
            return date;
        }
    }