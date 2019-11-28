package com.bailun.wangjing.permissionlibrary;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.ArrayList;

/**
 * create by wangjing on 2019/11/28 0028
 * description: 用于权限请求的Fragment
 */
public class PermissionRequestFragment extends Fragment {

    private ArrayList<String> permissionList;
    private int requestCode;

    private static PermissionResult callback;

    public static PermissionRequestFragment newInstance(@NonNull ArrayList<String> permissions,
                                                        int requestCode,
                                                        @NonNull PermissionResult result){
        callback = result;
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Constant.PERMISSION_LIST_NAME, permissions);
        bundle.putInt(Constant.REQUEST_CODE_NAME, requestCode);
        PermissionRequestFragment fragment = new PermissionRequestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(Constant.TAG, "Fragment request");
        super.onCreate(savedInstanceState);
        permissionList = getArguments().getStringArrayList(Constant.PERMISSION_LIST_NAME);
        requestCode = getArguments().getInt(Constant.REQUEST_CODE_NAME, 0);
        PermissionUtils.requestPermissions(new FragmentPermissionObjectWrap(this), permissionList, requestCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int result = PermissionUtils.getStateOnRequestPermissionsResult(grantResults, new FragmentPermissionObjectWrap(this), permissions);
        if (result == Constant.HAS_PERMISSIONS){
            callback.onAllow();
        } else if (result == Constant.NO_PERMISSIONS){
            callback.onRefuse(requestCode);
        } else {
            callback.onForbid(requestCode);
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }
}
