package com.example.locationinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && PackageManager.PERMISSION_GRANTED==grantResults[0]){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.info);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    String info="";
                    String lat=String.valueOf(location.getLatitude());
                    String lon=String.valueOf(location.getLongitude());
                    String accuracy=String.valueOf(location.getAccuracy());
                    String altitude=String.valueOf(location.getAltitude());
                    String address=list.get(0).getAddressLine(0);
                    if(list.get(0)!=null)
                    {
                        if (lat.length()>0)
                        {
                            info+="Latitude:"+"\n"+lat+"\n\n";
                        }
                        if (lon.length()>0)
                        {
                            info+="Longitude:"+"\n"+lon+"\n\n";
                        }
                        if (accuracy.length()>0)
                        {
                            info+="Accuracy:"+"\n"+accuracy+"\n\n";
                        }
                        if(altitude.length()>0)
                        {
                            info+="Altitude:"+"\n"+altitude+"\n\n";
                        }
                        if (address.length()>0)
                        {
                            info+="Address:"+"\n"+address;
                        }
                        textView.setText(info);
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }

    }
}
