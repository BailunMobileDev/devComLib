package com.bailun.bl_demo.listdataview.demo.interfaces;


import com.bailun.bl_demo.listdataview.demo.entitiy.TextBean;

import java.util.List;

public interface DemoMVP {
    interface IView {
        void showFirstData(List<TextBean> beans);
        void showMoreData(List<TextBean> beans);
    }
    interface IPresenter {
        void v2pGetFirstData();
        void v2pGetMoreData();
        void v2pGetDataSuccess(int pageIndex, List<TextBean> beans);
    }
    interface IModel {
        void p2mGetDataNet(int pageIndex);
    }
}
