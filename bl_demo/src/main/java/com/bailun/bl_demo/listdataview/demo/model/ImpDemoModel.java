package com.bailun.bl_demo.listdataview.demo.model;



import com.bailun.bl_demo.listdataview.demo.entitiy.TextBean;
import com.bailun.bl_demo.listdataview.demo.interfaces.DemoMVP;

import java.util.ArrayList;
import java.util.List;

public class ImpDemoModel implements DemoMVP.IModel {
    private final DemoMVP.IPresenter iPresenter;

    public ImpDemoModel(DemoMVP.IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    @Override
    public void p2mGetDataNet(final int pageIndex) {
        List<TextBean> beans = null;
        switch (pageIndex){
            case 1:
            case 2:
            case 3:
                beans = new ArrayList<>();
                for (int i = 0; i < 20; i ++){
                    beans.add(new TextBean("ListItem_" + (i + (pageIndex - 1) * 20)));
                }
                break;
            default:
                break;
        }
        final List<TextBean> finalBeans = beans;
        new Thread(new Runnable() {
            @Override
            public void run() {
                long l = System.currentTimeMillis();
                int s = (int) (l % 200 + 200);
                try {
                    Thread.sleep(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                iPresenter.v2pGetDataSuccess(pageIndex, finalBeans);
            }
        }).start();
    }
}
