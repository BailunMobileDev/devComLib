package com.bailun.wangjing.permissionlibrary;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bailun.wangjing.permissionlibrary.activity.PermissionRequestActivity;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * create by wangjing on 2018/12/29 0029
 * description: 权限管理的工具类，以后加入其它方法
 */
public class PermissionUtils {

    public static void request(@NonNull Context context, @NonNull String[] permissions
            , int request, @NonNull PermissionResult result){
        ArrayList<String> permissionList = new ArrayList<>(Arrays.asList(permissions));
        PermissionRequestActivity.requestPermission(context, permissionList, request, result);
    }

}
