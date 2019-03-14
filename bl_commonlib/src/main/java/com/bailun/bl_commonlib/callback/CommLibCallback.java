package com.bailun.bl_commonlib.callback;

/*************************************************
 * Copyright (C), 2017-2021, Bailun Tech. Co., Ltd.
 * File name: CommLibCallback.java
 * Author: kingpang   Version: v1.0   Date: 2017/9/26
 * Description: app与comLib交互，app收到comLib的反馈
 * Function List:
 *          onSuccess - 操作成功，根据成功消息来处理
 *          onError - 操作错误
 *          onCompleted - 操作结束（无论成功、失败都会走一次这个函数，表示调用周期结束）
 * History:
 *          kingpang 2017/9/26 v1.0 Create
 *************************************************/
public interface CommLibCallback<T>
{
    public void onSuccess(T t);
    public void onError(int errCode, String strErrMsg);
    public void onCompleted();
}
