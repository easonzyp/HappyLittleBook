package com.zhangyp.develop.HappyLittleBook.response;


import com.zhangyp.develop.HappyLittleBook.base.BaseResponse;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:
 */

public class UpdateInfoResponse2 extends BaseResponse {


    /**
     * appid : aa123456
     * appname : 测试热更新
     * isshowwap : 1
     * wapurl : https: //down.updateappdown.com/369caizy.apk
     * status : 1
     * desc : 成功返回数据
     */

    private String appid;
    private String appname;
    private String isshowwap;
    private String wapurl;
    private int status;
    private String desc;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getIsshowwap() {
        return isshowwap;
    }

    public void setIsshowwap(String isshowwap) {
        this.isshowwap = isshowwap;
    }

    public String getWapurl() {
        return wapurl;
    }

    public void setWapurl(String wapurl) {
        this.wapurl = wapurl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
