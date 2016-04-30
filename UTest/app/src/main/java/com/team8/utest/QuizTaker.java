package com.team8.utest;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizTaker extends AppCompatActivity {

    Quiz quiz;
    int currentQuestion = 0;
    Button[] buttons = new Button[5];
    ArrayList<Integer> results;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_taker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //will change this later
        quiz = new Quiz();
        Question question1 = new Question();
        Question question2 = new Question();
        Question question3 = new Question();
        Choice choice1 = new Choice("Wrong 1");
        Choice choice2 = new Choice("Wrong 2");
        Choice choice3 = new Choice("Correct");
        choice3.setCorrect(true);
        question1.addChoice(choice1);
        question1.addChoice(choice3);
        question2.addChoice(choice3);
        question2.addChoice(choice2);
        question3.addChoice(choice1);
        question3.addChoice(choice2);
        question3.addChoice(choice3);
        quiz.addQuestion(question1);
        quiz.addQuestion(question2);
        quiz.addQuestion(question3);
        //end of example quiz


        results = new ArrayList<>(quiz.quizSize());
        for(int i = 0; i < results.size(); i++){
            results.set(i, -1);
        }
        currentQuestion = 0;


        final EditText question = (EditText) findViewById(R.id.questionText);
        question.setText(quiz.getQuestion(currentQuestion).getQuestion());
        buttons[0] = (Button) findViewById(R.id.answer1);
        buttons[1] = (Button) findViewById(R.id.answer2);
        buttons[2] = (Button) findViewById(R.id.answer3);
        buttons[3] = (Button) findViewById(R.id.answer4);
        buttons[4] = (Button) findViewById(R.id.answer5);
        for(int i = 0; i < 5; i++){
            buttons[i].setTag(i);
        }
        setupButtons();

        ImageButton prev = (ImageButton) findViewById(R.id.goprev);
        ImageButton next = (ImageButton) findViewById(R.id.gonext);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion--;
                if(quiz.validQuestion(currentQuestion)){
                    question.setText(quiz.getQuestion(currentQuestion).getQuestion());
                    setupButtons();
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(), "No prior questions", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion++;
                if(quiz.validQuestion(currentQuestion)){
                    question.setText(quiz.getQuestion(currentQuestion).getQuestion());
                    setupButtons();
                } else{
                    if(allAnswered()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveResults();
                                //go to results page maybe, or something
                                //this will probably be done in saveResults()
                            }
                        });
                        builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentQuestion--;
                                question.setText(quiz.getQuestion(currentQuestion).getQuestion());
                                setupButtons();
                            }
                        });
                        builder.setMessage("Done with quiz?");
                        builder.setCancelable(false);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Finish quiz first", Toast.LENGTH_SHORT);
                        toast.show();
                        currentQuestion--;
                        question.setText(quiz.getQuestion(currentQuestion).getQuestion());
                        setupButtons();
                    }
                }
            }
        });

    }

    public void setupButtons(){ //
        Question question = quiz.getQuestion(currentQuestion);
        for(int i = 0; i < 5; i++){
            if(question.validChoice(i)){
                buttons[i].setText(question.getChoice(i).getAnswer());
                buttons[i].setClickable(true);
                buttons[i].setPressed(false);
                //change color
                buttons[i].setVisibility(View.VISIBLE);
                if(results.get(i) != -1){
                    buttons[i].setPressed(true);
                    //change color
                }
            } else{
                buttons[i].setClickable(false);
                buttons[i].setVisibility(View.INVISIBLE);
            }
            buttons[i].setOnClickListener(new View.OnClickListener() {  //will change this if not allowing user to go back to previous questions
                @Override
                public void onClick(View v) {
                    //code if allowed to go back and forth between questions
                    Button b = (Button) v;
                    b.setPressed(true);
                    //change color to pressed color
                    int index = (int) b.getTag();
                    for(int i = 0; i < 5; i++){
                        if(i != index){
                            buttons[i].setPressed(false);
                            //change color to unpressed color
                        }
                    }
                    results.set(currentQuestion, index);
                    //code if no going back on questions
                    /*Button button = (Button) v;
                    int number = (int) button.getTag();
                    results.set(currentQuestion, number);
                    currentQuestion ++;
                    if(!quiz.validQuestion(currentQuestion)){
                        saveResults();
                    } else {
                        setupButtons();
                    }*/

                }
            });
        }
    }

    public void saveResults(){
        double grade = quiz.gradeQuiz(results);
        //store in device somewhere or online or both
        //go to results screen/display grade somehow
    }

    public boolean allAnswered(){
        boolean done = true;
        for(int i = 0; i < results.size(); i++){
            if(results.get(i) == -1){
                done = false;
            }
        }
        return done;
    }

}
