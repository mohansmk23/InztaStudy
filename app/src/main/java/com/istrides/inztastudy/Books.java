package com.istrides.inztastudy;

public class Books {

    private String id;
    private String name;
    private String subTitle;
    private String date;
    private String catImg;
    private String subCat;

    public Books(String id, String name, String subTitle, String date, String catImg, String subCat) {
        this.id = id;
        this.name = name;
        this.subTitle = subTitle;
        this.date = date;
        this.catImg = catImg;
        this.subCat = subCat;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCatImg() {
        return catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }


}
