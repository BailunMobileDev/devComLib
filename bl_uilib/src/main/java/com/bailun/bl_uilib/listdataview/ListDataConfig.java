package com.bailun.bl_uilib.listdataview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * create by wangjing on 2019/6/19 0019
 * description:
 */
public class ListDataConfig {

//    一页的个数
    protected int pageSize = 20;
//    显示拖动高度/真实拖动高度
    protected float dragRate = 1f;
//    refresh最大拖动距离，和footer等距，不可越界拖动
    protected float footerMaxDragRate = 1f;
//    刷新头
    protected RefreshHeader header;
//    刷新尾
    protected RefreshFooter footer;
//    RecyclerView滑动模式
    protected int overScrollMode = RecyclerView.OVER_SCROLL_NEVER;

    public ListDataConfig(Context context) {
        header = new MaterialHeader(context);
        footer = new ClassicsFooter(context);
    }

    public ListDataConfig setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public ListDataConfig setDragRate(float dragRate) {
        this.dragRate = dragRate;
        return this;
    }

    public ListDataConfig setFooterMaxDragRate(float footerMaxDragRate) {
        this.footerMaxDragRate = footerMaxDragRate;
        return this;
    }

    public ListDataConfig setHeader(RefreshHeader header) {
        this.header = header;
        return this;
    }

    public ListDataConfig setFooter(RefreshFooter footer) {
        this.footer = footer;
        return this;
    }

    public ListDataConfig setOverScrollMode(int overScrollMode) {
        this.overScrollMode = overScrollMode;
        return this;
    }
}
