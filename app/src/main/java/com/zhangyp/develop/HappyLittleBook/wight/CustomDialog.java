package com.zhangyp.develop.HappyLittleBook.wight;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhangyp.develop.HappyLittleBook.R;

/**
 * Created by zyp on 2019/8/28 0028.
 * class note:
 */

public class CustomDialog extends Dialog {

    //    style引用style样式
    public CustomDialog(Context context) {
        super(context, R.style.DialogTheme);
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
    }
}
