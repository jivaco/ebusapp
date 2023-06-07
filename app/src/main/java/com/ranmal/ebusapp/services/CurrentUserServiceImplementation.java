package com.ranmal.ebusapp.services;

import android.util.Log;

import com.ranmal.ebusapp.containers.Api;
import com.ranmal.ebusapp.schemas.User;
import com.ranmal.ebusapp.repositories.GenericNetworkResponse;
import com.ranmal.ebusapp.repositories.RepositoryCallback;
import com.ranmal.ebusapp.repositories.UserRepository;
import com.ranmal.ebusapp.schemas.AuthenticationResponse;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Response;


public class CurrentUserServiceImplementation implements CurrentUserService {

    private final UserRepository userRepository;
    private final Api api;
    private final Executor executor;

    public CurrentUserServiceImplementation(UserRepository userRepository, Api api, Executor executor) {
        this.userRepository = userRepository;
        this.api = api;
        this.executor = executor;
    }

    @Override
    public void loginUserAsync(String email, String password, RepositoryCallback<Boolean> callback) {
        User user = new User(email, password);
        this.executor.execute(() -> {
            try {
               GenericNetworkResponse<AuthenticationResponse> response = loginUserSync(user);
               if (response instanceof GenericNetworkResponse.Success) {
                   this.userRepository.insert(user);
                   callback.onComplete(true);
               } else {
                   Log.d("responsestat", response.toString());
                   callback.onComplete(false);
               }
            } catch (IOException e) {
                Log.d("responseerror", e.toString());
                callback.onComplete(false);
            }
        });
    }

    private GenericNetworkResponse<AuthenticationResponse> loginUserSync(User user) throws IOException {
        Call<AuthenticationResponse> callApi = api.authenticate(user);
        Response<AuthenticationResponse> response = callApi.execute();
        Log.d("response_og", "" + response.message());
        if (response.isSuccessful() && response.body().status.equals("success")) {
            return new GenericNetworkResponse.Success<>(response.body());
        }
        return new GenericNetworkResponse.Error<>(response.message());
    }

    @Override
    public void getCurrentUserOrNullAsync(RepositoryCallback<User> action) {
    }

    @Override
    public void verifyCurrentCachedUserAsync(RepositoryCallback<Boolean> action) {
        User user = this.userRepository.getCurrent();
        if (user == null) {
            action.onComplete(false);
            return;
        }
        this.executor.execute(
                () -> {
                    try {
                        GenericNetworkResponse<AuthenticationResponse> response = loginUserSync(user);
                        if (response instanceof  GenericNetworkResponse.Success) {
                            action.onComplete(true);
                        } else {
                            action.onComplete(false);
                        }
                    } catch (IOException e) {
                        action.onComplete(false);
                    }
                }
        );
    }

    @Override
    public void logoutCurrentUser() {
        this.userRepository.deleteAll();
    }
}
