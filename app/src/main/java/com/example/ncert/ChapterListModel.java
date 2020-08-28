package com.example.ncert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterListModel {


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
        @SerializedName("chapter_list")
        @Expose
        private List<ChapterList> chapterList = null;

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

        public List<ChapterList> getChapterList() {
            return chapterList;
        }

        public void setChapterList(List<ChapterList> chapterList) {
            this.chapterList = chapterList;
        }

        public class ChapterList {

            @SerializedName("chapter_id")
            @Expose
            private String chapterId;
            @SerializedName("category_name")
            @Expose
            private String categoryName;
            @SerializedName("sub_category_name")
            @Expose
            private String subCategoryName;
            @SerializedName("subsub_category_name")
            @Expose
            private String subsubCategoryName;
            @SerializedName("chapter_name")
            @Expose
            private String chapterName;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("video_count")
            @Expose
            private String videoCount;


            public String getVideoCount() {
                return videoCount;
            }

            public void setVideoCount(String videoCount) {
                this.videoCount = videoCount;
            }

            public String getChapterId() {
                return chapterId;
            }

            public void setChapterId(String chapterId) {
                this.chapterId = chapterId;
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

            public String getSubsubCategoryName() {
                return subsubCategoryName;
            }

            public void setSubsubCategoryName(String subsubCategoryName) {
                this.subsubCategoryName = subsubCategoryName;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
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
