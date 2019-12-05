package com.bailun.bl_demo.listdataview.demo.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;


import com.bailun.bl_demo.R;
import com.bailun.bl_demo.listdataview.demo.entitiy.TextBean;
import com.bailun.bl_demo.listdataview.demo.interfaces.DemoMVP;
import com.bailun.bl_demo.listdataview.demo.presenter.ImpDemoPresenter;
import com.bailun.bl_demo.listdataview.view.DemoListDataView;
import com.bailun.bl_uilib.listdataview.OnListDataViewEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListDataViewActivity extends FragmentActivity implements DemoMVP.IView, View.OnClickListener{

    private final DemoMVP.IPresenter iPresenter = new ImpDemoPresenter(this);

    private DemoListDataView listDataView;
    private TextAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_view);
        listDataView = findViewById(R.id.ld_content);
        adapter = new TextAdapter();
        listDataView.setLayoutManager(new LinearLayoutManager(this));
        listDataView.setAdapter(adapter);
        listDataView.setListener(new OnListDataViewEventListener() {
            @Override
            public void onRetry() {
                iPresenter.v2pGetFirstData();
            }

            @Override
            public void onLoadMore() {
                iPresenter.v2pGetMoreData();
            }

            @Override
            public void onRefresh() {
                iPresenter.v2pGetFirstData();
            }
        });
        iPresenter.v2pGetFirstData();
        findViewById(R.id.btn_empty).setOnClickListener(this);
        findViewById(R.id.btn_error).setOnClickListener(this);
        findViewById(R.id.btn_no_full).setOnClickListener(this);
    }


    @Override
    public void showFirstData(final List<TextBean> beans) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listDataView.setFirstData(beans);
            }
        });
    }

    @Override
    public void showMoreData(final List<TextBean> beans) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listDataView.addMoreData(beans);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_empty:
                listDataView.setFirstData(null);
                break;
            case R.id.btn_error:
                listDataView.showError();
                break;
            case R.id.btn_no_full:
                List<TextBean> beans = new ArrayList<>();
                for (int i = 0; i < 10; i++ ){
                    beans.add(new TextBean("ListItem_"+i));
                }
                listDataView.setFirstData(beans);
                break;
        }
    }
}
