package com.ranmal.ebusapp;

import android.app.Application;

import com.ranmal.ebusapp.containers.AppContainer;

public class EBusApplication extends Application {
    public AppContainer appContainer;
    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer();
    }

}
