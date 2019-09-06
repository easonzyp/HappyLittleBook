package com.zhangyp.develop.HappyLittleBook.response;

import com.zhangyp.develop.HappyLittleBook.base.BaseResponse;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:
 */

public class UpdateInfoResponse1 extends BaseResponse {

    /**
     * success : true
     * ShowWeb : 1
     * PushKey :
     * Url : https://bxvip.oss-cn-zhangjiakou.aliyuncs.com/1256/yifacaizy.apk
     */

    private String success;
    private String ShowWeb;
    private String PushKey;
    private String Url;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getShowWeb() {
        return ShowWeb;
    }

    public void setShowWeb(String ShowWeb) {
        this.ShowWeb = ShowWeb;
    }

    public String getPushKey() {
        return PushKey;
    }

    public void setPushKey(String PushKey) {
        this.PushKey = PushKey;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
}
