package com.bailun.bl_commonlib.net;

import com.bailun.bl_commonlib.callback.CommLibCallback;
import com.bailun.bl_commonlib.net.http.HttpRequestParam;
import com.bailun.bl_commonlib.net.http.HttpUtils;


public class NetworkTransmissionUtils {
    private static NetworkTransmissionUtils m_Instance = null;

    public static NetworkTransmissionUtils getInstance() {
        if (null == m_Instance) {
            synchronized (NetworkTransmissionUtils.class) {
                if (null == m_Instance) {
                    m_Instance = new NetworkTransmissionUtils();
                }
            }
        }

        return m_Instance;
    }

    /**
     * Http请求
     *
     * @param httpRequestParam 请求的参数
     * @param callback         调用者数据响应接口
     */
    public void httpRequest(final HttpRequestParam httpRequestParam, final CommLibCallback callback) {
        if (httpRequestParam != null) {
            if (httpRequestParam.isSyncMsg()) {
                HttpUtils.syncHttpOp(httpRequestParam, callback);
            } else {
                HttpUtils.asynHttpOp(httpRequestParam, callback);
            }
        }
    }

    public void imRequest(String strSendToIP, String strMessage) {
    }
}
