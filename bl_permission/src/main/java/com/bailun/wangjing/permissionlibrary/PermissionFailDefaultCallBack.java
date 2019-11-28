package com.bailun.wangjing.permissionlibrary;

/**
 * create by wangjing on 2019/11/28 0028
 * description:
 */
public interface PermissionFailDefaultCallBack {
    void onRequestRefuse(int requestCode, String refuseTip);
    void onRequestForbid(int requestCode, String forbidTip);
}
