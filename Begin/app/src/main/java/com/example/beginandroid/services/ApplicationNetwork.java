package com.example.beginandroid.services;

import com.example.simba.constants.Urls;
import com.example.beginandroid.network.CategoriesApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationNetwork {
    private static ApplicationNetwork instance;

    private Retrofit retrofit;

    public ApplicationNetwork() {
        HttpLoggingInterceptor interceptor =  new  HttpLoggingInterceptor ();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

        OkHttpClient.Builder client =  new  OkHttpClient.Builder ()
                .addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static ApplicationNetwork getInstance() {
        if(instance==null)
            instance = new ApplicationNetwork();
        return instance;
    }

    public CategoriesApi getCategoriesApi() {
        return retrofit.create(CategoriesApi.class);
    }
}