package com.example.droneapp;

import com.example.droneapp.model.APDetail;
import com.example.droneapp.model.Base64Image;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSonPlaceHoldeApi {

    @POST("postAPDetail")
    Call<APDetail> createAPDetail(@Body APDetail apDetail);

    @POST("upload")
    Call<Base64Image> createBase64Image(@Body Base64Image base64Image);
}
