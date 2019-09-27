package com.example.droneapp.model;


import com.google.gson.annotations.SerializedName;

public class Base64Image {
    private String uuid;
    private String imageData;
    /*private String username;
    private String password;*/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    @SerializedName("body")
    private String text;
}
