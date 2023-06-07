package com.ranmal.ebusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class BusInfo extends AppCompatActivity {

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startBtn = findViewById(R.id.start_trip);
        startBtn.setOnClickListener(
                view -> startTrip()
        );
    }

    private void startTrip() {
        Intent tripStart = new Intent(this, CurrentTripActivity.class);
        startActivity(tripStart);
        finish();
    }
}