package com.example.apphotel.Searching.Domain;

import com.google.gson.annotations.SerializedName;

public class ImageDetail {
    @SerializedName("id")
    private int id;

    @SerializedName("img")
    private String img;

    @SerializedName("imgType")
    private String imgType;

    public ImageDetail(int id, String img, String imgType) {
        this.id = id;
        this.img = img;
        this.imgType = imgType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }
}
