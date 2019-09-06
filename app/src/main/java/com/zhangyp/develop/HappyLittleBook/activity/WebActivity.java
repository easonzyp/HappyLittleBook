package com.zhangyp.develop.HappyLittleBook.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.listener.DownloadCallBack;
import com.zhangyp.develop.HappyLittleBook.util.DownLoadUtil;
import com.zhangyp.develop.HappyLittleBook.util.InstallApkUtil;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;

import java.io.File;
import java.util.List;

public class WebActivity extends BaseActivity {

    private String url;
    private WebView webView;
    private boolean isCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
        }
    }

    private void initView() {
        webView = findViewById(R.id.wv_content);
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(url);

        WebSettings webSettings = webView.getSettings();
        //不使用缓存，只从网络获取数据.
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(webViewClient);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 用户是否需要通过手势播放媒体(不会自动播放)，默认值 true
            webSettings.setMediaPlaybackRequiresUserGesture(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 是否在离开屏幕时光栅化(会增加内存消耗)，默认值 false
            webSettings.setOffscreenPreRaster(false);
        }
//        if (isNetworkConnected(context)) {
//            // 根据cache-control决定是否从网络上取数据
//            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        } else {
//            // 没网，离线加载，优先加载缓存(即使已经过期)
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimeType, long contentLength) {
                // H5中包含下载链接的话让外部浏览器去处理
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });*/

    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /*if (url.equals("https://bxvip.oss-cn-zhangjiakou.aliyuncs.com/369/369caizy.apk")
                    || url.equals("https://down.nchdbfzx.com/369cai.apk")
                    || url.equals("http://down.updateappdown.com/369caizy.apk")
                    || url.equals("https://www.3698855.com/register?vip=809238")) {
                startDownLoad(url);
            }*/

            return false;
        }


    };

    private void startDownLoad(String fileUrl) {
        if (isWeixinAvilible(WebActivity.this)) {
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
        lUpData.upDataFile(WebActivity.this, fileUrl, new DownloadCallBack() {
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
                });
            }
        });
    }

    private String pag = "com.bxvip.app.bx152zy";

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
    protected void onRestart() {
        super.onRestart();
        if (isCompleted) {
            InstallApkUtil.uninstallApk(WebActivity.this);
            isCompleted = false;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
