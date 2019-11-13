package com.bailun.bl_commonlib.net.http.param;

import org.xutils.http.RequestParams;

public class FileBodyParam extends BaseBodyParam {
    Object obj;

    public FileBodyParam(String name, Object obj, String type){
        super(name,type);
        this.obj = obj;
    }

    public void add(RequestParams params) {
        params.addBodyParameter(this.key, obj, this.value);
        params.setMultipart(true);
    }
}
