package com.example.ivan.recyclerviewmap.Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ivan on 9/13/2017.
 */

public class GasstationEvent implements Gasstations{

    @SerializedName("num_all_results")
    private int num_all_results;
    @SerializedName("num_results")
    private int num_results;
    @SerializedName("gasstations")
    private List<Gasstation> gasstations;

    @Override
    public int num_all_results() {
        return num_all_results;
    }

    @Override
    public int num_results() {
        return num_results;
    }

    @Override
    public List<Gasstation> gasstations() {
        return gasstations;
    }
}
