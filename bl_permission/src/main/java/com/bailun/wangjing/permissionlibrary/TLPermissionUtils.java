package com.bailun.wangjing.permissionlibrary;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * create by wangjing on 2018/12/29 0029
 * description: 权限管理的工具类，以后加入其它方法
 */
public class TLPermissionUtils {


    /**
     * 使用Activity去请求权限并且处理
     *
     * @param context     上下文
     * @param permissions 权限列表
     * @param request     请求code
     * @param result      回调接口
     */
    public static void requestByActivity(@NonNull Context context, @NonNull String[] permissions
            , int request, @NonNull PermissionResult result) {
        ArrayList<String> permissionList = new ArrayList<>(Arrays.asList(permissions));
        // 先判断是否有权限, 有的话就不打开activity。效率提升
        if (isHasPermissions(context, permissionList)) {
            result.onAllow();
        } else {
            // 适配6.0以下
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context instanceof FragmentActivity){
                    FragmentActivity activity = (FragmentActivity) context;
                    PermissionRequestFragment fragment = PermissionRequestFragment.newInstance(permissionList, request, result);
                    FragmentManager manager = activity.getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(fragment, Constant.FRAGMENT_NAME);
                    transaction.commit();
                } else {
                    PermissionRequestActivity.requestPermission(context, permissionList, request, result);
                }
            } else {
                result.onForbid(request);
            }
        }
    }


    /**
     * 判断是否有这些权限
     *
     * @param context        上下文
     * @param permissionList 权限列表
     * @return 是否拥有权限
     */
    public static boolean isHasPermissions(Context context, List<String> permissionList) {
        boolean hasPermissions = true;
        for (String permission : permissionList) {
            // 特殊处理悬浮窗权限
            if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission)) {
                if (!hasFloatViewPermission(context)) {
                    hasPermissions = false;
                    break;
                }
            } else {
                int result = ContextCompat.checkSelfPermission(context, permission);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    hasPermissions = false;
                    break;
                }
            }
        }
        return hasPermissions;
    }


    /**
     * 跳转到应用的设置页面
     * @param activity
     */
    public static void goToSetting(Context activity){
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }

    /**
     * 用于回调的时候判断是否拥有权限
     * @param grantResults 回调结果
     * @param wrap 代理类
     * @param permissions 权限数组
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getStateOnRequestPermissionsResult(int[] grantResults, PermissionRequestObjectWrap wrap, String[] permissions) {
        int results = Constant.HAS_PERMISSIONS;
        for (int i = 0; i < grantResults.length ; i++) {
            int grantResult = grantResults[i];
            if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permissions[i])){
                if (!hasFloatViewPermissionOnActivityResult(wrap.getContext())){
                    results = Constant.NO_PERMISSIONS;
                    break;
                }
            } else {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    results = Constant.NO_PERMISSIONS;
                    break;
                }
            }
        }
        //请求的结果会有三种：允许，拒绝，禁止
        if (Constant.HAS_PERMISSIONS != results) {
            boolean isForbid = true;
            for (String permission : permissions) {
                isForbid &= !wrap.shouldShowRequestPermissionRationale(permission);
            }
            if (!isForbid) {
                results = Constant.FORBID_PERMISSIONS;
            } else {
                results = Constant.NO_PERMISSIONS;
            }
        }
        return results;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestPermissions(PermissionRequestObjectWrap wrap, List<String> permissionList, int requestCode) {
        boolean hasFloatView = false;
        List<String> tmpList = new ArrayList<>();
        for (String permission : permissionList){
            if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission)){
                hasFloatView = true;
            } else {
                tmpList.add(permission);
            }
        }
        if (hasFloatView){
            requestFloatViewPermission(wrap);
        }
        if (tmpList.size() > 0){
            String[] permissions = new String[tmpList.size()];
            tmpList.toArray(permissions);
            wrap.requestPermissions(permissions, requestCode);
        }
    }

    private static void requestFloatViewPermission(PermissionRequestObjectWrap wrap) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + wrap.getPackageName()));
        wrap.startActivityForResult(intent, 1);
    }

    /**
     * 判断是否拥有悬浮窗权限
     *
     * @param context 上下文
     * @return 是否拥有权限
     */
    public static boolean hasFloatViewPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            return hasFloatViewPermissionBelowMarshmallow(context);
        }
    }

    /**
     * 判断是否拥有悬浮窗权限在onActivityResult里
     *
     * @param context 上下文
     * @return 是否拥有权限
     */
    public static boolean hasFloatViewPermissionOnActivityResult(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return hasFloatViewPermissionForO(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            return hasFloatViewPermissionBelowMarshmallow(context);
        }
    }

    /**
     * 6.0以下判断是否有权限
     * 理论上6.0以上才需处理权限，但有的国内rom在6.0以下就添加了权限
     * 其实此方式也可以用于判断6.0以上版本，只不过有更简单的canDrawOverlays代替
     *
     * @param context 上下文
     * @return 是否拥有权限
     */
    private static boolean hasFloatViewPermissionBelowMarshmallow(Context context) {
        try {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Method dispatchMethod = AppOpsManager.class.getMethod("checkOp", int.class, int.class, String.class);
            //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
            return AppOpsManager.MODE_ALLOWED == (Integer) dispatchMethod.invoke(
                    manager, 24, Binder.getCallingUid(), context.getApplicationContext().getPackageName());
        } catch (Exception e) {
            Log.e("tag", "hasFloatViewPermissionBelowMarshmallow e:" + e.toString());
            return false;
        }
    }


    /**
     * 用于判断8.0时是否有权限，仅用于OnActivityResult
     * 针对8.0官方bug:在用户授予权限后Settings.canDrawOverlays或checkOp方法判断仍然返回false
     *
     * @param context 上下文
     * @return 是否拥有权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean hasFloatViewPermissionForO(Context context) {
        try {
            WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (mgr == null) {
                return false;
            }
            View viewToAdd = new View(context);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(0, 0,
                    android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ?
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT);
            viewToAdd.setLayoutParams(params);
            mgr.addView(viewToAdd, params);
            mgr.removeView(viewToAdd);
            return true;
        } catch (Exception e) {
            Log.e("tag", "hasFloatViewPermissionForO e:" + e.toString());
            return false;
        }
    }

}
