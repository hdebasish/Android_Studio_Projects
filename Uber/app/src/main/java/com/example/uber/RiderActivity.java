package com.example.uber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RiderActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Button button,mark;
    boolean riderActive=false, customlocation=false, markedlocation=false, driveArriving=false, driverActive=false;
    LatLng customLatLng;
    SharedPreferences sharedPreferences;
    Handler handler = new Handler();
    TextView textView;

    public void checkForUpdates(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.whereExists("driverUsername");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    button.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    if (!driveArriving){
                        textView.setText("Driver is on the way!");
                    }

                    String driverUsename = "";
                    for (ParseObject object: objects){
                        driverUsename=object.get("driverUsername").toString();
                    }
                    ParseQuery<ParseUser> query1 = ParseUser.getQuery();
                    query1.whereEqualTo("username",driverUsename);
                    query1.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if (e==null && objects.size()>0){
                                ParseGeoPoint driverDirection = new ParseGeoPoint();
                                for(ParseUser user: objects){
                                    driverDirection= user.getParseGeoPoint("location");
                                }
                               Log.i("Driver Direction",driverDirection.toString());
                                mMap.clear();
                                LatLng driverDirectionlatlng = new LatLng(driverDirection.getLatitude(),driverDirection.getLongitude());
                                if (customlocation){

                                    ParseGeoPoint riderDirection = new ParseGeoPoint(customLatLng.latitude,customLatLng.longitude);
                                    Double distance=riderDirection.distanceInKilometersTo(driverDirection);
                                    String userDistance = String.format("%.1f", distance);
                                    Double userDistanceDouble = Double.valueOf(userDistance);
                                    String pickUpUser= "Driver is "+userDistance+" km away.";

                                    if (userDistanceDouble<0.1)
                                    {
                                        textView.setText("Your Driver is Here!");

                                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
                                        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
                                        query.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if(e==null && objects.size()>0){
                                                    for (ParseObject object:objects){
                                                        object.deleteInBackground();
                                                    }
                                                }
                                            }
                                        });

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                new AlertDialog.Builder(RiderActivity.this)
                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                        .setTitle("Your uber has arrived!")
                                                        .setMessage("Happy Journey!")
                                                        .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(getApplicationContext(),RiderActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .show();



                                            }
                                        },5000);
                                    }else {
                                        final ArrayList<Marker> markers=new ArrayList<>();
                                        markers.add( mMap.addMarker(new MarkerOptions().position(customLatLng).title("Rider Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
                                        markers.add(mMap.addMarker(new MarkerOptions().position(driverDirectionlatlng).title("Driver Location")));
                                        //locationManager.removeUpdates(locationListener);
                                        textView.setText(pickUpUser);
                                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                        for (Marker marker : markers) {
                                            builder.include(marker.getPosition());
                                        }
                                        LatLngBounds bounds = builder.build();
                                        int padding = 100;
                                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                        mMap.moveCamera(cu);
                                        mMap.animateCamera(cu);
                                        driveArriving=true;

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                checkForUpdates();

                                            }
                                        },5000);
                                    }

                                }
                                else {
                                    if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED))
                                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
                                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if (lastKnownLocation != null) {
                                        LatLng riderDirectionlatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                                        ParseGeoPoint riderDirection = new ParseGeoPoint(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                                        Double distance=riderDirection.distanceInKilometersTo(driverDirection);
                                        String userDistance = String.format("%.1f", distance);
                                        Double userDistanceDouble = Double.valueOf(userDistance);
                                        String pickUpUser= "Driver is "+userDistance+" km away.";
                                        if (userDistanceDouble<0.1)
                                        {
                                            textView.setText("Your Driver is Here!");

                                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
                                            query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
                                            query.findInBackground(new FindCallback<ParseObject>() {
                                                @Override
                                                public void done(List<ParseObject> objects, ParseException e) {
                                                    if(e==null && objects.size()>0){
                                                        for (ParseObject object:objects){
                                                            object.deleteInBackground();
                                                        }
                                                    }
                                                }
                                            });

                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new AlertDialog.Builder(RiderActivity.this)
                                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                                            .setTitle("Your uber has arrived!")
                                                            .setMessage("Happy Journey!")
                                                            .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent(getApplicationContext(),RiderActivity.class);
                                                                    startActivity(intent);
                                                                }
                                                            })
                                                            .show();

                                                }
                                            },5000);
                                        }else {
                                            final ArrayList<Marker> markers=new ArrayList<>();
                                            markers.add( mMap.addMarker(new MarkerOptions().position(riderDirectionlatLng).title("Rider Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
                                            markers.add(mMap.addMarker(new MarkerOptions().position(driverDirectionlatlng).title("Driver Location")));
                                            //locationManager.removeUpdates(locationListener);
                                            textView.setText(pickUpUser);
                                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                            for (Marker marker : markers) {
                                                builder.include(marker.getPosition());
                                            }
                                            LatLngBounds bounds = builder.build();
                                            int padding = 100;
                                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                                            mMap.moveCamera(cu);
                                            mMap.animateCamera(cu);
                                            driveArriving=true;

                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        checkForUpdates();

                                                    }
                                                },5000);
                                        }

                                    }
                                }

                            }
                        }
                    });
                }else {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkForUpdates();
                        }
                    },2000);
                }

            }
        });


    }

    public void location(View view){
        if(customlocation && riderActive)
        {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
            query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null && objects.size()>0){
                        for (ParseObject object: objects){
                            ParseGeoPoint geoPoint = object.getParseGeoPoint("location");
                            customLatLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                        }
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(customLatLng).title("Your Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(customLatLng,15));
                        mark.setVisibility(View.INVISIBLE);

                    }
                }
            });
        }else {
            customlocation=false;
            markedlocation=false;
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                mMap.clear();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }

            mark.setVisibility(View.INVISIBLE);
        }


    }

    public void calluber(View view){
        if(riderActive){
            mark.setVisibility(View.VISIBLE);
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
            query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e==null && objects.size()>0){
                        for (ParseObject object:objects){
                            object.deleteInBackground();
                        }
                        button.setText(R.string.call_uber);
                        mark.setText(R.string.my_location);
                        riderActive=false;
                        customlocation=false;
                    }
                }
            });


        }else {
            mark.setVisibility(View.INVISIBLE);
            if(customlocation || markedlocation){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                    ParseObject request= new ParseObject("Request");
                    request.put("username", ParseUser.getCurrentUser().getUsername());
                    ParseGeoPoint geoPoint = new ParseGeoPoint(customLatLng.latitude,customLatLng.longitude);
                    request.put("location",geoPoint);
                    request.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                button.setText(R.string.cancel_uber);
                                mark.setText("Pick up Location");
                                riderActive=true;
                                checkForUpdates();

                            }else {
                                Toast.makeText(getApplicationContext(),"Uber Request was unsuccessful.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }else {
                Log.i("Inside","My location call uber");
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    ParseObject request= new ParseObject("Request");
                    request.put("username", ParseUser.getCurrentUser().getUsername());
                    ParseGeoPoint geoPoint = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
                    request.put("location",geoPoint);
                    request.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                button.setText(R.string.cancel_uber);
                                riderActive=true;
                                locationManager.removeUpdates(locationListener);
                                checkForUpdates();
                            }else {
                                Toast.makeText(getApplicationContext(),"Uber Request was unsuccessful.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

        }
    }

    public void logout(View view){

        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,locationListener);
            }

        }
    }

    public void updateLocation(Location location){
        if (!driveArriving){
            mMap.clear();
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        button=findViewById(R.id.callUber);
        textView=findViewById(R.id.textView);
        mark=findViewById(R.id.mark);
        textView.setVisibility(View.INVISIBLE);
        mark.setVisibility(View.INVISIBLE);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null && objects.size()>0){
                        button.setText(R.string.cancel_uber);
                        riderActive=true;
                        customlocation=true;
                    locationManager.removeUpdates(locationListener);
                    checkForUpdates();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
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
            ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
                query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && objects.size() > 0) {
                            for (ParseObject object : objects) {
                                ParseGeoPoint geoPoint = object.getParseGeoPoint("location");
                                customLatLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                            }
                            mMap.addMarker(new MarkerOptions().position(customLatLng).title("Your Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(customLatLng, 15));
                        } else {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
                                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (lastKnownLocation != null) {
                                    LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                                    mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                }
                            }

                        }

                    }
                });


        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                customlocation =true;
                customLatLng = latLng;
                markedlocation = true;
                mMap.clear();
                mark.setVisibility(View.VISIBLE);
                locationManager.removeUpdates(locationListener);
                mMap.addMarker(new MarkerOptions().position(latLng).title("New Location"));
            }
        });



    }


}
