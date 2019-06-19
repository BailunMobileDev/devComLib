package com.bailun.bl_demo.listdataview.demo.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bailun.bl_demo.R;
import com.bailun.bl_demo.listdataview.demo.entitiy.TextBean;
import com.bailun.bl_uilib.listdataview.ListDataAdapter;


/**
 * create by wangjing on 2019/6/19 0019
 * description:
 */
public class TextAdapter extends ListDataAdapter<TextAdapter.ViewHolder, TextBean> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_data_view_item_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv.setText(datas.get(i).getContent());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item);
        }
    }
}
