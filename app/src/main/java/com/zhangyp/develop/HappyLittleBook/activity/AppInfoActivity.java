package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:APP信息页面
 */

public class AppInfoActivity extends BaseActivity {

    private TextView tv_qq;
    private TextView tv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClick();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_info;
    }

    private void initView() {
        tv_qq = findViewById(R.id.tv_qq);
        tv_back = findViewById(R.id.tv_back);
    }

    private void initClick() {
        tv_back.setOnClickListener(v -> finish());

        tv_qq.setOnLongClickListener(v -> {
            copy();
            return false;
        });
    }

    private void copy() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", "427805156");
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }
        ToastUtil.showSuccessToast(context, "QQ号已复制到剪切板");
    }
}
