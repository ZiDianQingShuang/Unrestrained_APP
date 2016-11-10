package com.unrestrained;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.unrestrained.utils.CrashHandler;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by wangxiaofei on 2016/8/17.
 */
public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;

//    private BDLocation mBDLocation;


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
        initBaiduBap();
        startBaiduMapScan();
    }

    public static MyApplication getAppContext() {
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


    /**
     * 手机自带的GPS坐标 -> 百度GPS坐标
     *
     * @param lat
     * @param lng
     * @return
     */
    private LatLng gps2BaiduGPS(double lat, double lng) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        LatLng latLng = new LatLng(lat, lng);
        converter = converter.coord(latLng);
        return converter.convert();
    }

    private LocationClient mLocationClient;

    private void initBaiduBap() {
        mLocationClient = new LocationClient(myApplication);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType(BDLocation.BDLOCATION_GCJ02_TO_BD09LL);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setScanSpan(1000 * 10);


        mLocationClient.setLocOption(option);

        mLocalBDLocationListener = new LocalBDLocationListener();

        mLocationClient.registerLocationListener(mLocalBDLocationListener);
    }

    private LocalBDLocationListener mLocalBDLocationListener = null;

    private static class LocalBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            double latitude = bdLocation.getLatitude();
            double longitude = bdLocation.getLongitude();
            double altitude = bdLocation.getAltitude();

            switch (bdLocation.getLocType()) {
                case 161:

                    break;
                case 162:

                    break;
            }
            System.out.printf("latitude = %s,longtitude = %s,altitude  = %s", latitude, longitude, altitude);
        }
    }

    /**
     * 开始百度地图的搜索
     */
    public void startBaiduMapScan() {
        if (null != mLocationClient) {
            mLocationClient.start();
            if (null == mLocalBDLocationListener) {
                mLocationClient.registerLocationListener(mLocalBDLocationListener);
            }
        }
    }


    /**
     * 停止百度地图的搜索
     */
    public void stopBaiduMapScan() {
        if (null != mLocationClient) {
            if (null != mLocalBDLocationListener) {
                mLocationClient.unRegisterLocationListener(mLocalBDLocationListener);
                mLocalBDLocationListener = null;
            }
            mLocationClient.stop();
        }
    }


}
