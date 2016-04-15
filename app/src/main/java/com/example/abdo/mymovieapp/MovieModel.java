package com.example.abdo.mymovieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdo on 3/25/2016.
 */
public class MovieModel implements Parcelable
{
    String poster_path;
    String overview;
    String release_date;
    String original_title;
    int id;
    String vote_average;

    public MovieModel(String poster_path, String overview, String release_date, String original_title, int id, String vote_average)
    {
        this.id=id;
        this.original_title=original_title;
        this.overview=overview;
        this.release_date=release_date;
        this.vote_average=vote_average;
        this.poster_path=poster_path;
    }

    protected MovieModel(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        id = in.readInt();
        vote_average = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeInt(id);
        dest.writeString(vote_average);

    }
}
