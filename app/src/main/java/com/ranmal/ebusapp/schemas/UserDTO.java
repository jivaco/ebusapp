package com.ranmal.ebusapp.schemas;

public final class UserDTO {
    public final String fullname;
    public final  String email;
    public final long mobile;
    public final String password;

    public UserDTO(String fullname, String email, long mobile, String password) {
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }
}
