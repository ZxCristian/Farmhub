package com.example.agrionion;

public class ForecastModel {
    private String day;
    private String temperature;
    private String condition;
    private int iconResId;

    public ForecastModel(String day, String temperature, String condition, int iconResId) {
        this.day = day;
        this.temperature = temperature;
        this.condition = condition;
        this.iconResId = iconResId;
    }

    public String getDay() {
        return day;
    }

    public String getTemperature() {  // Ensure this method exists
        return temperature;
    }

    public String getCondition() {
        return condition;
    }

    public int getIconResId() {
        return iconResId;
    }
}
