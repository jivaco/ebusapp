package com.ranmal.ebusapp.services;

import com.ranmal.ebusapp.containers.Api;
import com.ranmal.ebusapp.database.User;
import com.ranmal.ebusapp.repositories.GenericNetworkResponse;
import com.ranmal.ebusapp.repositories.LoginRepository;
import com.ranmal.ebusapp.repositories.RepositoryCallback;
import com.ranmal.ebusapp.repositories.UserRepository;
import com.ranmal.ebusapp.schemas.LoginUser;

import java.util.List;
import java.util.concurrent.Executor;


public class CurrentUserServiceImplementation implements CurrentUserService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final Executor executor;
    private final Api api;

    public CurrentUserServiceImplementation(UserRepository userRepository, LoginRepository loginRepository, Executor executor, Api api) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
        this.executor = executor;
        this.api = api;
    }

    @Override
    public void loginUserAsync(String email, String password, RepositoryCallback<User> callback) {
        loginRepository.loginAsync(
                new LoginUser(email, password),
                data -> {
                    List<User> users = userRepository.getAll();
                    if (users.size() != 0) {
                        userRepository.deleteAll();
                    }
                    if (data instanceof GenericNetworkResponse.Success) {
                        User user = new User();
                        user.email = email;
                        user.password = password;
                        userRepository.insert(user);
                        callback.onComplete(user);
                    }
                }
        );
    }

    private void getCurrentUserorNullSync(RepositoryCallback<User> action) {
        List<User> users = userRepository.getAll();
        if (users.size() == 0) {
            action.onComplete(null);
        } else {
            action.onComplete(users.get(0));
        }
    }

    @Override
    public void getCurrentUserOrNullAsync(RepositoryCallback<User> action) {
        executor.execute(() -> getCurrentUserorNullSync(action));
    }

    @Override
    public void verifyCurrentCachedUserAsync(RepositoryCallback<Boolean> action) {
        executor.execute(
                () -> {
                    getCurrentUserorNullSync(
                            user -> {
                                if (user == null) {
                                    action.onComplete(false);
                                } else {
                                    loginRepository.loginAsync(
                                            new LoginUser(user.email, user.password),
                                            networkResponse -> {
                                                if (networkResponse instanceof GenericNetworkResponse.Success) {
                                                    action.onComplete(true);
                                                } else {
                                                    action.onComplete(false);
                                                }
                                            }
                                    );
                                }
                            }
                    );
                }
        );
    }

    @Override
    public void logoutCurrentUser() {
        executor.execute(userRepository::deleteAll);
    }
}
