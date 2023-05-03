package com.ranmal.ebusapp.repositories;

import com.ranmal.ebusapp.schemas.AuthenticationResponse;
import com.ranmal.ebusapp.schemas.LoginUser;

public interface LoginRepository {
    void loginAsync(LoginUser loginUser, RepositoryCallback<GenericNetworkResponse<AuthenticationResponse>> callback);
}
