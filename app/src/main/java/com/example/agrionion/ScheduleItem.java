package com.example.agrionion;

public class ScheduleItem {
    private String plantId;  // Added Plant ID
    private String plantName;
    private String wateringDate;
    private String fertilizerDate;
    private String insecticideDate;
    private String harvestDate;
    private String imageUri;  // Changed from int imageResId to String imageUri

    public ScheduleItem(String plantId, String plantName, String wateringDate, String fertilizerDate,
                        String insecticideDate, String harvestDate, String imageUri) {
        this.plantId = plantId;
        this.plantName = plantName;
        this.wateringDate = wateringDate;
        this.fertilizerDate = fertilizerDate;
        this.insecticideDate = insecticideDate;
        this.harvestDate = harvestDate;
        this.imageUri = imageUri;
    }

    public String getPlantId() { return plantId; }
    public String getPlantName() { return plantName; }
    public String getWateringDate() { return wateringDate; }
    public String getFertilizerDate() { return fertilizerDate; }
    public String getInsecticideDate() { return insecticideDate; }
    public String getHarvestDate() { return harvestDate; }
    public String getImageUri() { return imageUri; }
}