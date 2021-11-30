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

    @POST("api/v1/login")
    Call<List<User>> getUsersPost(@Body User datos);

    @POST("api/v1/users")
    Call<List<User>> createUser(@Body User datos1);
}
