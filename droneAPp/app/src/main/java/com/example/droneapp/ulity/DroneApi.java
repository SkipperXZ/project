package com.example.droneapp.ulity;

import com.example.droneapp.model.Flight;
import com.example.droneapp.model.ImageUploadForm;
import com.example.droneapp.model.Marker;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DroneApi {

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
    @FormUrlEncoded
    @POST("flight")
    Call<Flight> createNewFlight(@Field("flightName") String flightName,
                                 @Field("deviceID") String deviceID,
                                 @Field("userID") String userID,
                                 @Field("latitudeList") List<Double> latitudeList,
                                 @Field("longitudeList") List<Double> longitudeList,
                                 @Field("timeStamp") String timeStamp
    );

    @GET("getMarkerFromUser")
    Call<List<Marker>> getMarker(@Query("userID") String userID);

    @GET("getFlight")
    Call<Flight> getFlight(@Query("flightName") String flightName ,@Query("userID") String userID);

    @GET("getAllImageUrlFormMarker")
    Call<List<String>> getImageUrls (@Query("userID") String userID , @Query("markerID") String markerID );
}
