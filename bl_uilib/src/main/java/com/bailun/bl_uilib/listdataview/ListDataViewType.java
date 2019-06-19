package com.bailun.bl_uilib.listdataview;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * create by wangjing on 2019/6/19 0019
 * description:
 */
@IntDef({ListDataViewType.Content, ListDataViewType.Loading, ListDataViewType.Empty
        , ListDataViewType.Error, ListDataViewType.Other})
@Retention(value = RetentionPolicy.SOURCE)
public @interface ListDataViewType {
    int Content = 0;
    int Loading = 1;
    int Empty = 2;
    int Error = 3;
    int Other = 4;
}
