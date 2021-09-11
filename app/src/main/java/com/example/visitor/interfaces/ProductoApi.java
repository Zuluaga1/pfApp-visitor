package com.example.visitor.interfaces;

import com.example.visitor.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductoApi {
    @GET("api/crear")
    Call<List<User>> getUsersGet();

    @POST("api/crear")
    Call<List<User>> getUsersPost(@Body User datos);

    @POST("api/n_user")
    Call<List<User>> createUser(@Body User datos1);

    @POST("api/crear")
    Call<User> findUserPost(@Query("user")String User);
}
