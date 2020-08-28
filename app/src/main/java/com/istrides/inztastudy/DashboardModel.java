package com.istrides.inztastudy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DashboardModel {


    @SerializedName("Output")
    @Expose
    private List<Output> output = null;

    public List<Output> getOutput() {
        return output;
    }

    public void setOutput(List<Output> output) {
        this.output = output;
    }

        public class Output {

            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("category_list")
            @Expose
            private List<CategoryList> categoryList = null;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public List<CategoryList> getCategoryList() {
                return categoryList;
            }

            public void setCategoryList(List<CategoryList> categoryList) {
                this.categoryList = categoryList;
            }


            public class CategoryList {

                @SerializedName("category_id")
                @Expose
                private String categoryId;
                @SerializedName("category_name")
                @Expose
                private String categoryName;
                @SerializedName("sub_title")
                @Expose
                private String subTitle;
                @SerializedName("cat_image")
                @Expose
                private String catImage;
                @SerializedName("date")
                @Expose
                private String date;
                @SerializedName("sub_category")
                @Expose
                private String subCategory;

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getCatImage() {
                    return catImage;
                }

                public void setCatImage(String catImage) {
                    this.catImage = catImage;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getSubCategory() {
                    return subCategory;
                }

                public void setSubCategory(String subCategory) {
                    this.subCategory = subCategory;
                }

            }

        }

    }
