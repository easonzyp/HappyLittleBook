package com.zhangyp.develop.HappyLittleBook.http;


import android.util.Log;

import com.zhangyp.develop.HappyLittleBook.response.UpdateInfoResponse1;
import com.zhangyp.develop.HappyLittleBook.response.UpdateInfoResponse2;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class HttpAction {

    public static HttpAction getInstance() {
        return HttpActionHolder.instance;
    }

    private static class HttpActionHolder {
        private static HttpAction instance = new HttpAction();
    }

    private RequestBody getBody(String request) {
        Log.e("getBody", request);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), request);
    }

    private <T> Flowable<T> applySchedulers(Flowable<T> responseFlowable) {
        return responseFlowable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Flowable<UpdateInfoResponse1> getUpdateInfo1(Map<String, String> params) {
        return applySchedulers(HttpClient.getHttpService().getUpdateInfo1("http://appid.aigoodies.com/getAppConfig.php", params));
    }

    public Flowable<UpdateInfoResponse2> getUpdateInfo2(Map<String, String> params) {
        return applySchedulers(HttpClient.getHttpService().getUpdateInfo2("https://appid-apkk.xx-app.com/frontApi/getAboutUs", params));
    }

}
