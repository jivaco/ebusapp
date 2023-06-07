package com.ranmal.ebusapp.services;

import com.ranmal.ebusapp.repositories.RepositoryCallback;

public interface ServerStatusService {
    void isServerUpAsync(RepositoryCallback<Boolean> action);
}
