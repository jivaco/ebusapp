package com.ranmal.ebusapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ranmal.ebusapp.containers.AppContainer;
import com.ranmal.ebusapp.database.AuthDatabase;
import com.ranmal.ebusapp.repositories.UserRepositoryImplementation;
import com.ranmal.ebusapp.services.CurrentUserService;
import com.ranmal.ebusapp.services.CurrentUserServiceImplementation;

public class FindRoutesActivity extends AppCompatActivity {
    private CurrentUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_routes);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        // Set up DI
        EBusApplication application = (EBusApplication) getApplication();
        AppContainer appContainer = application.appContainer;
        userService = appContainer.currentUserService;
        verifyUser();
        Button searchTextInput = findViewById(R.id.find_routes_btn);
        searchTextInput.setOnClickListener(view -> search());
        Button logoutBtn = findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(view -> logout());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void forceUserLogin() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }

    private void search() {
        onSearchRequested();
    }

    private void verifyUser() {
        userService.verifyCurrentCachedUserAsync(
                userLoggedIn -> {
                    if (!userLoggedIn) forceUserLogin();
                }
        );
    }

    private void logout() {
        userService.logoutCurrentUser();
        forceUserLogin();
    }
}