package com.zhangyp.develop.HappyLittleBook.request;

import com.zhangyp.develop.HappyLittleBook.base.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:
 */

public class NewsListRequest extends BaseRequest {

    public String key;
    public String type;

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("key", key);
            object.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public String toJsonString() {
        return toJson().toString();
    }
}
