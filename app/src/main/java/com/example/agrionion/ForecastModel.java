package com.example.agrionion;

public class ForecastModel {
    private String day;
    private String temp;
    private String condition;
    private int iconRes;
    private String rainChance; // New field for chance of rain

    public ForecastModel(String day, String temp, String condition, int iconRes, String rainChance) {
        this.day = day;
        this.temp = temp;
        this.condition = condition;
        this.iconRes = iconRes;
        this.rainChance = rainChance;
    }

    public String getDay() { return day; }
    public String getTemp() { return temp; }
    public String getCondition() { return condition; }
    public int getIconRes() { return iconRes; }
    public String getRainChance() { return rainChance; }
}