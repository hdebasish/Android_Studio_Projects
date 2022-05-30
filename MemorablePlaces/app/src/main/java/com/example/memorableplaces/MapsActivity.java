package com.example.memorableplaces;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.memorableplaces.MainActivity.address;
import static com.example.memorableplaces.MainActivity.locations;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLocation;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        if (getIntent().getIntExtra("position", 0) == 0) {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mMap.clear();
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Log.i("My Location", list.get(0).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    locationManager.removeUpdates(locationListener);

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
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mMap.clear();
                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
            }
        }else {
            mMap.addMarker(new MarkerOptions().position(locations.get(getIntent().getIntExtra("position", 0))).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations.get(getIntent().getIntExtra("position", 0)),15));
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                mMap.addMarker(new MarkerOptions().position(latLng).title("New Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> list = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    Log.i("My Location", list.get(0).getAddressLine(0));
                    Toast.makeText(getApplicationContext(),list.get(0).getAddressLine(0),Toast.LENGTH_LONG).show();
                    address.add(list.get(0).getAddressLine(0));
                    locations.add(latLng);
                    MainActivity.adapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getSharedPreferences("com.example.memorableplaces",Context.MODE_PRIVATE);
                    ArrayList<String> latitudes = new ArrayList<>();
                    ArrayList<String> longitudes = new ArrayList<>();
                    for(LatLng coordinates: locations)
                    {
                        latitudes.add(Double.toString(coordinates.latitude));
                        longitudes.add(Double.toString(coordinates.longitude));
                    }
                    sharedPreferences.edit().putString("address",ObjectSerializer.serialize(address)).apply();
                    sharedPreferences.edit().putString("latitude",ObjectSerializer.serialize(latitudes)).apply();
                    sharedPreferences.edit().putString("longitude",ObjectSerializer.serialize(longitudes)).apply();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

}
