package com.bailun.bl_commonlib.net.http;

import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingpang on 2018/9/30.
 */

public class HttpRequestParam {
    private String m_strUrl = null;
    private int m_nMethod = -1; //http的访问方式

    private String m_strbodyJsonName = "";
    private String m_strbodyJson = null; //http的访问body的属性参数json string 格式
    private Map<String, String> m_map4query = null; //http的访问query的属性参数
    private Map<String, String> m_map4verify = null; //http的访问效验的属性参数 key：value

    private int m_nTimeout = 0;
    private boolean m_bRequestFinished = false;
    private boolean m_bSyncMsg = false;

    public HttpRequestParam(String url, int method){
        this.m_strUrl = url;
        this.m_nMethod = method;
    }

    public void addVerify(String key, String value) {
        if(null == m_map4verify) {
            m_map4verify = new HashMap<>();
        }

        if(null != m_map4verify) {
            m_map4verify.put(key, value);
        }
    }

    public void addQuery(String key, String value) {
        if(null == m_map4query) {
            m_map4query = new HashMap<>();
        }

        if(null != m_map4query) {
            m_map4query.put(key, value);
        }
    }

    public String getUrl(){
        return this.m_strUrl;
    }

    public void addBody(String strContent){
        m_strbodyJson = strContent;
    }

    public void addBody(String strName, String strJsonValue) {
        m_strbodyJsonName = strName;
        addBody(strJsonValue);
    }

    public int getRequestMethod(){
        return m_nMethod;
    }

    public void setTimeout(int timeout){
        this.m_nTimeout = timeout;
    }

    public int getTimeout(){
        return this.m_nTimeout;
    }

    public void setSyncMsg() {
        this.m_bSyncMsg = true;
    }

    public boolean isSyncMsg() {
        return this.m_bSyncMsg;
    }

    public RequestParams getRequestParams()
    {
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
        if (m_strbodyJson != null) {
            //params.setBodyContent(bodyJsonString);
            params.addBodyParameter(m_strbodyJsonName, m_strbodyJson);
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
