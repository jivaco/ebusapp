package com.ranmal.ebusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.ranmal.ebusapp.containers.AppContainer;

public class CurrentTripActivity extends AppCompatActivity {

    private Sensor sensor;
    private SensorManager sensorManager;
    private Handler uiHandler;
    private AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_trip);
        uiHandler = new Handler(Looper.getMainLooper());
        EBusApplication application = (EBusApplication) getApplication();
        appContainer = application.appContainer;
        TextView status = findViewById(R.id.status);
        TextView heading = findViewById(R.id.find_routes_heading);
        appContainer.stepLimit = 0;
        if (isPermissionGranted()) {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            Log.d("sensor", "" + sensor);
            if (sensor != null) {
                sensorManager.registerListener(new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        appContainer.stepLimit += event.values[0];
                        Log.d("sensor", "" + event.values[0]);
                        Log.d("sensor store", "" + appContainer.stepLimit);
                        if (appContainer.stepLimit >= 8) {
                            uiHandler.post(
                                    () -> {
                                        status.setText("Trip Finished");
                                        heading.setText("You have arrived!");
                                    }
                            );
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                }, sensor, SensorManager.SENSOR_DELAY_FASTEST);
            }
        } else {
            requestPermissions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appContainer.stepLimit = 0;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACTIVITY_RECOGNITION}, 2111);
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED;
    }
}