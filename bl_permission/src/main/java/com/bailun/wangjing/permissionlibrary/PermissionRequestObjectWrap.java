package com.bailun.wangjing.permissionlibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;

/**
 * create by wangjing on 2019/11/28 0028
 * description:
 */
public interface PermissionRequestObjectWrap {
    @RequiresApi(api = Build.VERSION_CODES.M)
    void requestPermissions(String[] permissions, int requestCode);
    @RequiresApi(api = Build.VERSION_CODES.M)
    boolean shouldShowRequestPermissionRationale(String permission);
    Context getContext();
    String getPackageName();
    void startActivityForResult(Intent intent, int requestCode);
}
