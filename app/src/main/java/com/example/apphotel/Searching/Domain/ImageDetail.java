package com.example.apphotel.Searching.Domain;

public class ImageDetail {
    private int id;
    private String img;
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
