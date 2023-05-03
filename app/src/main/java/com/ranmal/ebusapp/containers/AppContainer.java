package com.ranmal.ebusapp.containers;


import com.ranmal.ebusapp.database.AuthDatabase;
import com.ranmal.ebusapp.repositories.LoginRepository;
import com.ranmal.ebusapp.repositories.LoginRepositoryImplementation;
import com.ranmal.ebusapp.repositories.RegistrationRepository;
import com.ranmal.ebusapp.repositories.RegistrationRepositoryImplementation;
import com.ranmal.ebusapp.repositories.UserRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppContainer {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
    public NetworkContainer networkContainer = new NetworkContainer();
    public AuthDatabase db;
    public UserRepository userRepository;
    public LoginRepository loginRepository = new LoginRepositoryImplementation(networkContainer.api, executorService);
    public RegistrationRepository registrationRepository = new RegistrationRepositoryImplementation(executorService, networkContainer.api);
}
