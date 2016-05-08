package com.team8.utest;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;



/**
 * Created by jschoe on 4/30/16.
 */
public class DBFetch extends AsyncTask<String, Void, ArrayList<Quiz>> {
    public ArrayList<Quiz> queryResult;
    @Override
    protected ArrayList<Quiz> doInBackground(String ... params) {
        String get_name_url = "http://utestapp.com/getName.php";
        String get_creator_url = "http://utestapp.com/getCreator.php";
        String key = params[0];
        String task = params[1];
        ServiceHandler jsonParser = new ServiceHandler();
        ArrayList<Quiz> quizList= new ArrayList<Quiz>();
        String json = "";
        List<NameValuePair> nvp = new ArrayList<NameValuePair>();
        if(task == "title"){
            nvp.add(new BasicNameValuePair("quiz_name", key));
            json = jsonParser.makeServiceCall(get_name_url, ServiceHandler.GET, nvp);

        } else if (task == "creator"){
            nvp.add(new BasicNameValuePair("quiz_creator", key));
            json = jsonParser.makeServiceCall(get_creator_url, ServiceHandler.GET, nvp);
        }
        Log.e("Response: ", "> " + json);

        if(json != null){
        try{
            //JSONObject jsonObj = new JSONObject(json);
            //if (jsonObj != null) {
            JSONArray quizzes = new JSONArray(json);
                for (int i = 0; i < quizzes.length(); i++) {
                    byte[] quizObj = Base64.decode(quizzes.getString(i), Base64.DEFAULT);
                    Quiz quiz = Quiz.deserialize(quizObj);
                    quizList.add(quiz);
                }
            //}
            queryResult = quizList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        } else {
            Log.e("JSON Data", "Didn't receive any data from server!");
        }
        return queryResult;
    }


}
