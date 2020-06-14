package com.doan.khambacsi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.doan.khambacsi.config.AppConfig;
import com.doan.khambacsi.config.Constants;
import com.doan.khambacsi.model.Account;
import com.google.gson.Gson;

public class AccountUtils {
    // Lớp này sẽ lưu trữ tài khoản offline, để có thể sử dụng ở mọi class mà ko cần intent để gửi liên tục

    // Vì dùng nhiều lần nên ta sẽ sử dụng single instance

    private static AccountUtils accountUtils;
    private SharedPreferences preferences;

    public static AccountUtils getInstance(){
        if (accountUtils == null){
            accountUtils = new AccountUtils();
        }
        return accountUtils;
    }

    private AccountUtils() {

        preferences = AppConfig.getContext().getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE);
    }

    public void setAccount(Account account){
        // Đoạn này mình sẽ lưu đối tượng Account vào SharedPreferences
        // Nhưng SharedPreferences nó ko cho lưu trực tiếp 1 object, nên mình dùng gson để chuyển 1 object thành 1 chuỗi json

        preferences.edit()
                .putString(Constants.KEY_ACCOUNT,account.toString())
                .apply();
    }

    public Account getAccount(){
        // Đoạn này mình sẽ lấy chuỗi json đã lưu từ SharedPreferences ra rồi chuyển sang đối tượng object

        String jsonAccount = preferences.getString(Constants.KEY_ACCOUNT,null);

        if (jsonAccount == null){
            return null;
        }

        return new Gson().fromJson(jsonAccount,Account.class);
    }

    public void logout(){
        preferences.edit()
                .clear()
                .apply();
    }
}
