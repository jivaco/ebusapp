package com.ranmal.ebusapp.repositories;

public interface RepositoryCallback<T> {
    void onComplete(T data);
}
