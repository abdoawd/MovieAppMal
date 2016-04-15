
package com.example.abdo.mymovieapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
 * Created by abdo on 4/7/2016.
 */
public class MyFragmentDetails extends Fragment {
    ListView listView,listViewreview;
    ArrayList<String> key1=new ArrayList<String>();
    ArrayList<video> videos=new ArrayList<video>();
    MovieModel model;
    ImageView imageView;
    String yy="http://image.tmdb.org/t/p/w185/";
    String y,x;
     Button button;
    TextView original_title,overview,release ,vote;
    public  void get(MovieModel m){
        model=m;
        overview.setText(m.overview);
        original_title.setText(m.original_title);
        release.setText(m.release_date);
        vote.setText(m.vote_average);
        Picasso.with(getActivity()).load(yy+m.poster_path).resize(500,500).into(imageView);
        y="http://api.themoviedb.org/3/movie/"+m.id+"/videos?api_key="+MovieSchema.api;
        x="http://api.themoviedb.org/3/movie/"+m.id+"/reviews?api_key="+MovieSchema.api;
        final MovieDBHelper helper=new MovieDBHelper(getActivity());

        if (helper.Check(model.id).equals("not_check")) {
            button.setBackgroundResource(android.R.drawable.btn_star_big_off);

        }
        else if (helper.Check(model.id).equals("check")){
            button.setBackgroundResource(android.R.drawable.btn_star_big_on);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.Check(model.id).equals("not_check")) {
                    helper.addMovie(model.id,model.original_title, model.vote_average,model.overview,"check");
                    button.setBackgroundResource(android.R.drawable.btn_star_big_on);
                }
                else if (helper.Check(model.id).equals("check")){
                    button.setBackgroundResource(android.R.drawable.btn_star_big_on);
                    Toast.makeText(getActivity(),"this movie already add to favourite",Toast.LENGTH_LONG).show();
                }
            }
        });

        new Asyncc().execute();
        new Async().execute();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_detail_layout,container,false);



    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout layout= (RelativeLayout) view.findViewById(R.id.l);
        original_title= (TextView) layout.findViewById(R.id.te1);
        release= (TextView) layout.findViewById(R.id.te2);
        vote= (TextView) layout.findViewById(R.id.te3);
        overview= (TextView) layout.findViewById(R.id.te4);
        listView= (ListView) layout.findViewById(R.id.list2);
        imageView= (ImageView) layout.findViewById(R.id.image2);
        listViewreview= (ListView) layout.findViewById(R.id.review2);
         button= (Button) layout.findViewById(R.id.button3);
    }
    public  class Async extends AsyncTask<String,Void,ArrayList<video>> {
        @Override
        protected ArrayList<video> doInBackground(String... params) {
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
        protected void onPostExecute(ArrayList<video> strings) {

            Adapter adapter=new Adapter(getActivity(),strings);
            listView.setAdapter(adapter);
            super.onPostExecute(strings);
        }

        public ArrayList<video> parsing (String  json) throws JSONException {
               videos.clear();
            JSONObject all = new JSONObject(json);
            JSONArray myArray = all.getJSONArray("results");
            JSONObject item=null;
            for (int i = 0; i<myArray.length(); i++)
            {
                item = myArray.getJSONObject(i);

                String s="trailer";
                video video=new video(item.getString("key"),s+i);

                videos.add(video);
            }
            return  videos;
        }
    }
    public  class Asyncc extends AsyncTask<String,Void,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String tsonstring = null;

            try {

                URL url1 = new URL(x);

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
        protected void onPostExecute(ArrayList<String> string) {

            ArrayAdapter adapterr=new ArrayAdapter(getActivity()
                    ,R.layout.single_row_review,
                    R.id.review2,string);
            listViewreview.setAdapter(adapterr);
            super.onPostExecute(string);
        }

        public ArrayList<String> parsing (String  json) throws JSONException {
              key1.clear();
            JSONObject all = new JSONObject(json);
            JSONArray myArray = all.getJSONArray("results");

            String[] myMovies=null;
            for (int i = 0; i<myArray.length(); i++)
            {
                JSONObject item = myArray.getJSONObject(i);

                String s=item.getString("content");
                key1.add(s);
            }
            return  key1;
        }
    }
    public  class  video {

        String content ,trailer ;
        video( String content ,String trailer){
            this.content=content;
            this.trailer=trailer;
        }
    }
    public  class Adapter extends BaseAdapter {
        ArrayList<video> list;
        Context context;
        public Adapter(Context context, ArrayList<video> list1){
            this.context=context;
            this.list=list1;
            list=videos;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row=convertView;
            viewholder viewholder = null;
            if (row==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_row_trailer, parent, false);
                viewholder=new viewholder(row);
                row.setTag(viewholder);
            }
            else {
                viewholder= (viewholder) row.getTag();
            }
            video video=list.get(position);
            viewholder.TextView.setText(video.trailer);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s=list.get(position).content;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+s)));


                }
            });
            return row;
        }
    }
    public class viewholder {
        TextView TextView;
        Button button ;
        viewholder(View v) {
            TextView = (TextView) v.findViewById(R.id.te);
            button= (Button) v.findViewById(R.id.video_button);
        }


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.second,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.favourite){

        }
        else{
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"movie name "+model.original_title+"\n"+" rate "+model.vote_average);
            intent.setType("text/plain");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}