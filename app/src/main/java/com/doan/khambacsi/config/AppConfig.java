package com.doan.khambacsi.config;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;

// Lớp này là lớp Application, nó là lớp chạy đầu tiên của mỗi ứng dụng, mỗi ứng dụng sẽ có 1 lớp này, nó sẽ chạy xuyên suốt theo ứng dụng
// Lớp này là lớp nâng cao
public class AppConfig extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    public static Context getContext(){
        return context;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceID(){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
