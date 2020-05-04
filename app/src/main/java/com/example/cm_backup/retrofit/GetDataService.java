package com.example.cm_backup.retrofit;

import androidx.room.Delete;

import com.example.cm_backup.classes.Nota;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("nota")
    Call<List<Problema>> getAllProblemas(@Header("Authorization") String api_token);

    @GET("nota/{nota}")
    Call<Problema> getThisProblema(@Path ("nota") Nota nota, @Header("Authorization") String api_token);

    @POST("nota")
    Call<Problema> postProblema(@Header("Authorization") String api_token, @Body() Problema problema);

    @PUT("nota/{nota}")
    Call<Problema> putProblema(@Path ("nota") Nota nota, @Header("Authorization") String api_token, @Body() Problema problema);

    @DELETE("nota/{nota}")
    Call<ResponseBody> deleteProblema(@Path ("nota") @Header("Authorization") String api_token);

    @POST("login")
    Call<User> login(@Body User user);

    @POST("register")
    Call<User> register(@Body User user);

    @POST("logout")
    Call<User> logout(@Header("Authorization") String api_token);
}
