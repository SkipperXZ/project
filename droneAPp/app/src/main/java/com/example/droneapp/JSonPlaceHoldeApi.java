package com.example.droneapp;

import com.example.droneapp.model.Base64Image;
import com.example.droneapp.model.ImageUploadForm;
import com.example.droneapp.model.Marker;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface JSonPlaceHoldeApi {

    /*@POST("postAPDetail")
    Call<APDetail> createAPDetail(@Body APDetail apDetail);*/

    @POST("upload")
    @Multipart
    Call<ImageUploadForm> createImageUploadForm(@Part ("userID")RequestBody userID,
                                                @Part("latitude") RequestBody latitude,
                                                @Part("longitude") RequestBody longitude,
                                                @Part("timeStamp") RequestBody timeStamp,
                                                @Part MultipartBody.Part file
                                                );

    @GET("getMarkerFromUser")
    Call<List<Marker>> getMarker(@Query("userID") String userID);

    @GET("getAllImageUrlFormMarker")
    Call<List<String>> getImageUrls (@Query("userID") String userID , @Query("markerID") String markerID );
}
