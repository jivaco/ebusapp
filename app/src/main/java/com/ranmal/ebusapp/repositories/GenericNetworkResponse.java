package com.ranmal.ebusapp.repositories;

public class GenericNetworkResponse<T> {

    public static final class Success<T> extends GenericNetworkResponse<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static final class Error<T> extends GenericNetworkResponse<T> {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
