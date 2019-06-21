package com.bailun.bl_uilib.listdataview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * create by wangjing on 2019/6/19 0019
 * description:
 */
public abstract class ListDataView extends SmartRefreshLayout {

    private View mLoadingView, mEmptyView, mErrorView, mOtherView;
    private FrameLayout mStateViewParent;
    private WrapRecyclerView wrapRecyclerView;
    private ListDataAdapter mAdapter;
    private View mBottomView;
    private OnListDataViewEventListener listener;
    private ListDataConfig config;

    public ListDataView(Context context) {
        this(context, null);
    }

    public ListDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        initStateView();
        initRecyclerView();
        initRetry();
        setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (listener != null) listener.onLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (listener != null) listener.onRefresh();
            }
        });
        initConfig();
        if (config.isAutoShowLoading) showLoading();
    }

    private void initConfig(){
        config = getDefConfig();
        resetConfig();
    }

    private void resetConfig() {
        setRefreshFooter(config.footer);
        setRefreshHeader(config.header);
        setDragRate(config.dragRate);
        setFooterMaxDragRate(config.footerMaxDragRate);
        wrapRecyclerView.setOverScrollMode(config.overScrollMode);
    }

    private void initStateView() {
        mStateViewParent = new FrameLayout(getContext());
        mStateViewParent.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(mStateViewParent);
        mLoadingView = initLoadingView();
        mEmptyView = initEmptyView();
        mErrorView = initErrorView();
        mOtherView = initOtherView();
        mBottomView = initBottomView();
        mStateViewParent.addView(mLoadingView);
        mStateViewParent.addView(mEmptyView);
        mStateViewParent.addView(mErrorView);
        if (mOtherView != null) mStateViewParent.addView(mOtherView);
    }

    private void initRetry() {
        View v = mErrorView.findViewById(getRetryViewId());
        if (v == null) throw new NullPointerException("ErrorView is no find retryView!");
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (config.isAutoShowLoading) showLoading();
                    listener.onRetry();
                }
            }
        });
    }

    private void initRecyclerView() {
        wrapRecyclerView = new WrapRecyclerView(getContext());
        wrapRecyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mStateViewParent.addView(wrapRecyclerView);
        initRecyclerViewSetting(wrapRecyclerView);
    }

    private void changeView(@ListDataViewType int type) {
        wrapRecyclerView.setVisibility(type == ListDataViewType.Content ? View.VISIBLE : View.GONE);
        mLoadingView.setVisibility(type == ListDataViewType.Loading ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(type == ListDataViewType.Empty ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(type == ListDataViewType.Error ? View.VISIBLE : View.GONE);
        if (mOtherView != null)
            mOtherView.setVisibility(type == ListDataViewType.Other ? View.VISIBLE : View.GONE);
    }

    private View initLoadingView() {
        return LayoutInflater.from(getContext()).inflate(getLoadingLayoutId(), this, false);
    }

    private View initEmptyView() {
        return LayoutInflater.from(getContext()).inflate(getEmptyLayoutId(), this, false);
    }

    private View initErrorView() {
        return LayoutInflater.from(getContext()).inflate(getErrorLayoutId(), this, false);
    }

    public void setAdapter(ListDataAdapter adapter) {
        mAdapter = adapter;
        wrapRecyclerView.setAdapter(mAdapter);
    }

    public ListDataAdapter getAdapter() {
        return mAdapter;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        wrapRecyclerView.setLayoutManager(manager);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return wrapRecyclerView.getLayoutManager();
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        wrapRecyclerView.addItemDecoration(itemDecoration);
    }

    public void setFirstData(List list) {
        if (mAdapter == null) throw new NullPointerException("LisDataAdapter must is noNull!");
        finishRefresh();
        finishLoadMore();
        if (list == null || list.size() == 0) {
            showEmpty();
        } else {
            mAdapter.setDatas(list);
            mAdapter.notifyDataSetChanged();
            checkList(list);
            showList();
        }
    }

    public void addMoreData(List list) {
        if (mAdapter == null) throw new NullPointerException("LisDataAdapter must is noNull!");
        finishRefresh();
        finishLoadMore();
        if (list == null || list.size() == 0) {
            if (config.isAutoShowBottom) wrapRecyclerView.addFootView(mBottomView);
            if (config.isAutoEnableRefreshAndLoadMore) setEnableLoadMore(false);
            showList();
        } else {
            int oldCount = mAdapter.getItemCount();
            mAdapter.getDatas().addAll(list);
            int newCount = mAdapter.getItemCount();
            mAdapter.notifyItemRangeInserted(oldCount, newCount - 1);
            checkList(list);
            showList();
        }
    }

    private void checkList(List list) {
        if (list.size() < config.pageSize) {
            if (config.isAutoShowBottom) wrapRecyclerView.addFootView(mBottomView);
            if (config.isAutoEnableRefreshAndLoadMore) setEnableLoadMore(false);
        } else {
            if (config.isAutoShowBottom) wrapRecyclerView.removeFootView(mBottomView);
            if (config.isAutoEnableRefreshAndLoadMore) setEnableLoadMore(true);
        }
    }

    private void showList() {
        setEnableRefresh(true);
        changeView(ListDataViewType.Content);
    }

    private void showEmpty() {
        setEnableLoadMore(false);
        setEnableRefresh(true);
        changeView(ListDataViewType.Empty);
    }

    private void showLoading() {
        setEnableLoadMore(false);
        setEnableRefresh(false);
        changeView(ListDataViewType.Loading);
    }

    public void showError() {
        finishRefresh();
        finishLoadMore();
        setEnableLoadMore(false);
        setEnableRefresh(false);
        changeView(ListDataViewType.Error);
    }

    public void isShowBottom(boolean isShow){
        if (isShow){
            wrapRecyclerView.addFootView(mBottomView);
        } else {
            wrapRecyclerView.removeFootView(mBottomView);
        }
    }

    public void showOther() {
        finishRefresh();
        finishLoadMore();
        changeView(ListDataViewType.Other);
    }

    public void setConfig(ListDataConfig config) {
        this.config = config;
        resetConfig();
    }

    public ListDataConfig getConfig() {
        return config;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getOtherView() {
        return mOtherView;
    }

    public void setListener(OnListDataViewEventListener listener) {
        this.listener = listener;
    }

    protected void initRecyclerViewSetting(RecyclerView recyclerView){

    }

    protected abstract View initOtherView();

    protected abstract View initBottomView();

    protected abstract int getRetryViewId();

    protected abstract int getLoadingLayoutId();

    protected abstract int getErrorLayoutId();

    protected abstract int getEmptyLayoutId();

    protected ListDataConfig getDefConfig(){
        return new ListDataConfig(getContext());
    }

}
