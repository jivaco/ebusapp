package com.ranmal.ebusapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ranmal.ebusapp.containers.AppContainer;
import com.ranmal.ebusapp.services.CurrentUserService;
import com.ranmal.ebusapp.services.CurrentUserServiceImplementation;
import com.ranmal.ebusapp.services.ServerStatusService;


public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private CurrentUserService currentUserService;
    private ServerStatusService serverStatusService;
    private FrameLayout loader;
    private Handler uiHandler;
    private Button login;
    private TextView signupLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiHandler = new Handler(Looper.getMainLooper());
        //Set up DI
        setUpDI();
        //Set up interactions
        initSignup();
        initLogin();
        TextView disconnectWarning = findViewById(R.id.disconnect_warning);
        serverStatusService.isServerUpAsync(
                isServerUp -> {
                    if (!isServerUp) {
                        uiHandler.post(
                                () -> {
                                    disconnectWarning.setText("Warning! Server is down!");
                                    disconnectWarning.setVisibility(View.VISIBLE);
                                    login.setEnabled(false);
                                    signupLink.setVisibility(View.GONE);
                                }
                        );
                    }
                }
        );
    }

    private void setUpDI() {
        EBusApplication application = (EBusApplication) getApplication();
        AppContainer appContainer = application.appContainer;
        currentUserService = appContainer.currentUserService;
        serverStatusService = appContainer.serverStatusService;
    }

    private void initSignup() {
        signupLink = findViewById(R.id.sign_up_link);
        signupLink.setOnClickListener(view -> switchToSignUp());
    }

    private void initLogin() {
        email = findViewById(R.id.emailid_login);
        password = findViewById(R.id.password_login);
        loader = findViewById(R.id.loader);
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(view -> loginUser());
    }

    private void switchToSignUp() {
        Intent signupIntent = new Intent(this, SignUp.class);
        startActivity(signupIntent);
    }

    private void switchToFindRoutes() {
        Intent findRoutesIntent = new Intent(this, FindRoutesActivity.class);
        findRoutesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(findRoutesIntent);
        this.finish();
    }

    private void loginUser() {
        loader.setVisibility(View.VISIBLE);
        String _email = email.getText().toString();
        String _password = password.getText().toString();
        if (_email.isBlank() || _email == null || _password.isBlank() || _password == null) {
            Toast.makeText(getApplicationContext(), "Error! Please enter your email and password", Toast.LENGTH_SHORT).show();
            return;
        }
        currentUserService.loginUserAsync(_email, _password,
                hasLoggedIn -> {
                    if (hasLoggedIn) {
                        switchToFindRoutes();
                    } else {
                        uiHandler.post(
                                () -> Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_LONG).show()
                        );
                    }
                });
    }
}