package com.example.abdo.mymovieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyFavourite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);
        ListView listView= (ListView) findViewById(R.id.list2);
         MovieDBHelper db;
          try {
              db=new MovieDBHelper(this);
              CursorAdapter adapter=new CurserAdapter(this,db.getAllMoviessCursor());
              listView.setAdapter(adapter);
          }
          catch (Exception e){
              e.printStackTrace();
              Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
          }

    }
}
