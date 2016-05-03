package com.team8.utest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jschoe on 4/30/16.
 */
public class DBFetch extends AsyncTask<String, Void, Quiz> {
    Context ctx;

    DBFetch() {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Quiz doInBackground(String... params) {
        String get_url = "http://10.0.2.2:8080/UTest/get.php";
        String query_term = params[0];
        Quiz result = null;

        try{
            URL url = new URL(get_url);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);

            return result;


            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Quiz result) {

    }
}
