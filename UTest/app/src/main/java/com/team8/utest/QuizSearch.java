package com.team8.utest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                Question q1 = new Question("q1");
                Question q2 = new Question("q2");
                Choice c1 =  new Choice("c1");
                Choice c2 = new Choice("c2");
                Choice c3 = new Choice("c3");
                c2.setCorrect(true);
                q1.addChoice(c1);
                q1.addChoice(c2);
                q2.addChoice(c2);
                q2.addChoice(c3);
                for(int i = 0; i < 100; i++){
                    Quiz quiz = new Quiz(String.format("quiz%d", i), "creator1");
                    quiz.addQuestion(q1);
                    quiz.addQuestion(q2);
                    quizzes.add(quiz);
                }
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
                Question q1 = new Question("q1");
                Question q2 = new Question("q2");
                Choice c1 =  new Choice("c1");
                Choice c2 = new Choice("c2");
                Choice c3 = new Choice("c3");
                c2.setCorrect(true);
                q1.addChoice(c1);
                q1.addChoice(c2);
                q2.addChoice(c2);
                q2.addChoice(c3);
                for(int i = 0; i < 100; i++){
                    Quiz quiz = new Quiz(String.format("quiz%d", i), "creator2");
                    quiz.addQuestion(q1);
                    quiz.addQuestion(q2);
                    quizzes.add(quiz);
                }
                //end example code

                populateScroll();
            }
        });

        quizzes = InternalStorage.getQuizzes(this);
        populateScroll();


    }

    public void populateScroll(){
        //scroll = new ArrayList<>(quizzes.size());
        //maybe sort results later based on author or name
        LinearLayout scroll = (LinearLayout) findViewById(R.id.searchScroll2);
        scroll.removeAllViews();
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int i = 0;
        for(Quiz quiz : quizzes){
            RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.searchresult, null);
            layout.setTag(i);
            TextView name = (TextView) layout.findViewById(R.id.quiztitle);
            TextView creator = (TextView) layout.findViewById(R.id.creator);
            name.setText(quiz.name);
            creator.setText(quiz.creator);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Quiz quiz = quizzes.get((int) v.getTag());
                    Intent intent = new Intent(QuizSearch.this, QuizTaker.class);
                    intent.putExtra("quiz", quiz.serialize());
                    startActivity(intent);
                }
            });
            scroll.addView(layout);
            i++;
        }
    }



}
