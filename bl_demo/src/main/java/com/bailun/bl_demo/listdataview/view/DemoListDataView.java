package com.bailun.bl_demo.listdataview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bailun.bl_demo.R;
import com.bailun.bl_uilib.listdataview.ListDataView;


/**
 * create by wangjing on 2019/6/19 0019
 * description:
 */
public class DemoListDataView extends ListDataView {

    public DemoListDataView(Context context) {
        super(context);
    }

    public DemoListDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoListDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View initOtherView() {
        return null;
    }

    @Override
    protected View initBottomView() {
        TextView footView = new TextView(getContext());
        footView.setText("没有更多内容");
        footView.setGravity(Gravity.CENTER);
        footView.setPadding(0, 200, 0, 200);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footView.setLayoutParams(params);
        return footView;
    }

    @Override
    protected int getRetryViewId() {
        return R.id.btn_retry;
    }

    @Override
    protected int getLoadingLayoutId() {
        return R.layout.list_data_layout_loading;
    }

    @Override
    protected int getErrorLayoutId() {
        return R.layout.list_data_layout_error;
    }

    @Override
    protected int getEmptyLayoutId() {
        return R.layout.list_data_layout_empty;
    }
}
