package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class SoccerMainActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressBar progressBar;

    private List<String> matchList=new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId== R.id.about) {
            View middle = getLayoutInflater().inflate(R.layout.activity_soccer_info, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.create().show();
            makeText(this, "App info", LENGTH_SHORT).show();
        }

        if (itemId== R.id.fav) {
                //addData(artistWord,titleWord, lyrics);//add Lyrics, Lyrics to databas
                makeText(this, "Match added in list", Toast.LENGTH_LONG).show();
        }

        if (itemId== R.id.exit) {
            final Snackbar sb = Snackbar.make(listView, "Do You Want To Exit?", Snackbar.LENGTH_LONG);
            sb.setAction("Exit", new View.OnClickListener() {
                @Override
                public void onClick(View e) {
                    SoccerMainActivity.this.finish();
                }
            });
            sb.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.matchList);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        arrayAdapter=new ArrayAdapter<>(thi)
    }

    private  class lyricsquery extends AsyncTask<String,Integer,String> {
        @Override
        protected void onPostExecute(String s) {

            listView.setAdapter(arrayAdapter);
            progressBar.setProgress(100);
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            String line=null;
            String urlString = strings[0];
            publishProgress(25);

            try {
                URL wordURL = new URL(urlString);
                urlConnection = (HttpURLConnection) wordURL.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();

                BufferedReader reader = null;
                publishProgress(25);
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"), 8);
                line=reader.readLine();
                publishProgress(50);
                JSONObject jObject = new JSONObject(line);
                lyrics=jObject.getString("lyrics");

                Log.d("size1",String.valueOf(lyricsList.size()));
                lyricsArray = lyrics.split("\r\n|\n\n\n");//add Lyrics to the list
                for(int i=0;i<lyricsArray.length;i++){
                    lyricsList.add(lyricsArray[i]);
                }
                publishProgress(75);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Finished";
        }


    }


}