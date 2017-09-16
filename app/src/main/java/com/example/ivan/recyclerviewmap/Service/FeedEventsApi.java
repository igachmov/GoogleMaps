package com.example.ivan.recyclerviewmap.Service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xComputers on 14/06/2017.
 */

public interface FeedEventsApi {

    @GET("price?key=b9c2c52c4353e82&fuel=")
    Observable<PriceEvent> getEvents(
            @Query("fuel") String fuel,
            @Query("&date=") String date1);


    @GET("gasstations?key=b9c2c52c4353e82&brand_id=")
    Observable<GasstationEvent> getGasstations(
            @Query("brand_id") int brand_id,
            @Query("&fuel=") String fuel1,
            @Query("fuel") String fuel);

}