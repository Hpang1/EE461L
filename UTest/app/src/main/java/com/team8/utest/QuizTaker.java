package com.team8.utest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuizTaker extends AppCompatActivity {

    Quiz quiz;
    int currentQuestion = 0;
    Button[] buttons = new Button[5];
    ArrayList<Integer> results;
    Drawable selected;
    Drawable unselected;
    CountDownTimer timeKeeper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_taker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         //code in future
        //byte[] serialized = getIntent().getByteArrayExtra("quiz");
        //quiz = Quiz.deserialize(serialized);


        //will change this later
        Quiz testquiz = new Quiz();
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
        testquiz.addQuestion(question1);
        testquiz.addQuestion(question2);
        testquiz.addQuestion(question3);

        byte[] serialize = testquiz.serialize();
        quiz = Quiz.deserialize(serialize);
        //end of example quiz


        int size = quiz.quizSize();
        results = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            results.add(-1);
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
                    currentQuestion++;
                    question.setText(quiz.getQuestion(currentQuestion).getQuestion());
                    setupButtons();
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(QuizTaker.this);
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


        final TextView timer = (TextView) findViewById(R.id.textView2);    //change later
        quiz.setTime(15);    //remove later
        long time = quiz.time; //change later

        //time = 15000;//remove later
        if(time > 0){
            timeKeeper = new CountDownTimer(time, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    int minutes = (int) (millisUntilFinished / 60000);
                    int seconds = (int) ((millisUntilFinished - (minutes * 60000)) / 1000);
                    if(seconds > 9){
                        timer.setText(String.format("%d:%d", minutes, seconds));
                    } else{
                        timer.setText(String.format("%d:0%d", minutes, seconds));
                    }
                }

                @Override
                public void onFinish() {
                    saveResults();
                }
            };
            timeKeeper.start();
        }

    }

    public void setupButtons(){ //
        Question question = quiz.getQuestion(currentQuestion);
        for(int i = 0; i < 5; i++){
            if(question.validChoice(i)){
                buttons[i].setText(question.getChoice(i).getAnswer());
                buttons[i].setClickable(true);
                buttons[i].setPressed(false);
                //change color
                //buttons[i].setBackgroundColor(Color.WHITE);   //change later
                buttons[i].setBackgroundResource(R.drawable.answerbutton);
                buttons[i].setVisibility(View.VISIBLE);

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

                    b.setBackgroundResource(R.drawable.selectedanswerbutton);
                    int index = (int) b.getTag();
                    for(int i = 0; i < 5; i++){
                        if(i != index){
                            buttons[i].setPressed(false);
                            buttons[i].setBackgroundResource(R.drawable.answerbutton);
                            //change color to unpressed color
                            //buttons[i].setBackgroundColor(Color.WHITE);
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
        int chosen = results.get(currentQuestion);
        if(chosen != -1){
            buttons[chosen].setPressed(true);    //change this
            buttons[chosen].setBackgroundResource(R.drawable.selectedanswerbutton);
            //buttons[chosen].setBackgroundColor(Color.RED);
            //change color
        }
    }

    public void saveResults(){
        timeKeeper.cancel();
        double grade = quiz.gradeQuiz(results);
        String filename = "results.txt";
        try {
            File file = new File(this.getFilesDir(), filename);
            ArrayList<Results> allResults = null;
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                allResults = (ArrayList) in.readObject(); //probably change to results object
                in.close();
            }if(allResults == null){
                allResults = new ArrayList<>();
            }

            Results resultStorage = new Results(quiz, results);
            allResults.add(resultStorage);

            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(allResults);
            out.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        //go to results screen/display grade somehow
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizTaker.this);
        builder.setPositiveButton("Return home", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(QuizTaker.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("New quiz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(QuizTaker.this, QuizSearch.class);
                startActivity(intent);
            }
        });
        builder.setMessage("Grade: " + Integer.toString((int) grade));
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

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
