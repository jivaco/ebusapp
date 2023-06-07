package com.ranmal.ebusapp;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class FindRoutesFragment extends Fragment {

    private static final int LOCATION_PERMISSION_CODE = 101;
    private GoogleMap gMap;
    private FusedLocationProviderClient locationProviderClient;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gMap.setMyLocationEnabled(true);
            }
        }
    };

    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        requestPermissions( new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_routes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isLocationPermissionGranted()) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mapFragment.getMapAsync(callback);
                locationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                locationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17.0F));
                    }
                });
            }
        } else {
            requestLocationPermission();
        }
    }
}