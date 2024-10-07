package com.example.potatoleaf;


public class UserHistoryDetails {


    public UserHistoryDetails(String imageUri, String imageName) {
        this.imageName = imageName;
        this.imageUri = imageUri;


    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }


    private String imageName;
    private String imageUri;
    private Object timestamp;

    public UserHistoryDetails(){

    }

}