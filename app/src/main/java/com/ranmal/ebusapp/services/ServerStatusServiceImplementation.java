package com.ranmal.ebusapp.services;

import android.util.Log;

import com.ranmal.ebusapp.containers.Api;
import com.ranmal.ebusapp.repositories.RepositoryCallback;
import com.ranmal.ebusapp.schemas.HandshakeResponse;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Response;

public class ServerStatusServiceImplementation implements ServerStatusService {
    private final Executor executor;
    private final Api api;
    private boolean isServerUp;

    public ServerStatusServiceImplementation(Executor executor, Api api) {
        this.executor = executor;
        this.api = api;
        this.isServerUp = false;
    }

    @Override
    public void isServerUpAsync(RepositoryCallback<Boolean> callback) {
        executor.execute(
                () -> {
                    try {
                        isServerUp = isServerUpSync();
                    } catch (IOException e) {
                        isServerUp = false;
                        Log.d("server_check", e.toString());
                    }
                    callback.onComplete(isServerUp);
                }
        );
    }

    private boolean isServerUpSync() throws IOException {
        Call<HandshakeResponse> callApi = api.handshake();
        return callApi.execute().isSuccessful();
    }
}
