package com.example.ivan.recyclerviewmap.test;


import android.util.Log;

import com.example.ivan.recyclerviewmap.Service.base.HttpError;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;




public abstract class BasePresenter {

    private final Subject<String, String> errorSubject;

    protected BasePresenter() {

        errorSubject = PublishSubject.create();
    }

    //Here we call the handleError method
    protected Action1<Throwable> errorHandler() {

        return (this::handleError);
    }

    private void handleError(Throwable error) {
        String message = "This action requires an internet connection!";
        if (error instanceof UnknownHostException || error instanceof ConnectException) {
            Log.e("Error","UnknownHostException || ConnectException");
        }
        else if (error instanceof HttpException) {
            Log.e("Error","HttpException");
            HttpException exception = (HttpException) error;
            Response response = exception.response();
            Converter<ResponseBody, HttpError> converter = (Converter<ResponseBody, HttpError>) GsonConverterFactory.create()
                    .responseBodyConverter(HttpError.class, new Annotation[0], null);
            try {
                message = converter.convert(response.errorBody()).getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.e("Error","Just Error");
        errorSubject.onNext(message);
    }

    public Observable<String> errorObservable() {

        return errorSubject.asObservable().observeOn(AndroidSchedulers.mainThread());
    }
}