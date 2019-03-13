package com.bailun.localeslibrary.type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by kingpang on 2018/8/21.
 */

public enum  EnvironmentEnum {
    DEBUG,              // 开发环境
    TEST,               // 测试环境
    PRE_RELEASE,       // 预发布环境
    RELEASE;            // 发布环境
}
