package com.nimil.searchbooks;

import android.app.Application;
import android.content.Context;

import com.nimil.searchbooks.rest.BookRestApi;
import com.nimil.searchbooks.rest.BookRestApiFactory;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AppController extends Application {
    private BookRestApi restApi;
    private Scheduler scheduler;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    private static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }

    public static AppController create(Context context) {
        return AppController.get(context);
    }

    public BookRestApi getRestApi() {
        if(restApi == null) {
            restApi = BookRestApiFactory.create();
        }
        return restApi;
    }

    public void setRestApi(BookRestApi restApi) {
        this.restApi = restApi;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
