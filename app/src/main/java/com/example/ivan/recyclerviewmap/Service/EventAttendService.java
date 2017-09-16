package com.example.ivan.recyclerviewmap.Service;

import com.example.ivan.recyclerviewmap.Service.base.BaseService;
import com.example.ivan.recyclerviewmap.Service.base.RetrofitInterface;

import rx.Observable;

/**
 * Created by xComputers on 15/07/2017.
 *
 */
@RetrofitInterface(retrofitApi = FeedEventsApi.class)
public class EventAttendService extends BaseService<FeedEventsApi> {

    public Observable<PriceEvent> getEvent(String fuel, String date1) {
        return serviceApi.getEvents(fuel,date1);
    }

    public Observable<GasstationEvent> getGasstations(int brand_id,String fuel1,String fuel){
        return serviceApi.getGasstations(brand_id,fuel1,fuel);
    }

}