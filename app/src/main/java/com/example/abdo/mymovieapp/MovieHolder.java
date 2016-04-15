package com.example.abdo.mymovieapp;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by abdo on 3/24/2016.
 */
public class MovieHolder {
    ImageView imageView;
    MovieHolder(View v) {
        imageView = (ImageView) v.findViewById(R.id.imageView);
    }
    }
