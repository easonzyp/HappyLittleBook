package com.zhangyp.develop.HappyLittleBook.base;

import java.io.Serializable;

/**
 * Created by zyp on 2018/1/16.
 * response 基类
 */

public class BaseResponse implements Serializable {
    private int error_code;
    private String reason;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

