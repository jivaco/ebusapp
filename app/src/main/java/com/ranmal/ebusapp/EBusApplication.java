package com.ranmal.ebusapp;

import android.app.Application;

import com.ranmal.ebusapp.containers.AppContainer;
import com.ranmal.ebusapp.containers.NetworkContainer;

public class EBusApplication extends Application {
    public AppContainer appContainer = new AppContainer();
    @Override
    public void onCreate() {
        appContainer.networkContainer = new NetworkContainer();
        super.onCreate();
    }
}
