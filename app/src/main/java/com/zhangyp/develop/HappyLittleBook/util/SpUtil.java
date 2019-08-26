package com.zhangyp.develop.HappyLittleBook.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public final class SpUtil {
    private final static String WEATHER_JSON = "weather_json";

    private static volatile SpUtil instance = null;

    public static SpUtil getInstance() {
        if (instance == null) {
            synchronized (SpUtil.class) {
                if (instance == null) {
                    instance = new SpUtil();
                }
            }
        }
        return instance;
    }

    private SharedPreferences sp;

    public void init(Context context) {
        String SP_NAME = context.getPackageName() + "_sp";
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public String getWeather() {
        String weather = sp.getString(WEATHER_JSON, "-1");
        if (TextUtils.isEmpty(weather)) {
            return null;
        }
        return weather;
    }

    public void setWeather(String weatherJson) {
        sp.edit().putString(WEATHER_JSON, weatherJson).apply();
    }

}
