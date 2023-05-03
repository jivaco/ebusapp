package com.ranmal.ebusapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ranmal.ebusapp.repositories.GenericNetworkResponse;
import com.ranmal.ebusapp.repositories.RegistrationRepository;
import com.ranmal.ebusapp.schemas.UserDTO;

public class RegistrationViewModel extends ViewModel {
    private RegistrationRepository repository;

    public void setRepository(RegistrationRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> registrationMessage;

    public MutableLiveData<String> getRegistrationMessage() {
        if (registrationMessage == null) {
            this.registrationMessage = new MutableLiveData<>();
        }
        return registrationMessage;
    }

    public void makeRegistrationRequest(
            String fullname,
            String email,
            long mobile,
            String password
    ) {
        repository.makeRegistrationRequestAsync(
                fullname,
                email,
                mobile,
                password,
                data -> {
                    if (data instanceof GenericNetworkResponse.Success) {
                        GenericNetworkResponse.Success<UserDTO> userData = (GenericNetworkResponse.Success<UserDTO>) data;
                        registrationMessage.postValue(String.format(
                                "Successfully registered %s",
                                userData.getData().email));
                    } else {
                        GenericNetworkResponse.Error<UserDTO> error = (GenericNetworkResponse.Error<UserDTO>) data;
                        registrationMessage.postValue(String.format("Error: %s", error.getMessage()));
                    }
                }
        );
    }

}
