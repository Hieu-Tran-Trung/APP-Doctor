package com.doan.khambacsi.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable {

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("password")
    private String password;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("name")
    private String name;

    @SerializedName("position")
    private String position;

    @SerializedName("gender")
    private String gender;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("cmnd")
    private String cmnd;

    @SerializedName("country")
    private String country;

    @SerializedName("nation")
    private String nation;

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    // Khi gọi Account.toString() sẽ trả về 1 chuỗi json
    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
