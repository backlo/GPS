package com.example.lik.gps;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class getGPS extends AppCompatActivity {

    static String Area;
    Geocoder geocoder;
    double latitude;
    double longitude;
    static String TAG="hoit";

    CountDownTimer timer = new CountDownTimer(10 * 1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            Log.d(TAG,"countdown finish실행");
            if (getGPS.Area == null) {
                TextView tv = (TextView) findViewById(R.id.tv);
                tv.setText("위치를 찾을수 없습니다. 다시시도해주시거나 GPS를 켜주시기 바랍니다.");
                return;
            } else {
                TextView tv = (TextView) findViewById(R.id.tv);
                tv.setText(Area);
                Area = null;
                return;
            }
        }
    };

    public void onCreate(Bundle savedInatacneState) {
        super.onCreate(savedInatacneState);
        setContentView(R.layout.activity_get_gps);
        Log.d(TAG,"getgps액티비티 실행");

        SharedPreferences localSharedPreferences = getSharedPreferences("save_data", 0);
        localSharedPreferences.edit();
        geocoder = new Geocoder(this, Locale.KOREAN);


        if (ActivityCompat.checkSelfPermission(getGPS.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getGPS.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        ((LocationManager) getSystemService(LOCATION_SERVICE)).requestLocationUpdates("network", 0L, 0.0F, new LocationListener() {
            public void onLocationChanged(Location paramAnonymousLocation) {
                Log.d(TAG,"locationManager시작");
                latitude = paramAnonymousLocation.getLatitude();
                longitude = paramAnonymousLocation.getLongitude();

                try {
                    List localList = geocoder.getFromLocation(latitude, longitude, 5);
                    Area = ((Address) localList.get(0)).getCountryName();
                    if (localList.size() > 0) {
                        Address localAddress = (Address) localList.get(0);
                        StringBuffer localStringBuffer = new StringBuffer();
                        for (int i = 0; ; i++) {
                            String str = localAddress.getAddressLine(i);
                            if (str == null) {
                                Area = localStringBuffer.toString();
                                break;
                            }
                            localStringBuffer.append(str + "\n");
                        }
                    } else {
                        Area = "위치를 찾을수 없습니다.";
                    }

                } catch (Exception localException) {
                    localException.printStackTrace();
                }
                Log.d(TAG,"locationmanager 끝");
            }

            public void onProviderDisabled(String paramAnonymousString) {
            }

            public void onProviderEnabled(String paramAnonymousString) {
            }

            public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle) {
            }
        });
        this.timer.start();
        Log.d(TAG,"countdown시작");
    }
}
