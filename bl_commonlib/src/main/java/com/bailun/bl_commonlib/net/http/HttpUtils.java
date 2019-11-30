package com.bailun.bl_commonlib.net.http;

import com.bailun.bl_commonlib.callback.CommLibCallback;
import com.bailun.bl_commonlib.net.NetworkTransmissionDefine;
import com.bailun.bl_commonlib.utils.NetWorkInfoUtils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by kingpang on 2018/10/7.
 */

public class HttpUtils {

    public static Callback.Cancelable asynHttpOp(final HttpRequestParam httpRequestParam, final CommLibCallback callback) {

        //无网络情况，直接返回结果，不进行网络请求
        if (!noNetworkConnectedDispose(callback)) {
            return null;
        }

        RequestParams params = httpRequestParam.getRequestParams();

        Callback.CommonCallback libCallback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (callback != null) {
                    callback.onSuccess(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (x.isDebug()) {
                    //方便定位错误日志的位置
                    ex.printStackTrace();
                }
                if (callback != null) {
                    if (!NetWorkInfoUtils.isNetworkConnected(x.app().getApplicationContext())) {
                        callback.onError(NetworkTransmissionDefine.ResponseResult.NO_NETWORK, "");
                        return;
                    }
                    if (ex instanceof HttpException) {
                        callback.onError(NetworkTransmissionDefine.ResponseResult.HTTP_ERROR, ex.getMessage());
                        return;
                    }
                    callback.onError(NetworkTransmissionDefine.ResponseResult.FAILED, ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
//                    Log.e("@@@checkUpdate","cancel? = "+cex.getMessage());
            }

            @Override
            public void onFinished() {
                if (callback != null) {
                    callback.onCompleted();
                }
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
                cancelable = x.http().request(HttpMethod.PUT, params, libCallback);
                break;
            case NetworkTransmissionDefine.HttpMethod.DELETE:
                cancelable = x.http().request(HttpMethod.DELETE, params, libCallback);
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
        return cancelable;
    }

    public static void syncHttpOp(final HttpRequestParam httpRequestParam, final CommLibCallback callback) {

        //无网络情况，直接返回结果，不进行网络请求
        if (!noNetworkConnectedDispose(callback)) {
            return;
        }

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

            // Log.e("@@@", "result:" + result);
            if (callback != null && !result.isEmpty()) callback.onSuccess(result);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            if (callback != null)
                callback.onError(NetworkTransmissionDefine.ResponseResult.FAILED, throwable.getMessage());
        }

        if (callback != null) callback.onCompleted();
    }


    private static boolean noNetworkConnectedDispose(CommLibCallback callback) {
        if (NetWorkInfoUtils.isNetworkConnected(x.app().getApplicationContext())) {
            return true;
        }
        if (callback != null) {
            callback.onError(NetworkTransmissionDefine.ResponseResult.NO_NETWORK, "");
        }
        return false;
    }
}
