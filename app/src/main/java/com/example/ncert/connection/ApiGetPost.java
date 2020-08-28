package com.example.ncert.connection;

import com.example.ncert.BooksModel;
import com.example.ncert.DashboardModel;
import com.example.ncert.PDFlistModel;
import com.example.ncert.ProfileModel;
import com.example.ncert.SubCategoryModel;
import com.example.ncert.SubSubCategoryModel;
import com.example.ncert.ChapterListModel;
import com.example.ncert.VideoListModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiGetPost {

    @POST("category-list")
    Call<DashboardModel> Dashboard(@Body Map<String,Object> body);

    @POST("sub-category-list")
    Call<SubCategoryModel> SubCategory(@Body Map<String,Object> body);

    @POST("sub-sub-category-list")
    Call<SubSubCategoryModel> SubSubCategory(@Body Map<String,Object> body);

    @POST("pdf-list")
    Call<PDFlistModel> Pdflist(@Body Map<String,Object> body);

    @POST("chapter-list")
    Call<ChapterListModel> ChapterList(@Body Map<String,Object> body);

    @POST("video-list")
    Call<VideoListModel> VideoList(@Body Map<String,Object> body);

    @POST("profile-update")
    Call<VideoListModel> UpdateProfile(@Body Map<String,Object> body);

    @POST("profile-retrieve")
    Call<ProfileModel> ProfileRetrieve(@Body Map<String,Object> body);

    @POST("home-tag-list")
    Call<BooksModel> getBooks(@Body Map<String,Object> body);


    @Multipart
    @POST("profile-upload")
    Call<ProfileModel> PicUpload(
                        @Part("profile_pic\"; filename=\"pp.png\" ") RequestBody file, @Part("device_id") RequestBody id);

}
