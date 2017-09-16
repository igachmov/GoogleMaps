package com.example.ivan.recyclerviewmap.Service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xComputers on 15/06/2017.
 */

public class PriceEvent implements Price {

    private static final long serialVersionUID = -5020478936928446634L;

    @SerializedName("fuel")
    private String fuel;
    @SerializedName("date")
    private String date;
    @SerializedName("price")
    private double price;
    @SerializedName("dimension")
    private String dimension;


    @Override
    public String fuel() {
        return fuel;
    }

    @Override
    public String date() {
        return date;
    }

    @Override
    public double price() {
        return price;
    }

    @Override
    public String dimension() {
        return dimension;
    }



}