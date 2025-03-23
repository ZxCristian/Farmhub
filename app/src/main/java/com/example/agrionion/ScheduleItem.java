package com.example.agrionion;


public class ScheduleItem {
    private String plantName;
    private String wateringDate;
    private String fertilizerDate;
    private String insecticideDate;
    private String harvestDate;
    private int imageResId;

    public ScheduleItem(String plantName, String wateringDate, String fertilizerDate, String insecticideDate, String harvestDate, int imageResId) {
        this.plantName = plantName;
        this.wateringDate = wateringDate;
        this.fertilizerDate = fertilizerDate;
        this.insecticideDate = insecticideDate;
        this.harvestDate = harvestDate;
        this.imageResId = imageResId;
    }

    public String getPlantName() { return plantName; }
    public String getWateringDate() { return wateringDate; }
    public String getFertilizerDate() { return fertilizerDate; }
    public String getInsecticideDate() { return insecticideDate; }
    public String getHarvestDate() { return harvestDate; }
    public int getImageResId() { return imageResId; }
}
