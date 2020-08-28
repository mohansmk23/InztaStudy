package com.example.ncert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryModel {

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
        @SerializedName("sub_category_list")
        @Expose
        private List<SubCategoryList> subCategoryList = null;

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

        public List<SubCategoryList> getSubCategoryList() {
            return subCategoryList;
        }

        public void setSubCategoryList(List<SubCategoryList> subCategoryList) {
            this.subCategoryList = subCategoryList;
        }


        public class SubCategoryList {

            @SerializedName("sub_category_id")
            @Expose
            private String subCategoryId;
            @SerializedName("category_name")
            @Expose
            private String categoryName;
            @SerializedName("sub_category_name")
            @Expose
            private String subCategoryName;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("sub_sub_category")
            @Expose
            private String subSubCategory;

            public String getSubCategoryId() {
                return subCategoryId;
            }

            public void setSubCategoryId(String subCategoryId) {
                this.subCategoryId = subCategoryId;
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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSubSubCategory() {
                return subSubCategory;
            }

            public void setSubSubCategory(String subSubCategory) {
                this.subSubCategory = subSubCategory;
            }

        }

    }

}
