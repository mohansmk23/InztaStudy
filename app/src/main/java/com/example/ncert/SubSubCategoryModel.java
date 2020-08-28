package com.example.ncert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubSubCategoryModel {


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
        @SerializedName("sub_sub_category_list")
        @Expose
        private List<SubSubCategoryList> subSubCategoryList = null;

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

        public List<SubSubCategoryList> getSubSubCategoryList() {
            return subSubCategoryList;
        }

        public void setSubSubCategoryList(List<SubSubCategoryList> subSubCategoryList) {
            this.subSubCategoryList = subSubCategoryList;
        }
        public class SubSubCategoryList {

            @SerializedName("sub_sub_category_id")
            @Expose
            private String subSubCategoryId;
            @SerializedName("category_name")
            @Expose
            private String categoryName;
            @SerializedName("sub_category_name")
            @Expose
            private String subCategoryName;
            @SerializedName("sub_sub_category_name")
            @Expose
            private String subSubCategoryName;
            @SerializedName("date")
            @Expose
            private String date;

            public String getSubSubCategoryId() {
                return subSubCategoryId;
            }

            public void setSubSubCategoryId(String subSubCategoryId) {
                this.subSubCategoryId = subSubCategoryId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getSubCategoryName() {
                return subCategoryName;
            }

            public void setSubCategoryName(String subCategoryName) {
                this.subCategoryName = subCategoryName;
            }

            public String getSubSubCategoryName() {
                return subSubCategoryName;
            }

            public void setSubSubCategoryName(String subSubCategoryName) {
                this.subSubCategoryName = subSubCategoryName;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

        }

    }




}
