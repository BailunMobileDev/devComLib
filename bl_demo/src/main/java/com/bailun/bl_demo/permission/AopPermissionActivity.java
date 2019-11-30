package com.bailun.bl_demo.permission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bailun.bl_demo.R;
import com.bailun.wangjing.permissionlibrary.annotation.RequestPermission;
import com.bailun.wangjing.permissionlibrary.annotation.RequestPermissionForbid;
import com.bailun.wangjing.permissionlibrary.annotation.RequestPermissionRefuse;

public class AopPermissionActivity extends FragmentActivity implements View.OnClickListener {

    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop_permission);
        Button btnSingle = findViewById(R.id.btn_single);
        Button btnMultiple = findViewById(R.id.btn_multiple);
        tvInfo = findViewById(R.id.tv_info);
        btnSingle.setOnClickListener(this);
        btnMultiple.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_multiple:
                requestMultiplePermission();
                break;
            case R.id.btn_single:
                requestSinglePermission();
                break;
        }
    }

    @RequestPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE}, request = 0)
    private void requestSinglePermission(){
        tvInfo.setText("申请单个权限被允许");
    }

    @RequestPermission(permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.SYSTEM_ALERT_WINDOW}, request = 1)
    private void requestMultiplePermission(){
        tvInfo.setText("申请多个权限被允许");
    }

    @RequestPermissionRefuse
    private void requestPermissionRefuse(int request){
        switch (request){
            case 0:
                tvInfo.setText("申请单个权限被拒绝");
                break;
            case 1:
                tvInfo.setText("申请多个权限被拒绝");
                break;
        }
    }

//    @RequestPermissionForbid
    private void requestPermission(int request){
        switch (request){
            case 0:
                tvInfo.setText("申请单个权限被禁止");
                break;
            case 1:
                tvInfo.setText("申请多个权限被禁止");
                break;
        }
    }

}
