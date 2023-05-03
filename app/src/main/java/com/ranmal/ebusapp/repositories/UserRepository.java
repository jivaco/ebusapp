package com.ranmal.ebusapp.repositories;

import com.ranmal.ebusapp.database.User;

import java.util.List;

public interface UserRepository {
    void insert(User user);

    List<User> getAll();

    User findByEmail(String email);

    void deleteAll();
}
