package com.example.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import okhttp3.internal.Util;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parks.adapter.ViewPagerAdapter;
import com.example.parks.model.Activities;
import com.example.parks.model.Park;
import com.example.parks.model.ParkViewModel;

import java.util.List;


public class DetailsFragment extends Fragment {

    private TextView parkName;
    private TextView parkDesg;
    private TextView parkDescription;
    private TextView parkActivities;
    private TextView parkTopics;
    private TextView parkEntrance;
    private TextView parkOperatingHours;
    private TextView parkDirections;
    private ParkViewModel parkViewModel;
    private Park park;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    public DetailsFragment() {
        // Required empty public constructor
    }

public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        park = new Park();

        parkViewModel = new ViewModelProvider(requireActivity()).get(ParkViewModel.class);
        if (parkViewModel.getSelectedPark().getValue()!=null){
            park = parkViewModel.getSelectedPark().getValue();
            parkName.setText(park.getName());
            parkDesg.setText(park.getDesignation());
            viewPagerAdapter = new ViewPagerAdapter(park.getImages());
            viewPager.setAdapter(viewPagerAdapter);
            parkDescription.setText(park.getDescription());
            List<Activities> activities = park.getActivities();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <activities.size() ; i++) {

                sb.append(activities.get(i).getName()).append(" | ");
            }
            parkActivities.setText(sb);
            if (park.getEntranceFees().size()>0){
                parkEntrance.setText(String.format("Cost: $%s", park.getEntranceFees().get(0).getCost()));
            }else {
                parkEntrance.setText(getString(R.string.infonot));
            }
            StringBuilder op = new StringBuilder();
            op.append("Sunday: ").append(park.getOperatingHours().get(0).getStandardHours().getSunday()).append("\n")
                    .append("Monday: ").append(park.getOperatingHours().get(0).getStandardHours().getMonday()).append("\n")
                    .append("Tuesday: ").append(park.getOperatingHours().get(0).getStandardHours().getTuesday()).append("\n")
                    .append("Wednesday: ").append(park.getOperatingHours().get(0).getStandardHours().getWednesday()).append("\n")
                    .append("Thursday: ").append(park.getOperatingHours().get(0).getStandardHours().getThursday()).append("\n")
                    .append("Friday: ").append(park.getOperatingHours().get(0).getStandardHours().getFriday()).append("\n")
                    .append("Saturday: ").append(park.getOperatingHours().get(0).getStandardHours().getSaturday()).append("\n");
            parkOperatingHours.setText(op);

            StringBuilder topic = new StringBuilder();
            for (int i = 0; i <park.getTopics().size() ; i++) {
                topic.append(park.getTopics().get(i).getName()).append(" | ");
            }
            parkTopics.setText(topic);

            if (!TextUtils.isEmpty(park.getDirectionsInfo())){
                parkDirections.setText(park.getDirectionsInfo());
            }else{
                parkDirections.setText(R.string.nodirection);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        parkDesg = view.findViewById(R.id.details_park_designation);
        parkName = view.findViewById(R.id.details_park_name);
        viewPager = view.findViewById(R.id.details_viewPager);
        parkDescription = view.findViewById(R.id.details_desc_textView);
        parkActivities = view.findViewById(R.id.details_active_textView);
        parkTopics = view.findViewById(R.id.details_topics_textView);
        parkEntrance = view.findViewById(R.id.details_fees_textView);
        parkOperatingHours = view.findViewById(R.id.details_hours_textView);
        parkDirections = view.findViewById(R.id.details_direction_textView);
        return view;
    }
}