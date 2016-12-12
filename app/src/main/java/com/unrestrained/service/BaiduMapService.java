package com.unrestrained.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

/**
 * 记录百度地图的坐标
 */
public class BaiduMapService extends Service {
    private LocationClient mLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        initBaiduBap();
    }

    private void initBaiduBap() {
        mLocationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setCoorType(BDLocation.BDLOCATION_GCJ02_TO_BD09LL);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setScanSpan(1000 * 10);


        mLocationClient.setLocOption(option);

        mLocalBDLocationListener = new LocalBDLocationListener();

        mLocationClient.registerLocationListener(mLocalBDLocationListener);
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


    private LocalBDLocationListener mLocalBDLocationListener = null;

    private class LocalBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            requestLocation(bdLocation);
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


    /**
     * 记录定位的记录数据
     *
     * @param bdLocation
     */
    private void requestLocation(BDLocation bdLocation) {
        double latitude = bdLocation.getLatitude();
        double longitude = bdLocation.getLongitude();
        double altitude = bdLocation.getAltitude();
        int locationType = bdLocation.getLocType();
        System.out.printf("latitude = %s,longtitude = %s,altitude  = %s", latitude, longitude, altitude);
        switch (locationType) {
            case 161:

                break;
            case 162:

                break;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

}
