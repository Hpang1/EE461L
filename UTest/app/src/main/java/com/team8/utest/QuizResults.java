package com.team8.utest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class QuizResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Results> results = getResults();
        if(results.size() == 0){

        } else{

        }
        //grab all previous quizzes
        //grab results for each corresponding quiz
    }

    public ArrayList<Results> getResults(){
        String filename = "results.txt";
        ArrayList<Results> allResults = null;
        try {
            File file = new File(this.getFilesDir(), filename);
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                allResults = (ArrayList) in.readObject(); //probably change to results object
                in.close();
            }if(allResults == null){
                allResults = new ArrayList<>();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return allResults;
    }

}
