package com.bailun.wangjing.permissionlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;


/**
 * create by wangjing on 2018/12/28 0028
 * description: 用来进行权限请求的activity
 */
public class PermissionRequestActivity extends FragmentActivity {


    private static PermissionResult callback;
    private ArrayList<String> permissionList;
    private int requestCode;

    public static void requestPermission(@NonNull Context context,
                                         @NonNull ArrayList<String> permissions,
                                         int requestCode,
                                         @NonNull PermissionResult result) {
        Intent intent = new Intent(context, PermissionRequestActivity.class);
        intent.putExtra(Constant.PERMISSION_LIST_NAME, permissions);
        intent.putExtra(Constant.REQUEST_CODE_NAME, requestCode);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callback = result;
        context.startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constant.TAG, "Activity request");
        permissionList = getIntent().getStringArrayListExtra(Constant.PERMISSION_LIST_NAME);
        requestCode = getIntent().getIntExtra(Constant.REQUEST_CODE_NAME, 0);
        PermissionUtils.requestPermissions(new ActivityPermissionObjectWrap(this), permissionList, requestCode);
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
        int result = PermissionUtils.getStateOnRequestPermissionsResult(grantResults, new ActivityPermissionObjectWrap(this), permissions);
        if (result == Constant.HAS_PERMISSIONS) {
            callback.onAllow();
        } else if (result == Constant.NO_PERMISSIONS) {
            callback.onRefuse(requestCode);
        } else {
            callback.onForbid(requestCode);
        }
        finish();
    }

}
