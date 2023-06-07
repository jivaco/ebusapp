package com.ranmal.ebusapp.containers;


import com.ranmal.ebusapp.repositories.RegistrationRepository;
import com.ranmal.ebusapp.repositories.RegistrationRepositoryImplementation;
import com.ranmal.ebusapp.repositories.UserRepository;
import com.ranmal.ebusapp.services.CurrentUserService;
import com.ranmal.ebusapp.services.ServerStatusService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppContainer {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
    public NetworkContainer networkContainer = new NetworkContainer();
    public UserRepository userRepository;
    public CurrentUserService currentUserService;
    public ServerStatusService serverStatusService;
    public RegistrationRepository registrationRepository = new RegistrationRepositoryImplementation(
            executorService,
            networkContainer.api
    );

    public int stepLimit = 0;
}
