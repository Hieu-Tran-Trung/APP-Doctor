package com.doan.khambacsi.config;

import com.doan.khambacsi.R;

public class Constants {
    // Đoạn này là lấy tên ứng dụng của mình
    public static final String APP_NAME = AppConfig.getContext().getString(R.string.app_name);
    public static final String KEY_ACCOUNT = "ABC1";
    public static final String KEY_TYPE = "ABC2";
    public static final String KEY_HOSPITAL = "ABC3";

    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_REGISTER = 2;
}
