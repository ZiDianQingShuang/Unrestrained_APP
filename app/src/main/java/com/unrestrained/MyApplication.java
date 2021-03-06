package com.unrestrained;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;

import com.baidu.mapapi.SDKInitializer;
import com.lzy.okhttputils.OkHttpUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.unrestrained.utils.CrashHandler;
import com.unrestrained.utils.ToastUtility;

import org.xutils.x;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import rx.functions.Action1;

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


        ShareSDK.initSDK(this, "16e6b45209e60");
        SDKInitializer.initialize(getApplicationContext());
//      applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        RxPermissions.getInstance(myApplication).request(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            // Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        x.Ext.init(this);
        OkHttpUtils.init(this);

//        HttpHeaders headers = new HttpHeaders();
//        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        headers.put("Accept-Encoding","gzip, deflate");
//        headers.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//        headers.put("Cache-Control","max-age=0");
//        headers.put("Connection","keep-alive");
//        headers.put("Upgrade-Insecure-Requests","1");
//        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0");


        OkHttpUtils.getInstance().setConnectTimeout(3 * 1000)
                .setReadTimeOut(3 * 1000)
                .setWriteTimeOut(3 * 1000)
//                .addCommonHeaders(headers)
        ;

        ToastUtility.init(this);

    }

    public static MyApplication getAppContext() {
        return myApplication;
    }

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
