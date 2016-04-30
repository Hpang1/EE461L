package com.team8.utest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class QuizResults extends AppCompatActivity {

    ArrayList<Results> allResults;

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
        allResults = null;
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

    public void populateScroll(){
        //scroll = new ArrayList<>(quizzes.size());
        //maybe sort results later based on author or name
        LinearLayout scroll = (LinearLayout) findViewById(R.id.searchScroll2);
        scroll.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int i = 0;
        for(Results result : allResults){
            Quiz quiz = result.quiz;
            RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.searchresult, null);
            layout.setTag(i);
            TextView name = (TextView) layout.findViewById(R.id.quiztitle);
            TextView creator = (TextView) layout.findViewById(R.id.creator);
            name.setText(quiz.name);
            creator.setText(quiz.creator);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Results result = allResults.get((int) v.getTag());
                    Intent intent = new Intent(QuizResults.this, QuizViewer.class);
                    intent.putExtra("result", result.serialize());
                    startActivity(intent);
                }
            });
            scroll.addView(layout);
        }
    }

}
