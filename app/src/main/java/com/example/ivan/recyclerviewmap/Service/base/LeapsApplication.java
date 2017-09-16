package com.example.ivan.recyclerviewmap.Service.base;

import android.app.Application;


/**
 * Created by xComputers on 22/05/2017.
 */

public class LeapsApplication extends Application {

    public static final String BASE_URL = "http://fuelo.net/api";

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.getInstance().init(BASE_URL);
    }
}