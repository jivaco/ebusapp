package com.ranmal.ebusapp.repositories;

import com.ranmal.ebusapp.schemas.User;

public interface UserRepository {
    void insert(User user);

    User getCurrent();

    User findByEmail(String email);

    void deleteAll();
}
