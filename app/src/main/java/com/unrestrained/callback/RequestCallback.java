package com.unrestrained.callback;

/**
 * Created by wangxiaofei on 2016/10/26.
 */

public interface RequestCallback<T> {
    void requestBefore();

    void success(T t);

    void error(String msg);
}
