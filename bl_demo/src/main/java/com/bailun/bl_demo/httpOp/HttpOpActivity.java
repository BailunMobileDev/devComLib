package com.bailun.bl_demo.httpOp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bailun.bl_commonlib.callback.CommLibCallback;
import com.bailun.bl_commonlib.net.NetworkTransmissionDefine;
import com.bailun.bl_commonlib.net.NetworkTransmissionUtils;
import com.bailun.bl_commonlib.net.http.HttpRequestParam;
import com.bailun.bl_demo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HttpOpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_op);

        findViewById(R.id.btn_get_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryGetRequest();
            }
        });

        findViewById(R.id.btn_upload_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryUploadFile();
            }
        });

    }

    private void tryUploadFile() {
        HttpRequestParam param = new HttpRequestParam("http://47.107.18.220/appapi/api/postFileUpload", NetworkTransmissionDefine.HttpMethod.POST);

        File file = new File ("/mnt/sdcard/123.png");
        param.addVerify("locallanguage","0");
        param.addBody("file",file,"multipart/form-data");
        param.addQuery("fileType","1");
        NetworkTransmissionUtils.getInstance().httpRequest(param, new CommLibCallback() {

            @Override
            public void onSuccess(Object o) {
                String strMsg = (String) o;
                Log.e("@@@", strMsg);
            }

            @Override
            public void onError(int errCode, String strErrMsg) {
                Log.e("@@@", "err=" + strErrMsg);
            }

            @Override
            public void onCompleted() {
                Log.e("@@@", "http response finished.");
            }
        });
    }

    private void tryGetRequest() {
        HttpRequestParam param = new HttpRequestParam("http://47.107.18.220/appapi/api/getNewsList", NetworkTransmissionDefine.HttpMethod.GET);
        param.addVerify("locallanguage","0");
        param.addQuery("pageIndex", "1");
        param.addQuery("pageSize", "5");

        NetworkTransmissionUtils.getInstance().httpRequest(param, new CommLibCallback() {

            @Override
            public void onSuccess(Object o) {
                String strMsg = (String) o;
                Log.e("@@@", strMsg);
            }

            @Override
            public void onError(int errCode, String strErrMsg) {
                Log.e("@@@", "err=" + strErrMsg);
            }

            @Override
            public void onCompleted() {
                Log.e("@@@", "http response finished.");
            }
        });
    }
}
