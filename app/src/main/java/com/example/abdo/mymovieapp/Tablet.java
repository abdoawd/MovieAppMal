package com.example.abdo.mymovieapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Tablet extends AppCompatActivity implements comm {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);



    }

    @Override
    public void respond(MovieModel s) {
        FragmentManager manager=getFragmentManager();
        MyFragmentDetails details= (MyFragmentDetails) manager.findFragmentById(R.id.fragment4);
        details.get(s);
    }
}
