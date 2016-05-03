package com.team8.utest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.DriverManager;

/**
 * Created by jschoe on 4/28/16.
 */
public class DBPush extends AsyncTask<Quiz, Void, String> {

    Context context;

    public DBPush(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Quiz... params) {
        String put_url = "http://10.0.2.2:8888/UTest/put.php";
        Quiz quiz = params[0];
        try{
            URL url = new URL(put_url);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream OS = con.getOutputStream();
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("quiz_name", "UTF-8") + "=" + URLEncoder.encode(quiz.name, "UTF-8") + "&" +
                    URLEncoder.encode("quiz_creator", "UTF-8") + "=" + URLEncoder.encode(quiz.creator, "UTF-8");
            buffer.write(data);
            buffer.flush();
            buffer.close();
            OS.close();
            con.disconnect();
            quiz.serialize();


            return "Quiz Successfully Created!";

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
    protected void onPostExecute(String result) {
    }

}
