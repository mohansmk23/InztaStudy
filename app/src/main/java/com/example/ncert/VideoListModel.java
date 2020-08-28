package com.example.ncert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoListModel {

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
        @SerializedName("video_list")
        @Expose
        private List<VideoList> videoList = null;

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

        public List<VideoList> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoList> videoList) {
            this.videoList = videoList;
        }
        public class VideoList {

            @SerializedName("auto_id")
            @Expose
            private String autoId;
            @SerializedName("video_id")
            @Expose
            private String videoId;
            @SerializedName("chapter_name")
            @Expose
            private String chapterName;
            @SerializedName("upload_video")
            @Expose
            private String uploadVideo;
            @SerializedName("video_duration")
            @Expose
            private String videoDuration;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("video_name")
            @Expose
            private String videoName;

            public String getVideoName() {
                return videoName;
            }

            public void setVideoName(String videoName) {
                this.videoName = videoName;
            }

            public String getAutoId() {
                return autoId;
            }

            public void setAutoId(String autoId) {
                this.autoId = autoId;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public String getUploadVideo() {
                return uploadVideo;
            }

            public void setUploadVideo(String uploadVideo) {
                this.uploadVideo = uploadVideo;
            }

            public String getVideoDuration() {
                return videoDuration;
            }

            public void setVideoDuration(String videoDuration) {
                this.videoDuration = videoDuration;
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
