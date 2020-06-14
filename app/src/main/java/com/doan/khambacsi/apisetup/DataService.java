package com.doan.khambacsi.apisetup;

import com.doan.khambacsi.model.Account;
import com.doan.khambacsi.model.Schedule;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DataService {

    @FormUrlEncoded
    @POST("/login.php")
    Call<Account> getAccount(@Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST("/register.php")
    Call<String> registerAccount(@Field("phoneNumber")  String phoneNumber,
                                 @Field("password")     String password,
                                 @Field("deviceId")     String deviceId,
                                 @Field("name")         String name);

    @FormUrlEncoded
    @POST("/update.php")
    Call<String> updateAccount(@Field("phoneNumber")    String phoneNumber,
                                 @Field("name")         String name,
                                 @Field("gender")       String gender,
                                 @Field("birthday")     String birthday,
                                @Field("cmnd")          String cmnd,
                                @Field("country")       String country,
                                @Field("nation")        String nation,
                                @Field("position")      String position);

    @FormUrlEncoded
    @POST("/addSchedule.php")
    Call<String> addSchedule(@Field("phoneNumber") String phoneNumber,
                             @Field("doctor") String doctor,
                             @Field("time") String time,
                             @Field("room") String room,
                             @Field("hospital") String hospital,
                             @Field("position") String position);

    @FormUrlEncoded
    @POST("/getSchedule.php")
    Call<ArrayList<Schedule>> getSchedule(@Field("phoneNumber") String phoneNumber);
}
