package com.example.ivan.recyclerviewmap;

/**
 * Created by Ivan on 9/8/2017.
 */

public class Event {

    private String name;
    private String trainer;
    private int imageId;
    private double latitude;
    private double longitude;
    private int position;

    public Event(String name, String trainer, int imageId, double latitude, double longitude) {
        this.name = name;
        this.trainer = trainer;
        this.imageId = imageId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getTrainer() {
        return trainer;
    }

    public int getImageId() {
        return imageId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition(){ return  position;}
}
