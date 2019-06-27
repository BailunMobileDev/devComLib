package com.bailun.bl_demo;

import android.app.Application;

import com.bailun.wangjing.permissionlibrary.AOPRequestPermission;

/**
 * create by wangjing on 2019/6/27 0027
 * description:
 */
public class DemoApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AOPRequestPermission.init(this);
    }
}
