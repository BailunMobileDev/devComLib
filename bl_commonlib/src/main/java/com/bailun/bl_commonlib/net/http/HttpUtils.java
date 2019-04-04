package com.bailun.bl_commonlib.net.http;

import android.util.Log;

import com.bailun.bl_commonlib.callback.CommLibCallback;
import com.bailun.bl_commonlib.net.NetworkTransmissionDefine;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by kingpang on 2018/10/7.
 */

public class HttpUtils {

    public static void asynHttpOp(final HttpRequestParam httpRequestParam, final CommLibCallback callback) {

        Log.e("@@@","--------------- 异步消息 ------------------");

        RequestParams params = httpRequestParam.getRequestParams();

        Callback.CommonCallback libCallback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (callback != null) callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (callback != null)
                    callback.onError(NetworkTransmissionDefine.ResponseResult.FAILED, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
//                    Log.e("@@@checkUpdate","cancel? = "+cex.getMessage());
            }

            @Override
            public void onFinished() {
                if (callback != null) callback.onCompleted();
                httpRequestParam.setRequestFinishResult(true);
            }
        };

        Callback.Cancelable cancelable = null;
        switch (httpRequestParam.getRequestMethod()) {
            case NetworkTransmissionDefine.HttpMethod.GET:
                cancelable = x.http().get(params, libCallback);
                break;
            case NetworkTransmissionDefine.HttpMethod.POST:
                cancelable = x.http().post(params, libCallback);
                break;
            case NetworkTransmissionDefine.HttpMethod.PUT:
                cancelable = x.http().request(HttpMethod.PUT,params,libCallback);
                break;
            case NetworkTransmissionDefine.HttpMethod.DELETE:
                cancelable = x.http().request(HttpMethod.DELETE,params,libCallback);
                break;
            default:
                break;
        }

        if (httpRequestParam.getTimeout() > 0) {
            final Callback.Cancelable finalCancelable = cancelable;
            x.task().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (httpRequestParam.isRequestFinished()) {
                        //Log.e("@@@checkupdate",httpRequestParam.getUrl()+" is finished");
                    } else {
                        //Log.e("@@@checkupdate",httpRequestParam.getUrl()+" need finish by self");
                        if (finalCancelable != null) {
                            finalCancelable.cancel();
                            //Log.e("@@@checkupdate",httpRequestParam.getUrl()+" cancel...");
                            if (callback != null) {
                                callback.onError(NetworkTransmissionDefine.ResponseResult.TIME_OUT, httpRequestParam.getUrl() + " Http request cancelled by timeout.[" + httpRequestParam.getTimeout() + "]s");
                            }
                        }
                    }
                }
            }, httpRequestParam.getTimeout());
        }
    }

    public static void syncHttpOp(final HttpRequestParam httpRequestParam, final CommLibCallback callback) {

        Log.e("@@@","--------------- 同步消息 ------------------");

        //同步线程中去访问网路，必须要在子线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = httpRequestParam.getRequestParams();
                try {
                    String result = "";
                    switch (httpRequestParam.getRequestMethod()) {
                        case NetworkTransmissionDefine.HttpMethod.GET:
                            result = x.http().getSync(params, String.class);
                            break;
                        case NetworkTransmissionDefine.HttpMethod.POST:
                            result = x.http().postSync(params, String.class);
                            break;
                        default:
                            break;
                    }

                    Log.e("@@@", "result:" + result);
                    if (callback != null && !result.isEmpty()) callback.onSuccess(result);

                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    if (callback != null)
                        callback.onError(NetworkTransmissionDefine.ResponseResult.FAILED, throwable.getMessage());
                }

                if (callback != null) callback.onCompleted();
            }
        }).start();

    }
}
