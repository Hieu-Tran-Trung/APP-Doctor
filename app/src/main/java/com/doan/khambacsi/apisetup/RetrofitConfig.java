package com.doan.khambacsi.apisetup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://khambacsi.000webhostapp.com/";

    public static Retrofit getInstance() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClient.Builder().build())
                    .build();
        }
        return retrofit;
    }
}
