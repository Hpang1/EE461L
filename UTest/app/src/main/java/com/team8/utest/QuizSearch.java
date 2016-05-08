package com.team8.utest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizSearch extends AppCompatActivity {

    DBFetch db;
    //ArrayList<RelativeLayout> scroll;
    static ArrayList<Quiz> quizzes;

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


        searchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBar.getQuery().toString();
                DBFetch searchTitle = new DBFetch();
                try {
                    quizzes = searchTitle.execute(text, "title").get();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if(quizzes != null){
                    populateScroll();
                }
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
                DBFetch searchCreator = new DBFetch();
                try {
                    quizzes = searchCreator.execute(text, "creator").get();
                } catch (Exception e){
                   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if(quizzes != null) {
                    populateScroll();
                }
            }
        });


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

    public static void setQuizzes(ArrayList<Quiz> result){
        quizzes = result;
    }


}
