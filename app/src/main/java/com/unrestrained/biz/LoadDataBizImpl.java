package com.unrestrained.biz;

import com.unrestrained.callback.RequestCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaofei on 2016/10/26.
 */

public class LoadDataBizImpl implements LoadDataBiz {
    public void loaddata(int size, int num, RequestCallback<List<String>> callback) {
        if (0 == size || 0 == num) {
            callback.error("size and num cannot equals zero!");
        } else {
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < size * num; i++) {
                stringList.add("num  = " + i);
            }
            callback.success(stringList);
        }
    }
}
