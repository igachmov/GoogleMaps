package com.example.ivan.recyclerviewmap.Service;

/**
 * Created by Ivan on 9/13/2017.
 */

public class Gasstation {

    private int id;
    private double lat;
    private double lon;
    private String brand_name;
    private int brand_id;
    private String name;
    private String city;
    private String address;

    private int position;

    public Gasstation(int id, double lat, double lon, String brand_name, int brand_id, String name, String city, String address) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.brand_name = brand_name;
        this.brand_id = brand_id;
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lon;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition(){ return  position;}
}
