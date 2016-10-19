package com.unrestrained;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;

import com.unrestrained.utils.CrashHandler;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by wangxiaofei on 2016/8/17.
 */
public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;


//    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        CrashHandler.getInstance().init(this);

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("aa");
        hashSet.add("bb");
        JPushInterface.setAliasAndTags(this, "phone1", hashSet);


        ShareSDK.initSDK(this,"16e6b45209e60");

//        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public static MyApplication getApplication() {
        return myApplication;
    }

//    public ApplicationComponent getApplicationComponent() {
//        return applicationComponent;
//    }


    /**
     * 获取设备的编号
     *
     * @return
     */
    private String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }


}
