package com.bailun.bl_demo.listdataview.demo.presenter;



import com.bailun.bl_demo.listdataview.demo.entitiy.TextBean;
import com.bailun.bl_demo.listdataview.demo.interfaces.DemoMVP;
import com.bailun.bl_demo.listdataview.demo.model.ImpDemoModel;

import java.util.List;

public class ImpDemoPresenter implements DemoMVP.IPresenter {
    private final DemoMVP.IView iView;
    private final DemoMVP.IModel iModel = new ImpDemoModel(this);

    private int pageIndex = 1;
    public ImpDemoPresenter(DemoMVP.IView iView) {
        this.iView = iView;
    }

    @Override
    public void v2pGetFirstData() {
        pageIndex = 1;
        iModel.p2mGetDataNet(1);
    }

    @Override
    public void v2pGetMoreData() {
        iModel.p2mGetDataNet(pageIndex + 1);
    }

    @Override
    public void v2pGetDataSuccess(int pageIndex, List<TextBean> beans) {
        this.pageIndex = pageIndex;
        if (pageIndex > 1){
            iView.showMoreData(beans);
        } else {
            iView.showFirstData(beans);
        }
    }

}
