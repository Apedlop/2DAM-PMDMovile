package com.example.mapagoogle;

public class Place {
    private String name;
    private double latitude;
    private double longitude;
    private String type;

    // Constructor
    public Place(String name, double latitude, double longitude, String type) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getType() {
        return type;
    }
}
