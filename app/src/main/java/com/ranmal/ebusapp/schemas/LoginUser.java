package com.ranmal.ebusapp.schemas;

public final class LoginUser {

    public final String email;
    public final String password;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
