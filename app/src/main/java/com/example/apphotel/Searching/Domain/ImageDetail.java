package com.example.apphotel.Searching.Domain;

import com.google.gson.annotations.SerializedName;

public class ImageDetail {
    @SerializedName("id")
    private int id;

    @SerializedName("img")
    private String img;

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    @SerializedName("hotelImage")
    private String hinh;

    public ImageDetail(int id, String img, String hinh) {
        this.id = id;
        this.img = img;
        this.hinh = hinh;
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


}
