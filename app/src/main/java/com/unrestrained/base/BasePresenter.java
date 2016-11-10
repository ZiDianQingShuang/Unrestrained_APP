package com.unrestrained.base;

import android.support.annotation.NonNull;

/**
 * Created by wangxiaofei on 2016/10/26.
 */
public interface BasePresenter {
    void onCreate();

    void attachView(@NonNull BaseView view);

    void onDestroy();
}
