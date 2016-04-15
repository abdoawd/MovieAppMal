package com.example.abdo.mymovieapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by abdo on 4/6/2016.
 */
public class CurserAdapter extends CursorAdapter {
    public CurserAdapter(Context context, Cursor c)
    {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
         return ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.favourit_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView view1,view2,view3;
        LinearLayout layout= (LinearLayout) view.findViewById(R.id.favourite);
        view1= (TextView) layout.findViewById(R.id.title);
        view2= (TextView) layout.findViewById(R.id.title1);
        view3= (TextView) layout.findViewById(R.id.title2);
        view1.setText("movie name : "+cursor.getString(cursor.getColumnIndex(MovieSchema.COLUMN_ORIGINAL_TITL)));
        view2.setText("vote average : "+cursor.getString(cursor.getColumnIndex(MovieSchema.COLUMN_VOTE_AVETAGE))+" /10");
        view3.setText("over view  : "+cursor.getString(cursor.getColumnIndex(MovieSchema.COLUMN_OVERVIEW)));
    }
}
