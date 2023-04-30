package com.ranmal.ebusapp.containers;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppContainer {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
    public NetworkContainer networkContainer;
}
