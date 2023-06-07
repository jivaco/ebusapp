package com.ranmal.ebusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FindRoutesSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_routes_search);
        String[] routes = new String[] {"Kalubowila", "Kohuwala", "Nugegoda"};
        ArrayAdapter routeAdapter = new ArrayAdapter<String>(this, R.layout.activity_listitemview, routes);
        ListView routeList = findViewById(R.id.routes_list);
        routeList.setAdapter(routeAdapter);
        routeList.setOnItemClickListener(
                (parent, view, position, id) -> switchToBusInfo()
        );
    }

    private void switchToBusInfo() {
        Intent businfoIntent = new Intent(this, BusInfo.class);
        startActivity(businfoIntent);
        finish();
    }
}