package com.example.imageslideshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SlideShowAdapter extends PagerAdapter {

    private Context context;
    LayoutInflater inflater;

    public SlideShowAdapter(Context context) {
        this.context = context;
    }

    public int[] images = {
            R.drawable.angrybirds,
            R.drawable.asphalt8,
            R.drawable.clashofclans,
            R.drawable.cuttherope,
            R.drawable.fruitninja,
            R.drawable.talkingtom,
            R.drawable.worms3,
            R.drawable.wheresmywhater,
            R.drawable.pou,
    };

    @Override
    public int getCount() {
        return images.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slidshow_layout,container,false);
        ImageView img = view.findViewById(R.id.slideShowImageView);
        //img.setImageResource(images[position]);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Image "+(position+1),Snackbar.LENGTH_SHORT).show();
            }
        });
        Glide.with(context).load(images[position]).into(img);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((LinearLayout)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }
}
