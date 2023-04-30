package com.ranmal.ebusapp.containers;

import com.ranmal.ebusapp.schemas.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("signup")
    Call<UserDTO> signup(@Body UserDTO userDTO);
}
