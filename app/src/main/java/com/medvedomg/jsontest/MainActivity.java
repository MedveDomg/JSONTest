package com.medvedomg.jsontest;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static MainActivity parent;
    private URL url;
    final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = new MainActivity();
        String baseUrl = "https://api.nasa.gov/planetary/apod?api_key=NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo";
        try {
            url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MyAsynTask myAsynTask = new MyAsynTask();
        myAsynTask.execute(url);
    }

    private void connectionFromURL(String baseUrl) {

    }

    public class MyAsynTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;
            String ss = "";
            try {
                connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "connection is ok");
                    StringBuilder builder = new StringBuilder();
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }
                    ss = builder.toString();
                    Log.d(TAG, "builder.toString" + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                return new JSONObject(ss);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG,  "не дошло");
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);
            try {
                String soka = s.getString("date");
                Log.d(TAG, "onPostExecute" + soka);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            JSONObject date = s.optJSONObject("date");
//            Log.d(TAG, "onPostExecute" + date);
        }
    }

}
