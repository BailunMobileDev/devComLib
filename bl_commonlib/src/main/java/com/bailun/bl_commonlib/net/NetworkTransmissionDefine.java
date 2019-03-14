package com.bailun.bl_commonlib.net;

/*************************************************
 * Copyright (C), 2017-2021, Bailun Tech. Co., Ltd.
 * File name: BaseActivity.java
 * Author: kingpang   Version: v1.0   Date: 2017/9/27
 * Description: null
 *              1、...
 * Other: null
 * Function List: null
 * History:
 *          kingpang 2017/9/27 v1.0 Create 
 *************************************************/
public class NetworkTransmissionDefine {
    public static class HttpMethod {
        public static final int POST = 1;
        public static final int GET = 2;
        public static final int PUT = 3;
        public static final int DELETE = 4;
        public static final int HEAD = 5;
        public static final int OPTIONS = 6;
    }

    public static class ResponseResult {
        public static final int UNKNOWN = -1;
        public static final int SUCCESS = 0;
        public static final int FAILED = 1;
        public static final int TIME_OUT = 2;
    }
}
