package com.team8.utest;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class QuizPast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_past);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //grab all previously created quizzes
        //possibly grab from server all corresponding results (or maybe do that when a quiz is selected)
        ArrayList<Quiz> quizzes = getQuizzes();
        if(quizzes.size() == 0){

        } else{

        }
    }



    public ArrayList<Quiz> getQuizzes(){
        String filename = "quizzes.txt";
        ArrayList<Quiz> allQuizzes = null;
        try {
            File file = new File(this.getFilesDir(), filename);
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                allQuizzes = (ArrayList) in.readObject(); //probably change to results object
                in.close();
            }if(allQuizzes == null){
                allQuizzes = new ArrayList<>();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return allQuizzes;
    }

}
