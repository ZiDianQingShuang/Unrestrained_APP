package com.unrestrained.application;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.unrestrained.utils.CrashHandler;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wangxiaofei on 2016/8/17.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("aa");
        hashSet.add("bb");
        JPushInterface.setAliasAndTags(this,"phone1",hashSet);
    }


    /**
     * 获取设备的编号
     * @return
     */
    private String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }


}
