package com.bailun.bl_commonlib.net.http.param;

import org.xutils.http.RequestParams;

public class BaseBodyParam {
    String key;
    String value;

    public BaseBodyParam(String name, String value) {
        this.key = name;
        this.value = value;
    }

    public void add(RequestParams params) {
        params.addBodyParameter(this.key, this.value);
    }
}
