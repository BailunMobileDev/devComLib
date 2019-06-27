package com.bailun.wangjing.permissionlibrary.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.bailun.wangjing.permissionlibrary.PermissionResult;

import java.util.ArrayList;

/**
 * create by wangjing on 2018/12/28 0028
 * description: 用来进行权限请求的activity
 */
public class PermissionRequestActivity extends FragmentActivity {

    public static final String PERMISSION_LIST_NAME = "permission_list_name";
    public static final String REQUEST_CODE_NAME = "request_code_name";

    private static PermissionResult callback;
    private ArrayList<String> permissionList;
    private int requestCode;

    public static void requestPermission(@NonNull Context context
            , @NonNull ArrayList<String> permissions, int requestCode, @NonNull PermissionResult result){
        Intent intent = new Intent(context, PermissionRequestActivity.class);
        intent.putExtra(PERMISSION_LIST_NAME, permissions);
        intent.putExtra(REQUEST_CODE_NAME, requestCode);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callback = result;
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionList = getIntent().getStringArrayListExtra(PERMISSION_LIST_NAME);
        requestCode = getIntent().getIntExtra(REQUEST_CODE_NAME, 0);
        requestOrCheckPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callback = null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissions = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                hasPermissions = false;
                break;
            }
        }
        //请求的结果会有三种：允许，拒绝，禁止
        if (hasPermissions){
            if (callback != null) callback.onAllow();
            finish();
        } else {
            boolean isForbid = true;
            for (String permission : permissions){
                isForbid &= !shouldShowRequestPermissionRationale(permission);
            }
            if (!isForbid) {
                if (callback != null) callback.onRefuse(this.requestCode);
            } else {
                if (callback != null) callback.onForbid(this.requestCode);
            }
            finish();
        }
    }

    private void requestOrCheckPermission(){
        //针对6.0以下适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[permissionList.size()];
            permissionList.toArray(permissions);
            requestPermissions(permissions, requestCode);
        } else {
            boolean hasPermissions = true;
            for (String permission : permissionList){
                int result = ContextCompat.checkSelfPermission(this, permission);
                if (result != PackageManager.PERMISSION_GRANTED){
                    hasPermissions = false;
                    break;
                }
            }
            if (hasPermissions){
                callback.onAllow();
            } else {
                callback.onForbid(requestCode);
            }
            finish();
        }
    }
}
