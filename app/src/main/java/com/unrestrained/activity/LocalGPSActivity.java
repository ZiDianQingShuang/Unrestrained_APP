package com.unrestrained.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.unrestrained.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocalGPSActivity extends AppCompatActivity {


    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_gps);
        ButterKnife.bind(this);
    }


    private LocationManager mLocationManager;

    public void localGPS(View view) {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location gpsProvider = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location passiveProvider = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double altitude = location.getAltitude();
                double longitude = location.getLongitude();

                String stringFormat = String.format("latitude = %s,longitude = %s,altitude = %s", latitude, longitude, altitude);

                textView.setText(stringFormat);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println(provider);
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

}
