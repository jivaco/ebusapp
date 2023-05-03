package com.ranmal.ebusapp.repositories;


import com.ranmal.ebusapp.containers.Api;
import com.ranmal.ebusapp.schemas.UserDTO;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Response;

public class RegistrationRepositoryImplementation implements RegistrationRepository {

    private final Executor executor;
    private final Api api;

    public RegistrationRepositoryImplementation(Executor executor, Api api) {
        this.api = api;
        this.executor = executor;
    }

    private GenericNetworkResponse<UserDTO> makeRegistrationRequestSync(final UserDTO userDTO) throws IOException {
        Call<UserDTO> callApi = api.signup(userDTO);
        Response<UserDTO> response = callApi.execute();
        if (response.isSuccessful()) {
            return new GenericNetworkResponse.Success<>(response.body());
        } else {
            return new GenericNetworkResponse.Error<>(response.message());
        }
    }

    public void makeRegistrationRequestAsync(
            String fullname,
            String email,
            long mobile,
            String password,
            RepositoryCallback<GenericNetworkResponse<UserDTO>> callback
    ) {
        final UserDTO userDTO = new UserDTO(
                fullname,
                email,
                mobile,
                password
        );
        executor.execute(() -> {
            GenericNetworkResponse<UserDTO> response;
            try {
                response = makeRegistrationRequestSync(userDTO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            callback.onComplete(response);
        });
    }
}
