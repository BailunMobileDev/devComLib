package com.bailun.wangjing.permissionlibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

/**
 * create by wangjing on 2019/11/28 0028
 * description:
 */
public class FragmentPermissionObjectWrap implements PermissionRequestObjectWrap{

    private Fragment fragment;

    public FragmentPermissionObjectWrap(Fragment fragment) {
        this.fragment = fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions, int requestCode) {
        fragment.requestPermissions(permissions, requestCode);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return fragment.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public Context getContext() {
        return fragment.getContext();
    }

    @Override
    public String getPackageName() {
        return fragment.getContext().getPackageName();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
    }

}
