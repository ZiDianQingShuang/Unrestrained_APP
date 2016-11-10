package com.unrestrained.utils;

import android.os.Looper;

/**
 * Created by wangxiaofei on 2016/11/1.
 */

public class Utils {

    /**
     * 检测当前是否为主线程
     *
     * @return
     */
    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
