package com.ck.casfid_challenge.data.remote.api;

import com.ck.casfid_challenge.data.model.UserResponseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/")
    Call<UserResponseDTO> getUsers(@Query("results") int results);

    @GET("api/")
    Call<UserResponseDTO> getUsersPaged(
            @Query("page") int page,
            @Query("results") int results
    );
}
