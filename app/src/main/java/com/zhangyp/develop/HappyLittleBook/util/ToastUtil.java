package com.zhangyp.develop.HappyLittleBook.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangyp.develop.HappyLittleBook.R;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:
 */

public class ToastUtil {

    private static Toast toast;

    public static void showSuccessToast(Context context, String text) {
        if (toast == null) {
            toast = new Toast(context);
        }
        View layout = View.inflate(context, R.layout.success_toast_view, null);
        TextView tv_toast = layout.findViewById(R.id.tv_toast);
        tv_toast.setText(text);
        toast.setView(layout);
        toast.show();
    }

    public static void showWarningToast(Context context, String text) {
        if (toast == null) {
            toast = new Toast(context);
        }
        View layout = View.inflate(context, R.layout.warning_toast_view, null);
        TextView tv_toast = layout.findViewById(R.id.tv_toast);
        tv_toast.setText(text);

        toast.setView(layout);
        toast.show();
    }

    public static void showNormalToast(Context context, String text) {
        if (toast == null) {
            toast = new Toast(context);
        }
        View layout = View.inflate(context, R.layout.normal_toast_view, null);
        TextView tv_toast = layout.findViewById(R.id.tv_toast);
        tv_toast.setText(text);

        toast.setView(layout);
        toast.show();
    }
}
