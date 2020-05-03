package com.example.cm_backup.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface GetDataService {
    @GET("nota")
    Call<List<Problema>> getAllProblemas(@Header("Authorization") String api_token);

    @GET("nota/{nota}")
    Call<Problema> getThisProblema();

    @POST("nota")
    Call<Problema> postProblema(@Header("Authorization") String api_token, @Body() Problema problema);

    @PUT("nota/{nota}")
    Call<Problema> putProblema();

    @POST("login")
    Call<User> login(@Body User user);

    @POST("register")
    Call<User> register(@Body User user);

    @POST("logout")
    Call<User> logout(@Header("Authorization") String api_token);
}
