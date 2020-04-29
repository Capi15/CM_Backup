package com.example.cm_backup.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface GetDataService {
    @GET("nota")
    Call<List<Problema>> getAllProblemas();

    @GET("nota/{nota}")
    Call<Problema> getThisProblema();

    @POST("nota")
    Call<List<Problema>> postProblema();

    @PUT("nota/{nota}")
    Call<List<Problema>> putProblema();

    @POST("login")
    Call<User> login(@Body User user);

    @POST("register")
    Call<User> register();

    @POST("logout")
    Call<User> logout();
}
