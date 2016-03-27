package com.team8.utest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class QuizCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout layout = (LinearLayout)findViewById(R.id.createLayout);
        EditText question = new EditText(this);
        question.setText("Sample Question");

        LinearLayout questionLayout = new LinearLayout(this);
        questionLayout.setOrientation(LinearLayout.HORIZONTAL);
        EditText answer1 = new EditText(this);
        answer1.setText("Sample Answer Choice");
        Button newQuestion = new Button(this);
        questionLayout.addView(answer1);
        questionLayout.addView(newQuestion);

        layout.addView(question);
        layout.addView(questionLayout);


    }

}
