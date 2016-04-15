package com.example.abdo.mymovieapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;
public class Main extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
            if (savedInstanceState==null){

                if (diagonalInches>=6){
                    startActivity(new Intent(this,Tablet.class));
                    Toast.makeText(this,"tablet  = " ,Toast.LENGTH_LONG).show();
                }
                else if (diagonalInches<6){
                getFragmentManager().beginTransaction().add(R.id.container, new MoviesFragment()).commit();

            }
        }
    }

    }