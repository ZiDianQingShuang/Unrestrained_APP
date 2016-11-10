package com.unrestrained.base;

import android.support.annotation.NonNull;

import com.unrestrained.callback.RequestCallback;

import rx.Subscription;

/**
 * Created by wangxiaofei on 2016/10/26.
 */

public class BasePresentImpl<T extends BaseView, E> implements BasePresenter, RequestCallback<E> {

    private T mView;

    private Subscription subscription;

    @Override
    public void onCreate() {
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mView = (T) view;
    }

    @Override
    public void onDestroy() {
        if(null != subscription && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    @Override
    public void requestBefore() {
        mView.showProgress();
    }

    @Override
    public void success(E e) {
        mView.hideProgress();
    }

    @Override
    public void error(String msg) {
        mView.hideProgress();
        mView.showMsg(msg);
    }
}
