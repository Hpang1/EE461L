package com.team8.utest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class QuizSearch extends AppCompatActivity {

    DBFetch db;
    //ArrayList<RelativeLayout> scroll;
    ArrayList<Quiz> quizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DBFetch();

        final SearchView searchBar = (SearchView) findViewById(R.id.searchView2);
        Button searchTitle = (Button) findViewById(R.id.searchAuthor);
        Button searchCreator = (Button) findViewById(R.id.searchCreator);


        //this functionality can be replaced with the strategy design later
        searchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBar.getQuery().toString();
                //get results
                quizzes = new ArrayList<>();    //replace with response from db


                //example code
                Quiz quiz1 = new Quiz("quiz1", "creator1");
                Quiz quiz2 = new Quiz("quiz2", "creator1");
                quizzes.add(quiz1);
                quizzes.add(quiz2);
                //end example code


                populateScroll();
                //search with this text
                //populate scroll view with results
                //set each element's onclick method to start the quiz taker with that quiz
                //can now serialize and deserialize quizzes into byte arrays to send in intents to other activities
                //also I don't know if all of the search results should be in xml, but rather inserted dynamically (or at least have fewer of them maybe)

            }
        });
        searchCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBar.getQuery().toString();
                //search with this text
                quizzes = new ArrayList<>();    //replace with response from db

                //example code
                Quiz quiz1 = new Quiz("quiz1", "creator2");
                Quiz quiz2 = new Quiz("quiz2", "creator2");
                quizzes.add(quiz1);
                quizzes.add(quiz2);
                //end example code

                populateScroll();
            }
        });


    }

    public void populateScroll(){
        //scroll = new ArrayList<>(quizzes.size());
        LinearLayout scroll = (LinearLayout) findViewById(R.id.searchScroll2);
        Inflater inflater = 
        int i = 0;
        for(Quiz quiz : quizzes){
            RelativeLayout layout = (RelativeLayout)findViewById(R.id.mysearchresult);
            layout.setTag(i);
            TextView name = (TextView) layout.findViewById(R.id.quiztitle);
            TextView creator = (TextView) layout.findViewById(R.id.creator);
            name.setText(quiz.name);
            creator.setText(quiz.creator);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Quiz quiz = quizzes.get((int) v.getTag());
                    Intent intent = new Intent(QuizSearch.this, QuizCreator.class);
                    intent.putExtra("quiz", quiz.serialize());
                    startActivity(intent);
                }
            });
            scroll.addView(layout);
        }
    }

}
