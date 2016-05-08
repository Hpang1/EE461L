package com.team8.utest;

import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jschoe on 4/28/16.
 */
public class DBPush extends AsyncTask<Quiz, Void, Void> {


    @Override
    protected Void doInBackground(Quiz... params) {
        String put_url = "http://utestapp.com/put.php";
        Quiz quiz = params[0];
        String quiz_name = quiz.name;
        String quiz_creator = quiz.creator;

        try{
            URL url = new URL(put_url);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream OS = con.getOutputStream();
            String serializedQuiz = Base64.encodeToString(quiz.serialize(), Base64.DEFAULT);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("quiz_name", "UTF-8") + "=" + URLEncoder.encode(quiz.name, "UTF-8") + "&" +
                    URLEncoder.encode("quiz_creator", "UTF-8") + "=" + URLEncoder.encode(quiz.creator, "UTF-8") + "&" +
                    URLEncoder.encode("quiz_object" , "UTF-8") + "=" + URLEncoder.encode(serializedQuiz, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream in = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "iso-8859-1"));
            String result = "";
            String line;
            while((line = bufferedReader.readLine())!= null){
                result += line;
            }
            bufferedReader.close();
            in.close();
            con.disconnect();

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


    // Serialize a single object.
    public static String serializeToJson(Quiz quiz) {
        Gson gson = new Gson();
        String j = gson.toJson(quiz);
        return j;
    }

}
