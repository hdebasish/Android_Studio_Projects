package com.example.uber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Request extends AppCompatActivity {


    LocationManager locationManager;
    LocationListener locationListener;
    ListView listView;
    ArrayList<String> nearByUsers;
    ArrayList<LatLng> riderDirections;
    ArrayList<String> username;
    ArrayAdapter adapter;
    ParseGeoPoint driverLocation;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1 && grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        listView=findViewById(R.id.listView);
        nearByUsers= new ArrayList<>();
        riderDirections = new ArrayList<>();
        username = new ArrayList<>();
        //nearByUsers.add("Test");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nearByUsers);
        //listView.setAdapter(adapter);
        listView.setAdapter(adapter);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                driverLocationUpdate(location);
                ParseUser.getCurrentUser().put("location", new ParseGeoPoint(location.getLatitude(),location.getLongitude()));
                ParseUser.getCurrentUser().saveInBackground();

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
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
            Location lastKnownLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("My location",lastKnownLocation.toString());
            driverLocation = new ParseGeoPoint(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());

            ParseQuery<ParseObject> query= new ParseQuery<ParseObject>("Request");
            query.whereNear("location",driverLocation);
            query.whereDoesNotExist("driverUsername");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null){
                        for(ParseObject object:objects){
                            ParseGeoPoint direction=object.getParseGeoPoint("location");
                            LatLng latLng = new LatLng(direction.getLatitude(),direction.getLongitude());
                            riderDirections.add(latLng);
                            Double distance=driverLocation.distanceInKilometersTo(object.getParseGeoPoint("location"));
                            String userDistance = String.format("%.5f", distance);
                            String pickUpUser= "Rider is "+userDistance+" km away.";
                            nearByUsers.add(pickUpUser);
                            username.add(object.getString("username"));
                        }
                        adapter.notifyDataSetChanged();

                    }
                }
            });
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DriverActivity.class);
                intent.putExtra("riderDirection",riderDirections.get(position));
                LatLng driverDirection = new LatLng(driverLocation.getLatitude(),driverLocation.getLongitude());
                intent.putExtra("driverDirection",driverDirection);
                intent.putExtra("username",username.get(position));
                startActivity(intent);
            }
        });

    }

    public void driverLocationUpdate(Location location){

        nearByUsers.clear();
        riderDirections.clear();
        username.clear();
        driverLocation = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
        ParseQuery<ParseObject> query= new ParseQuery<ParseObject>("Request");
        query.whereNear("location",driverLocation);
        query.whereDoesNotExist("driverUsername");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    for(ParseObject object:objects){
                        ParseGeoPoint direction=object.getParseGeoPoint("location");
                        LatLng latLng = new LatLng(direction.getLatitude(),direction.getLongitude());
                        riderDirections.add(latLng);
                        Double distance=driverLocation.distanceInKilometersTo(object.getParseGeoPoint("location"));
                        String userDistance = String.format("%.5f", distance);
                        String pickUpUser= "Rider is "+userDistance+" km away.";
                        nearByUsers.add(pickUpUser);
                        username.add(object.getString("username"));
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });

    }
}
