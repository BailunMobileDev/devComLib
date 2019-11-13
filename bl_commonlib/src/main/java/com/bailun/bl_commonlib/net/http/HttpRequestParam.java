package com.bailun.bl_commonlib.net.http;

import com.bailun.bl_commonlib.net.http.param.BaseBodyParam;
import com.bailun.bl_commonlib.net.http.param.FileBodyParam;

import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingpang on 2018/9/30.
 */

public class HttpRequestParam {
    private String m_strUrl = null;
    private int m_nMethod = -1; //http的访问方式

    private List<BaseBodyParam> m_ls4Body = null;
    private Map<String, String> m_map4query = null; //http的访问query的属性参数
    private Map<String, String> m_map4verify = null; //http的访问效验的属性参数 key：value

    private int m_nTimeout = 0;
    private boolean m_bRequestFinished = false;
    private boolean m_bSyncMsg = false;
    private int m_nMaxRequestLen = 0;

    public HttpRequestParam(String url, int method) {
        this.m_strUrl = url;
        this.m_nMethod = method;
    }

    public void addVerify(String key, String value) {
        if (null == m_map4verify) {
            m_map4verify = new HashMap<>();
        }

        if (null != m_map4verify) {
            m_map4verify.put(key, value);
        }
    }

    public void addQuery(String key, String value) {
        if (null == m_map4query) {
            m_map4query = new HashMap<>();
        }

        if (null != m_map4query) {
            m_map4query.put(key, value);
        }
    }

    public String getUrl() {
        return this.m_strUrl;
    }

    private void initBody() {
        if ( null == m_ls4Body) {
            m_ls4Body = new ArrayList<>();
        }
    }

    public void addBody(String strContent) {
        initBody();
        if(null != m_ls4Body){
            m_ls4Body.add(new BaseBodyParam(null,strContent));
        }
    }

    public void addBody(String strName, String strJsonValue) {
        initBody();
        if(null != m_ls4Body) {
            m_ls4Body.add(new BaseBodyParam(strName, strJsonValue));
        }
    }

    public void addBody(String strName, Object object, String strType) {
        initBody();
        if(null != m_ls4Body) {
            m_ls4Body.add(new FileBodyParam(strName, object,strType));
        }
    }

    public int getRequestMethod() {
        return m_nMethod;
    }

    public void setTimeout(int timeout) {
        this.m_nTimeout = timeout;
    }

    public int getTimeout() {
        return this.m_nTimeout;
    }

    public void setSyncMsg() {
        this.m_bSyncMsg = true;
    }

    public boolean isSyncMsg() {
        return this.m_bSyncMsg;
    }

    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams(m_strUrl);

//        if(this.nTimeout > 0) {
//            params.setConnectTimeout(this.nTimeout);
//            params.setReadTimeout(this.nTimeout);
//            params.setMaxRetryCount(0);
//            params.setUseCookie(false);
//            //params.setCacheMaxAge(0);
//        } else {
//            params.setConnectTimeout(1000*15);
//           params.setReadTimeout(1000*15);
//            params.setMaxRetryCount(2);
//            //params.setCacheMaxAge(1000);
//        }

        //params.setAsJsonContent(true);
        if (m_ls4Body != null) {
            for (BaseBodyParam it : m_ls4Body) {
                it.add(params);
            }
        }

        if (m_map4query != null) {
            for (String key : m_map4query.keySet()) {
                params.addQueryStringParameter(key, m_map4query.get(key));
            }
        }

        if (m_map4verify != null) {
            for (String key : m_map4verify.keySet()) {
                params.setHeader(key, m_map4verify.get(key));
            }
        }

        return params;
    }

    public boolean isRequestFinished() {
        return m_bRequestFinished;
    }

    public void setRequestFinishResult(boolean bFinished) {
        m_bRequestFinished = bFinished;
    }

}
