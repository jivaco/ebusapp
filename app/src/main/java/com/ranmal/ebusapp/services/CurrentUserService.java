package com.ranmal.ebusapp.services;

import com.ranmal.ebusapp.schemas.User;
import com.ranmal.ebusapp.repositories.RepositoryCallback;

public interface CurrentUserService {
    void loginUserAsync(String email, String password, RepositoryCallback<Boolean> action);
    void getCurrentUserOrNullAsync(RepositoryCallback<User> action);
    void verifyCurrentCachedUserAsync(RepositoryCallback<Boolean> action);
    void logoutCurrentUser();
}
