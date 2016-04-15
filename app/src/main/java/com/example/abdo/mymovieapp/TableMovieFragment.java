package com.example.abdo.mymovieapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by abdo on 4/11/2016.
 */
public class TableMovieFragment extends Fragment {
    GridView gridview ;
    comm  comm;
    String y="http://api.themoviedb.org/3/movie/top_rated?api_key=1b5062dd05eaace07a10010e59798def";

    ArrayList<MovieModel> myMovies = new ArrayList<>();
    MovieModel m=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.movie_layout,container,false);



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm= (comm) getActivity();
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        gridview= (GridView) view.findViewById(R.id.gridView2);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                m=myMovies.get(position);
//                Intent intent=new Intent(getActivity(),MovieDetails.class);
//                intent.putExtra("parcell",m);
//                startActivity(intent);
               comm.respond(m);

            }
        });
        Async async=new Async();
        async.execute();
        super.onViewCreated(view, savedInstanceState);
    }
    public  class Async extends AsyncTask<String,Void,ArrayList<MovieModel>> {
        @Override
        protected ArrayList<MovieModel> doInBackground(String... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String tsonstring = null;

            try {

                URL url1 = new URL(y);

                urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    tsonstring = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    tsonstring = null;
                }
                tsonstring = buffer.toString();
                return parsing(tsonstring);
            } catch (IOException e) {

                tsonstring = null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieModel> strings) {
            Adapter  adapter=new Adapter(getActivity(),strings);
            gridview.setAdapter(adapter);

            super.onPostExecute(strings);
        }

        public ArrayList<MovieModel> parsing (String  json) throws JSONException {

            myMovies.clear();
            JSONObject all = new JSONObject(json);
            JSONArray myArray = all.getJSONArray("results");

            for (int i=0;i<myArray.length();i++)
            {
                JSONObject item = myArray.getJSONObject(i);

                MovieModel temp = new MovieModel(item.getString("poster_path"),
                        item.getString("overview"),
                        item.getString("release_date"),item.getString("title")
                        ,item.getInt("id"),item.getString("vote_average"));
                myMovies.add(temp);
            }
            return  myMovies;
        }
    }
    public class Adapter extends BaseAdapter {
        ArrayList<MovieModel> list;
        Context context;
        Adapter(Context context,ArrayList <MovieModel> movies){
            this.list= movies;
            this.context=context;
            list=myMovies;
        }

        @Override
        public int getCount() {
            return myMovies.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=convertView;
            MovieHolder holder;
            if (row==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_row, parent, false);
                holder= new MovieHolder(row);
                row.setTag(holder);}
            else {
                holder= (MovieHolder) row.getTag();
            }
            MovieModel item=list.get(position);
            String yy="http://image.tmdb.org/t/p/w185/";
            Picasso.with(context).load(yy+item.poster_path).resize(500,500).into(holder.imageView);
            return row;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.setting,menu);
        super.onCreateOptionsMenu(menu, inflater);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.popular){
            Toast.makeText(getActivity(),"this is rated option item man",Toast.LENGTH_LONG).show();
            Async async=new Async();
            async.execute();
            y="http://api.themoviedb.org/3/movie/popular?api_key=1b5062dd05eaace07a10010e59798def";

        }
        else if (id==R.id.to_prated) {
            Toast.makeText(getActivity(),"this is most popular option item man",Toast.LENGTH_LONG).show();
            Async async=new Async();
            async.execute();
            y="http://api.themoviedb.org/3/movie/top_rated?api_key=1b5062dd05eaace07a10010e59798def";
        }
        else  if (id==R.id.favourite){
            startActivity(new Intent(getActivity(),MyFavourite.class));

        }
        return super.onOptionsItemSelected(item);
    }
}
