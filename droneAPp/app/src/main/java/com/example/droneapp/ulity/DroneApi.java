package com.example.droneapp.ulity;

import com.example.droneapp.model.Device;
import com.example.droneapp.model.Flight;
import com.example.droneapp.model.FlightInfo;
import com.example.droneapp.model.ImageUploadForm;
import com.example.droneapp.model.Marker;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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
    @FormUrlEncoded
    @POST("device/createNewDevice")
    Call<Boolean> createNewDevice(@Field("userID") String userID ,
                                  @Field("deviceName") String deviceName,
                                  @Field("deviceKey") String deviceKey
                                 );
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "device/removeDevice", hasBody = true)
    Call<Boolean> removeDevice(@Field("deviceID") String deviceID);

    @FormUrlEncoded
    @PUT("finishFlight")
    Call<Boolean> finishFlight(@Field("flightID") String flightID);

    @GET("device/getDeviceByUser")
    Call<List<Device>> getDeviceByUser(@Query("userID") String userID);

    @GET("getMarkerFromUser")
    Call<List<Marker>> getMarker(@Query("userID") String userID);

    @GET("getFlight")
    Call<Flight> getFlight(@Query("flightName") String flightName ,@Query("userID") String userID);

    @GET("getAllFlightInfo")
    Call<List<FlightInfo>> getAllFlightInfo(@Query("userID") String userID);

    @GET("getAllFlightInfoByDate")
    Call<List<FlightInfo>> getAllFlightByDate(@Query("userID") String userID,@Query("date") String date);

    @GET("getAllActiveFlightInfo")
    Call<List<FlightInfo>> getAllActiveFlightInfo(@Query("userID") String userID);

    @GET("getAllFlight")
    Call<List<Flight>> getAllFlight(@Query("userID") String userID);

    @GET("getAllImageUrlFormMarker")
    Call<List<String>> getImageUrls (@Query("userID") String userID , @Query("markerID") String markerID );



}
