package com.ranmal.ebusapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ranmal.ebusapp.containers.AppContainer;
import com.ranmal.ebusapp.viewmodels.RegistrationViewModel;

public class SignUp extends AppCompatActivity {
    private RegistrationViewModel model;
    private EditText fullnameInput;
    private EditText emailInput;
    private EditText mobileInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_sign_up);
        // Setup di for the viewmodel
        EBusApplication application = (EBusApplication) getApplication();
        AppContainer appContainer = application.appContainer;
        // Setup observer for livedata
        model = new ViewModelProvider(this).get(RegistrationViewModel.class);
        model.setRepository(appContainer.registrationRepository);
        final Observer<String> registrationMessageObserver = newRegistrationMessage -> {
            Toast.makeText(getApplicationContext(), newRegistrationMessage, Toast.LENGTH_LONG).show();
            if (newRegistrationMessage.contains("Success")) {
                new Handler().postDelayed(this::switchToLoginAfterRegistration, 3500);
            }
        };
        model.getRegistrationMessage().observe(this, registrationMessageObserver);
        // Hook up event handlers
        fullnameInput = (EditText) findViewById(R.id.fullname);
        emailInput = (EditText) findViewById(R.id.emailid);
        mobileInput = (EditText) findViewById(R.id.mobile);
        passwordInput = (EditText) findViewById(R.id.password);
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String fullname = fullnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String mobileString = mobileInput.getText().toString();
        long mobile = Long.parseLong(mobileString.equals("") ? "1" : mobileString);
        model.makeRegistrationRequest(
                fullname,
                email,
                mobile,
                password
        );
    }

    private void switchToLoginAfterRegistration() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
}