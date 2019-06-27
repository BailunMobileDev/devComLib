package com.bailun.wangjing.permissionlibrary;

/**
 * create by wangjing on 2018/12/29 0029
 * description: 权限请求的回调接口
 */
public interface PermissionResult {
    void onAllow();
    void onRefuse(int request);
    void onForbid(int request);
}
