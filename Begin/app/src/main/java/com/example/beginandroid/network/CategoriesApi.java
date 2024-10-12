package com.example.beginandroid.network;

import com.example.beginandroid.dto.CategoryItemDTO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface CategoriesApi {
    @Multipart
    @POST("/api/category")
    public Call<Void> create(@PartMap Map<String, RequestBody> params,
                             @Part MultipartBody.Part image);

    @GET("/api/Category")
    public Call<List<CategoryItemDTO>> list();
}