package com.ranmal.ebusapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ranmal.ebusapp.containers.AppContainer;
import com.ranmal.ebusapp.services.CurrentUserService;
import com.ranmal.ebusapp.services.CurrentUserServiceImplementation;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private CurrentUserService currentUserService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EBusApplication application = (EBusApplication) getApplication();
        AppContainer appContainer = application.appContainer;
        currentUserService = new CurrentUserServiceImplementation(
                appContainer.userRepository,
                appContainer.loginRepository,
                appContainer.executorService,
                appContainer.networkContainer.api
        );
        TextView signupLink = (TextView) findViewById(R.id.sign_up_link);

        signupLink.setOnClickListener(view -> switchToSignUp());

        email = (EditText) findViewById(R.id.emailid_login);
        password = (EditText) findViewById(R.id.password_login);

        Button login = (Button) findViewById(R.id.login_btn);

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
        currentUserService.loginUserAsync(email.getText().toString(), password.getText().toString(),
                user -> switchToFindRoutes());
    }
}