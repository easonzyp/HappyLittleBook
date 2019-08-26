package com.zhangyp.develop.HappyLittleBook.listener;

import java.io.IOException;

public interface CallBackListener<T> {
    void onSuccess(T response) throws IOException;
    void onError(int code, String message);
}
