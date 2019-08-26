package com.zhangyp.develop.HappyLittleBook.util;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.zhangyp.develop.HappyLittleBook.listener.CallBackListener;

import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.HttpException;


public class ErrorUtils {

    private static final int RESPONSE_FATAL_EOR = -1;  //返回数据失败,严重的错误
    private static final String TAG = ErrorUtils.class.getSimpleName();

    public static void paserError(Throwable t, CallBackListener errorCallBack) {
        int errorCode;
        String errorMsg;
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
            Log.e(TAG, errorCode + "\n" + errorMsg + "\n");
            if (errorCode >= 500) {
                errorCode = RESPONSE_FATAL_EOR;
                errorMsg = "服务器错误";
            } else {
                try {
                    JSONObject object = new JSONObject(httpException.response().errorBody().string());
                    errorMsg = object.getString("message");

                } catch (Exception e) {
                    e.printStackTrace();
                    errorMsg = "网络链接异常，请稍后尝试";
                }
            }
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof UnknownHostException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "没有网络，请检查网络连接";
        } else if (t instanceof NetworkOnMainThreadException) {//主线程不能网络请求，这个很容易发现
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "主线程不能网络请求";
        } else if (t instanceof RuntimeException) { //很多的错误都是extends RuntimeException
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "运行时错误";
        } else {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "未知的服务器错误";
        }
        if (null != errorCallBack) {
            errorCallBack.onError(errorCode, errorMsg);
        }
    }
}
