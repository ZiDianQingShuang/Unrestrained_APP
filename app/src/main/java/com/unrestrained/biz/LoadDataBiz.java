package com.unrestrained.biz;

import com.unrestrained.callback.RequestCallback;

import java.util.List;

/**
 * Created by wangxiaofei on 2016/10/26.
 */

public interface LoadDataBiz {
    public void loaddata(int size, int num, RequestCallback<List<String>> callback);
}
