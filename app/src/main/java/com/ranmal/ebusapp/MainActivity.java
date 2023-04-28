package com.ranmal.ebusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView signupLink = (TextView) findViewById(R.id.sign_up_link);

        signupLink.setOnClickListener(view -> switchToSignUp());
    }

    private void switchToSignUp() {
        Intent signupIntent = new Intent(this, SignUp.class);
        startActivity(signupIntent);
    }
}