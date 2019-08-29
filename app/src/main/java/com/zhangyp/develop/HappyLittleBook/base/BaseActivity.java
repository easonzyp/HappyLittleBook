package com.zhangyp.develop.HappyLittleBook.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.util.StatusBarHelper;

/**
 * Activity基类
 * <p>
 * Created by zyp on 2018/2/6.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    public LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = this;
        inflater = LayoutInflater.from(this);
        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initParam(getIntent().getExtras());
        }

        StatusBarHelper.getInstance().setStatusBar(this, true,
                ContextCompat.getColor(this, R.color.mainColor));
    }

    public void initParam(Bundle bundle) {

    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            if (imm != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public abstract int getLayoutId();
}