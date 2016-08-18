package com.unrestrained.application;

import android.app.Application;

import com.unrestrained.utils.CrashHandler;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wangxiaofei on 2016/8/17.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
}
