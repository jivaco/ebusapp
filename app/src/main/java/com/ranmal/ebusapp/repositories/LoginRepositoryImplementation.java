package com.ranmal.ebusapp.repositories;

import com.ranmal.ebusapp.containers.Api;
import com.ranmal.ebusapp.schemas.AuthenticationResponse;
import com.ranmal.ebusapp.schemas.LoginUser;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Response;

public class LoginRepositoryImplementation implements LoginRepository{
    private final Api api;
    private final Executor executor;

    public LoginRepositoryImplementation(Api api, Executor executor) {
        this.api = api;
        this.executor = executor;
    }

    @Override
    public void loginAsync(LoginUser loginUser, RepositoryCallback<GenericNetworkResponse<AuthenticationResponse>> callback) {
        executor.execute(() -> {
            GenericNetworkResponse<AuthenticationResponse> authenticationResponse;
            try {
                authenticationResponse = this.loginSync(loginUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            callback.onComplete(authenticationResponse);
        });
    }

    private GenericNetworkResponse<AuthenticationResponse> loginSync(LoginUser loginUser) throws IOException {
        Call<AuthenticationResponse> apiCall = api.authenticate(loginUser);
        Response<AuthenticationResponse> authResponse = apiCall.execute();
        if (authResponse.body() != null && authResponse.body().status.equals("success")) {
            return new GenericNetworkResponse.Success<>(authResponse.body());
        } else {
            return new GenericNetworkResponse.Error<>("Failed to authenticate. Please check credentials");
        }
    }
}
