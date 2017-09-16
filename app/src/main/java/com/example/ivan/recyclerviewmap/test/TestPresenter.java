package com.example.ivan.recyclerviewmap.test;


import com.example.ivan.recyclerviewmap.Service.EventAttendService;
import com.example.ivan.recyclerviewmap.Service.GasstationEvent;
import com.example.ivan.recyclerviewmap.Service.PriceEvent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Ivan on 9/2/2017.
 */

public class TestPresenter extends BasePresenter {

    private EventAttendService service;
    private Subject<PriceEvent, PriceEvent> followingSubject;
    private Subject<Throwable, Throwable> errorFollowingSubject;
    private Subject<GasstationEvent, GasstationEvent> gasstationEventSubject;
    private Subject<Throwable, Throwable> errorGasstationSubject;

    public TestPresenter(){
        service = new EventAttendService();
        followingSubject = PublishSubject.create();
        errorFollowingSubject = PublishSubject.create();
        gasstationEventSubject = PublishSubject.create();
        errorGasstationSubject = PublishSubject.create();
    }

    public void getEvent(String fuel,String date1){
        service.addHeader("Authorization", "b9c2c52c4353e82");
        service.getEvent(fuel,date1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(priceEvent ->{
                    followingSubject.onNext(priceEvent);
                    service.removeHeader("Authorization");
                }, throwable -> {
                    service.removeHeader("Authorization");
                    errorHandler().call(throwable);
                    errorFollowingSubject.onNext(null);
                });
    }

    public void getGasstations(int brand_id,String fuel1,String fuel){
        service.addHeader("Authorization", "b9c2c52c4353e82");
        service.getGasstations(brand_id,fuel1,fuel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gasstationEvent ->{
                    gasstationEventSubject.onNext(gasstationEvent);
                    service.removeHeader("Authorization");
                }, throwable -> {
                    service.removeHeader("Authorization");
                    errorHandler().call(throwable);
                    errorGasstationSubject.onNext(null);
                });

    }




    public Observable<PriceEvent> getEventObservable(){
        return followingSubject.asObservable().observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Throwable> getErrorEventObservable(){
        return errorFollowingSubject.asObservable().observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GasstationEvent> getGasstationEventObservable(){
        return gasstationEventSubject.asObservable().observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<Throwable> getErrorGasstationEventObservable(){
        return errorGasstationSubject.asObservable().observeOn(AndroidSchedulers.mainThread());
    }

}
