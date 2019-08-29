package com.zhangyp.develop.HappyLittleBook;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zhangyp.develop.HappyLittleBook.db.DaoMaster;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.util.SpUtil;

import cn.jpush.android.api.JPushInterface;

public class ExampleApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtil.getInstance().init(this);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);    // 初始化 JPush
        initGreenDao();
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "littlebook.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
