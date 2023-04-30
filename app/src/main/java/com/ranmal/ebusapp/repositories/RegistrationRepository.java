package com.ranmal.ebusapp.repositories;

import com.ranmal.ebusapp.schemas.UserDTO;

public interface RegistrationRepository {
    void makeRegistrationRequestAsync(
            String fullname,
            String email,
            long mobile,
            String password,
            RepositoryCallback<GenericNetworkResponse<UserDTO>> callback
    );
}
