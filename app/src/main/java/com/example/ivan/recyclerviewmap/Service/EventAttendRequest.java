package com.example.ivan.recyclerviewmap.Service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xComputers on 15/07/2017.
 */

public class EventAttendRequest {

    @SerializedName("fuel")
    private String fuel;
    @SerializedName("date")
    private String date;

    public EventAttendRequest(String fuel, String date) {
        this.fuel = fuel;
        this.date = date;
    }
}