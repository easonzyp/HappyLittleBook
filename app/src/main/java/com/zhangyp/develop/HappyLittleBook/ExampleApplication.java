package com.zhangyp.develop.HappyLittleBook;

import android.app.Application;

import com.zhangyp.develop.HappyLittleBook.util.SpUtil;

import cn.jpush.android.api.JPushInterface;

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtil.getInstance().init(this);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);    // 初始化 JPush
    }
}
