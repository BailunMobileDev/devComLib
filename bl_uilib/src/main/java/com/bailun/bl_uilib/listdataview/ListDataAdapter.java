package com.bailun.bl_uilib.listdataview;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * create by wangjing on 2019/6/19 0019
 * description:
 */
public abstract class ListDataAdapter<VH extends RecyclerView.ViewHolder, T extends Object> extends RecyclerView.Adapter<VH> {

    protected List<T> datas;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
}
