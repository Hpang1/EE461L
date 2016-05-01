package com.team8.utest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class QuizPast extends AppCompatActivity {

    ArrayList<Quiz> allQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_past);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //grab all previously created quizzes
        //possibly grab from server all corresponding results (or maybe do that when a quiz is selected)
        allQuizzes = getQuizzes();
        if(allQuizzes.size() == 0){
            TextView title = (TextView) findViewById(R.id.pastQuizText);
            title.setText("No Quizzes");
        } else{
            populateScroll();
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
                allQuizzes = (ArrayList<Quiz>) in.readObject(); //probably change to results object
                in.close();
            } else{
                allQuizzes = new ArrayList<>();
                FileOutputStream outputStream = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(outputStream);
                out.writeObject(allQuizzes);
                out.close();
            }



            if(allQuizzes == null){
                allQuizzes = new ArrayList<>();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return allQuizzes;
    }

    public void populateScroll(){
        //scroll = new ArrayList<>(quizzes.size());
        //maybe sort results later based on author or name
        LinearLayout scroll = (LinearLayout) findViewById(R.id.pastScroll2);
        scroll.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int i = 0;
        for(Quiz quiz : allQuizzes){
            RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.searchresult, null);
            layout.setTag(i);
            TextView name = (TextView) layout.findViewById(R.id.quiztitle);
            TextView creator = (TextView) layout.findViewById(R.id.creator);
            name.setText(quiz.name);
            creator.setText(quiz.creator);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Quiz quiz = allQuizzes.get((int) v.getTag());
                    Intent intent = new Intent(QuizPast.this, QuizViewer.class);
                    intent.putExtra("quiz", quiz.serialize());
                    startActivity(intent);
                }
            });
            scroll.addView(layout);
        }
    }

}
