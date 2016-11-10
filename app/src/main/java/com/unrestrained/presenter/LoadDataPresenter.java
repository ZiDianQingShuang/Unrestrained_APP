package com.unrestrained.presenter;

import android.support.annotation.NonNull;

import com.unrestrained.base.BasePresenter;
import com.unrestrained.base.BaseView;
import com.unrestrained.biz.LoadDataBiz;
import com.unrestrained.biz.LoadDataBizImpl;
import com.unrestrained.callback.RequestCallback;
import com.unrestrained.mvp.view.LoadDataView;

import java.util.List;

/**
 * Created by wangxiaofei on 2016/10/26.
 */

public class LoadDataPresenter implements BasePresenter {

    private LoadDataBiz mLoadDataBiz;

    private LoadDataView mLoadDataView;

    public LoadDataPresenter(LoadDataView loadDataView) {
        this.mLoadDataView = loadDataView;
        this.mLoadDataBiz = new LoadDataBizImpl();
    }

    public void loadData(int size, int num) {
        this.mLoadDataBiz.loaddata(size, num, new RequestCallback<List<String>>() {
            @Override
            public void requestBefore() {
                mLoadDataView.showProgress();
            }

            @Override
            public void success(List<String> strings) {
                mLoadDataView.showData(strings);
            }

            @Override
            public void error(String msg) {
                mLoadDataView.hideProgress();
                mLoadDataView.showMsg(msg);
            }
        });
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(@NonNull BaseView view) {

    }

    @Override
    public void onDestroy() {

    }
}
