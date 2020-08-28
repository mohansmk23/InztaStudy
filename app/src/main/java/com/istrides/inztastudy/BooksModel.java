package com.istrides.inztastudy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksModel {

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
        @SerializedName("hometaglist")
        @Expose
        private List<Hometaglist> hometaglist = null;

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

        public List<Hometaglist> getHometaglist() {
            return hometaglist;
        }

        public void setHometaglist(List<Hometaglist> hometaglist) {
            this.hometaglist = hometaglist;
        }

        public class Hometaglist {

            @SerializedName("group_auto_id")
            @Expose
            private String groupAutoId;
            @SerializedName("group_name")
            @Expose
            private String groupName;
            @SerializedName("view_type")
            @Expose
            private String viewType;
            @SerializedName("group_details")
            @Expose
            private List<GroupDetail> groupDetails = null;

            public String getGroupAutoId() {
                return groupAutoId;
            }

            public void setGroupAutoId(String groupAutoId) {
                this.groupAutoId = groupAutoId;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getViewType() {
                return viewType;
            }

            public void setViewType(String viewType) {
                this.viewType = viewType;
            }

            public List<GroupDetail> getGroupDetails() {
                return groupDetails;
            }

            public void setGroupDetails(List<GroupDetail> groupDetails) {
                this.groupDetails = groupDetails;
            }

            public class GroupDetail {

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
                @SerializedName("category_id")
                @Expose
                private String categoryId;

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

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

            }


        }

    }


}
