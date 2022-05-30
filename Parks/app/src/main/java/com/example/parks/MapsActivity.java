package com.example.parks;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.parks.adapter.CustomInfoWindowAdapter;
import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;
import com.example.parks.model.ParkViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;
    private EditText searchEditText;
    private ImageButton searchButton;
    private CardView cardView;
    private String code="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        parkViewModel = new ViewModelProvider(this).get(ParkViewModel.class);
        searchEditText = findViewById(R.id.search_editText);
        searchButton = findViewById(R.id.search_button);
        cardView = findViewById(R.id.floating_search);
        BottomNavigationView bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment=null;
            int id=item.getItemId();
            if (id==R.id.bottom_nav_map){
                if (cardView.getVisibility()==View.GONE || cardView.getVisibility()==View.INVISIBLE){
                    cardView.setVisibility(View.VISIBLE);
                }
                parkList.clear();
                mMap.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.map,mapFragment).addToBackStack(null).commit();
                mapFragment.getMapAsync(this);

            }else if (id==R.id.bottom_nav_parks){
                cardView.setVisibility(View.INVISIBLE);
                selectedFragment = ParksFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.map,selectedFragment).commit();
            }


            return true;

        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                parkList.clear();
                String stateCode = searchEditText.getText().toString().trim();

                if (!TextUtils.isEmpty(stateCode)){
                    parkViewModel.selectCode(stateCode);
                    code=stateCode;
                    onMapReady(mMap);
                    Log.i("State code",code);
                    searchEditText.setText("");
                }else if (stateCode.equals("")){
                    code=stateCode;
                    onMapReady(mMap);
                }

            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera

        populateMap();
    }

    private void populateMap() {
        mMap.clear();
        Repository.getParks(new AsyncResponse() {
            @Override
            public void processPark(List<Park> parks) {
                parkList = new ArrayList<>();
                parkList.clear();
                for (Park park:parks) {
                    parkList=parks;
                    LatLng location = new LatLng(Double.parseDouble(park.getLatitude()),Double.parseDouble(park.getLongitude()));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(location)
                            .title(park.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_VIOLET
                            ))
                            .snippet(park.getStates());
                    Marker marker = mMap.addMarker(markerOptions);
                    marker.setTag(park);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,5));
                }
                parkViewModel.setSelectedParks(parks);
            }
        },code);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        cardView.setVisibility(View.INVISIBLE);
        goToDetails(marker);
    }

    private void goToDetails(Marker marker) {
        parkViewModel.selectPark((Park) marker.getTag());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map, DetailsFragment.newInstance())
                .commit();
    }
}