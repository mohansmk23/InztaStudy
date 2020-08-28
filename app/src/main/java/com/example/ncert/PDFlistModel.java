package com.example.ncert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PDFlistModel {


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
        @SerializedName("pdf_list")
        @Expose
        private List<PdfList> pdfList = null;

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

        public List<PdfList> getPdfList() {
            return pdfList;
        }

        public void setPdfList(List<PdfList> pdfList) {
            this.pdfList = pdfList;
        }



        public class PdfList {

            @SerializedName("auto_id")
            @Expose
            private String autoId;
            @SerializedName("content_id")
            @Expose
            private String contentId;
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
            @SerializedName("upload_link")
            @Expose
            private Object uploadLink;
            @SerializedName("upload_file")
            @Expose
            private String uploadFile;
            @SerializedName("date")
            @Expose
            private String date;

            @SerializedName("pdf_name")
            @Expose
            private String  pdf_name;

            public String getPdf_name() {
                return pdf_name;
            }

            public void setPdf_name(String pdf_name) {
                this.pdf_name = pdf_name;
            }

            public String getAutoId() {
                return autoId;
            }

            public void setAutoId(String autoId) {
                this.autoId = autoId;
            }

            public String getContentId() {
                return contentId;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
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

            public Object getUploadLink() {
                return uploadLink;
            }

            public void setUploadLink(Object uploadLink) {
                this.uploadLink = uploadLink;
            }

            public String getUploadFile() {
                return uploadFile;
            }

            public void setUploadFile(String uploadFile) {
                this.uploadFile = uploadFile;
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
