package com.bailun.wangjing.permissionlibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

/**
 * create by wangjing on 2019/11/28 0028
 * description:
 */
public class ActivityPermissionObjectWrap implements PermissionRequestObjectWrap{

    private FragmentActivity activity;

    public ActivityPermissionObjectWrap(FragmentActivity activity) {
        this.activity = activity;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions, int requestCode) {
        activity.requestPermissions(permissions, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return activity.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public Context getContext() {
        return activity;
    }

    @Override
    public String getPackageName() {
        return activity.getPackageName();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }
}
