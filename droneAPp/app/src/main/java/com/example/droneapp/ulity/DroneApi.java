package com.example.droneapp.ulity;

import com.example.droneapp.model.Device;
import com.example.droneapp.model.Flight;
import com.example.droneapp.model.FlightInfo;
import com.example.droneapp.model.ImageUploadForm;
import com.example.droneapp.model.LoginForm;
import com.example.droneapp.model.Marker;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DroneApi {



    /*@POST("postAPDetail")
    Call<APDetail> createAPDetail(@Body APDetail apDetail);*/

    @POST("upload")
    @Multipart
    Call<ImageUploadForm> createImageUploadForm(
                                                @HeaderMap Map<String, String> token,
                                                @Part("latitude") RequestBody latitude,
                                                @Part("longitude") RequestBody longitude,
                                                @Part("flightID") RequestBody flightID,
                                                @Part("timeStamp") RequestBody timeStamp,
                                                @Part MultipartBody.Part file
                                                );
    @FormUrlEncoded
    @POST("flight")
    Call<Flight> createNewFlight(@Header("Authorization") String authHeader,
                                 @Field("flightName") String flightName,
                                 @Field("deviceID") String deviceID,
                                 @Field("latitudeList") List<Double> latitudeList,
                                 @Field("longitudeList") List<Double> longitudeList,
                                 @Field("timeStamp") String timeStamp

    );
    @FormUrlEncoded
    @POST("device/createNewDevice")
    Call<Boolean> createNewDevice(@Header("Authorization") String authHeader,
                                  @Field("deviceName") String deviceName,
                                  @Field("deviceKey") String deviceKey

                                 );
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "device/removeDevice", hasBody = true)
    Call<Boolean> removeDevice( @Header("Authorization") String authHeader,
                                @Field("deviceID") String deviceID);

    @FormUrlEncoded
    @PUT("finishFlight")
    Call<Boolean> finishFlight( @Header("Authorization") String authHeader,
                                @Field("flightID") String flightID
                                );

    @GET("device/getDeviceByUser")
    Call<List<Device>> getDeviceByUser(@Header("Authorization") String authHeader);


    @GET("device/getAvailableDevice")
    Call<List<Device>> getAvailableDevice(@Header("Authorization") String authHeader);

    @GET("marker/getMarkerFromUser")
    Call<List<Marker>> getMarker(@Header("Authorization") String authHeader);

    @GET("marker/getMarkerByFlightID")
    Call<List<Marker>> getMarkerByFlightID(@Header("Authorization") String authHeader,@Query("flightID") String flightID );

    @GET("getFlight")
    Call<Flight> getFlight(@Header("Authorization") String authHeader,@Query("flightName") String flightName  );

    @GET("getAllFlightInfo")
    Call<List<FlightInfo>> getAllFlightInfo(@Header("Authorization") String authHeader);

    @GET("getAllFlightInfoByDate")
    Call<List<FlightInfo>> getAllFlightByDate(@Header("Authorization") String authHeader,@Query("date") String date);

    @GET("getAllActiveFlightInfo")
    Call<List<FlightInfo>> getAllActiveFlightInfo(@Header("Authorization") String authHeader);

    @GET("getAllFlight")
    Call<List<Flight>> getAllFlight(@Header("Authorization") String authHeader);

    @GET("getAllImageUrlByMarkerIDAndFlightID")
    Call<List<String>> getImageUrls (@Header("Authorization") String authHeader , @Query("markerID") String markerID,@Query("flightID") String flightID );

    @POST("authenticate")
    Call<JsonElement> getToken (@Body LoginForm body);


}
