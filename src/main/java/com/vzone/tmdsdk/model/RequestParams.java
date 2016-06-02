package com.vzone.tmdsdk.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 詹子聪 on 2016/6/2.
 * 请求参数
 */
public class RequestParams {

    private Map<String, String> mParams;

    public RequestParams() {
        mParams = new HashMap<>();
    }

    /**
     * 参数列表
     * @return
     */
    public Map<String, String> getParams() {
        return mParams;
    }

    /**
     * 增加请求参数
     * @param key 参数名
     * @param value 参数名对应的值
     */
    public void addRequestParam(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mParams.put(key, value);
        }
    }
}
