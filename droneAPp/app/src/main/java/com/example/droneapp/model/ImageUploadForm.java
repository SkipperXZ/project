package com.example.droneapp.model;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageUploadForm {
    private   RequestBody userID;
    private RequestBody latitude;
    private RequestBody longitude;
    private RequestBody timeStamp;
     private MultipartBody.Part imageFile;
     private RequestBody flightID;

    public ImageUploadForm(String userID, double latitude, double longitude,String flightID, String timeStamp, byte[] imageFile) {
        this.userID =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), userID);
        this.latitude =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),String.valueOf(latitude));
        this.longitude =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),String.valueOf(longitude));
        this.flightID =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),flightID);
        this.timeStamp =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), timeStamp);
        this.imageFile = MultipartBody.Part.createFormData("file", "image", RequestBody.create(MediaType.parse("image/*"),imageFile));
    }

    public RequestBody getUserID() {
        return userID;
    }

    public RequestBody getFlightID() {
        return flightID;
    }

    public void setFlightID(RequestBody flightID) {
        this.flightID = flightID;
    }

    public void setUserID(RequestBody userID) {
        this.userID = userID;
    }

    public RequestBody getLatitude() {
        return latitude;
    }

    public void setLatitude(RequestBody latitude) {
        this.latitude = latitude;
    }

    public RequestBody getLongitude() {
        return longitude;
    }

    public void setLongitude(RequestBody longitude) {
        this.longitude = longitude;
    }

    public RequestBody getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(RequestBody timeStamp) {
        this.timeStamp = timeStamp;
    }

    public MultipartBody.Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartBody.Part imageFile) {
        this.imageFile = imageFile;
    }
}
