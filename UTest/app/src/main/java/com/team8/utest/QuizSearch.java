package com.team8.utest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class QuizSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SearchView searchBar = (SearchView) findViewById(R.id.searchView2);
        Button searchTitle = (Button) findViewById(R.id.searchAuthor);
        Button searchCreator = (Button) findViewById(R.id.searchCreator);


        //this functionality can be replaced with the strategy design later
        searchTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBar.getQuery().toString();
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
            }
        });


    }

}
