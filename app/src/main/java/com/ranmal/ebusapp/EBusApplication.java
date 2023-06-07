package com.ranmal.ebusapp;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.ranmal.ebusapp.containers.AppContainer;
import com.ranmal.ebusapp.database.AuthDatabase;
import com.ranmal.ebusapp.repositories.UserRepositoryImplementation;
import com.ranmal.ebusapp.services.CurrentUserServiceImplementation;
import com.ranmal.ebusapp.services.ServerStatusServiceImplementation;

public class EBusApplication extends Application {
    public AppContainer appContainer;
    private SQLiteOpenHelper db;
    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer();
        db = new AuthDatabase(this);
        appContainer.userRepository = new UserRepositoryImplementation(db.getWritableDatabase());
        appContainer.currentUserService = new CurrentUserServiceImplementation(
                appContainer.userRepository,
                appContainer.networkContainer.api,
                appContainer.executorService
        );
        appContainer.serverStatusService = new ServerStatusServiceImplementation(
                appContainer.executorService,
                appContainer.networkContainer.api
        );
    }
}
