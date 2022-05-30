package com.example.parks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.parks.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private View view;

    public CustomInfoWindowAdapter(Context context) {
        this.context=context;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_info_window,null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView title = view.findViewById(R.id.custom_title);
        TextView state = view.findViewById(R.id.custom_state);

        title.setText(marker.getTitle());
        state.setText(marker.getSnippet());

        return view;

    }
}
