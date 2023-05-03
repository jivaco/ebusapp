package com.ranmal.ebusapp.repositories;

@FunctionalInterface
public interface RepositoryCallback<T> {
    void onComplete(T data);
}
