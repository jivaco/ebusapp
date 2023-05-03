package com.ranmal.ebusapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ranmal.ebusapp.containers.AppContainer;
import com.ranmal.ebusapp.database.AuthDatabase;
import com.ranmal.ebusapp.repositories.UserRepositoryImplementation;
import com.ranmal.ebusapp.services.CurrentUserService;
import com.ranmal.ebusapp.services.CurrentUserServiceImplementation;

public class FindRoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up DI
        EBusApplication application = (EBusApplication) getApplication();
        AppContainer appContainer = application.appContainer;
        appContainer.db = new AuthDatabase(getApplicationContext());
        appContainer.userRepository = new UserRepositoryImplementation(appContainer.db.getWritableDatabase());
        // verify login
        CurrentUserService currentUserService = new CurrentUserServiceImplementation(
                appContainer.userRepository,
                appContainer.loginRepository,
                appContainer.executorService,
                appContainer.networkContainer.api
        );
        currentUserService.verifyCurrentCachedUserAsync(
                verified -> {
                    if (!verified) {
                        forceUserLogin();
                    }
                }
        );
        setContentView(R.layout.activity_find_routes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void forceUserLogin() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }
}