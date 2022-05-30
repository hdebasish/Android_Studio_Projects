package com.example.testlocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testlocation.model.SharedViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    TextView locationTextView;
    SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        locationTextView=view.findViewById(R.id.textview_first);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getLocation()!=null){
            LiveData<String> location = sharedViewModel.getLocation();

            location.observe(FirstFragment.this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    locationTextView.setText(s);
                }
            });


        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}