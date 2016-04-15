package com.example.abdo.mymovieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE =
            "create table " +
                    MovieSchema.TABLE_NAME
                    + " ( " + "_id INTEGER PRIMARY KEY, "
                    + MovieSchema.COLUMN_ID+ " INTEGER , "
                    +MovieSchema.COLUMN_ORIGINAL_TITL + " TEXT,"
                    + MovieSchema.COLUMN_VOTE_AVETAGE + " TEXT, "
                    + MovieSchema.COLUMN_OVERVIEW + " TEXT, "
                    + MovieSchema.BOOL + " TEXT ) ";

    private static final String DB_NAME1 = "Movies.db";
    private static final int DB_VERSION = 1;


    public MovieDBHelper(Context context) {

        super(context, DB_NAME1, null, DB_VERSION);
    }

    public void addMovie(int id,String title,
                         String average,String view ,String bool) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieSchema.COLUMN_ID, id);
        values.put(MovieSchema.COLUMN_ORIGINAL_TITL, title);
        values.put(MovieSchema.COLUMN_VOTE_AVETAGE, average);
        values.put(MovieSchema.COLUMN_OVERVIEW, view);
        values.put(MovieSchema.BOOL, bool);
        long x=db.insert(MovieSchema.TABLE_NAME, null, values);
        }
        public  String Check(int c){
        SQLiteDatabase db = this.getWritableDatabase();
        String b=null;
        String get=" select * from "+
                MovieSchema.TABLE_NAME + " where " + MovieSchema.COLUMN_ID + "=" +c;
        Cursor cursor=db.rawQuery(get,null);
        if(cursor.getCount() <= 0){
            b="not_check";
        }
       else {
            b="check";
        }


        return b ;
    }
//    public  String Check(String c){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String get=" select "+MovieSchema.BOOL+" from "+
//                MovieSchema.TABLE_NAME+" where "+MovieSchema.COLUMN_ORIGINAL_TITL+" = ' "+c+ " ' ;";
//        Cursor cursor=db.rawQuery(get,null);
//        String s=null;
//        while (cursor.moveToNext()) {
//            s    = cursor.getString(cursor.getColumnIndex(MovieSchema.BOOL));
//
//        }
//        return s;
//    }

    public Cursor getAllMoviessCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String Select_Query = "select * from " + MovieSchema.TABLE_NAME;
        Cursor c = db.rawQuery(Select_Query, null);


        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
        Log.d("on create ","on create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieSchema.TABLE_NAME);

        // Create tables again
        onCreate(db);

        Log.d("on upgrade ","on upgrade");

    }
}
