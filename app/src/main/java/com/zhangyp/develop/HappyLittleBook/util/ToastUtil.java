package com.zhangyp.develop.HappyLittleBook.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:
 */

public class ToastUtil {

    public static void showShortToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

}
