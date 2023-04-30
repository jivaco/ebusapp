package com.ranmal.ebusapp.containers;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkContainer {
    private static final String BASE_URL = "http://192.168.8.130:8080";
    public Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();
    public Api api = retrofit.create(Api.class);
}

