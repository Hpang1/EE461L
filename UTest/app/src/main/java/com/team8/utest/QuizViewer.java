package com.team8.utest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizViewer extends AppCompatActivity {

    Button[] buttons = new Button[5];
    int currentQuestion = 0;
    Quiz quiz;
    ArrayList<Integer> results;
    ArrayList<Integer> answers;
    boolean prevResults = false;
    TextView outOfTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Results result = null;
        try{
            byte[] quizBack = getIntent().getByteArrayExtra("quiz");
            byte[] resultBack = getIntent().getByteArrayExtra("result");
            quiz = Quiz.deserialize(quizBack);
            result = Results.deserialize(resultBack);
        } catch(Exception e){
            e.printStackTrace();
        }
        if(result != null){
            results = result.results;
            quiz = result.quiz;
            prevResults = true;
        }


        //quiz = new Quiz();  //change later
        //results = new ArrayList<Integer>(); //make all -1, if from quizpast
        answers = quiz.getCorrectAnswers();

        final EditText questionText = (EditText) findViewById(R.id.questionTextView);
        buttons[0] = (Button) findViewById(R.id.answer1View);
        buttons[1] = (Button) findViewById(R.id.answer2View);
        buttons[2] = (Button) findViewById(R.id.answer3View);
        buttons[3] = (Button) findViewById(R.id.answer4View);
        buttons[4] = (Button) findViewById(R.id.answer5View);
        for(int i = 0; i < 5; i++){
            buttons[i].setTag(i);
        }
        questionText.setText(quiz.getQuestion(currentQuestion).getQuestion());
        setupButtons();

        ImageButton prev = (ImageButton) findViewById(R.id.goprev);
        ImageButton next = (ImageButton) findViewById(R.id.gonext);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion--;
                if(quiz.validQuestion(currentQuestion)){
                    questionText.setText(quiz.getQuestion(currentQuestion).getQuestion());
                    setupButtons();
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(), "No previous questions", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion++;
                if(quiz.validQuestion(currentQuestion)){
                    questionText.setText(quiz.getQuestion(currentQuestion).getQuestion());
                    setupButtons();
                } else{
                    Intent intent;
                    if(prevResults){
                        intent = new Intent(QuizViewer.this, QuizResults.class);
                    } else{
                        intent = new Intent(QuizViewer.this, QuizPast.class);
                    }
                    startActivity(intent);
                }
            }
        });
        outOfTime = (TextView) findViewById(R.id.textView2);


    }

    public void setupButtons() { //
        outOfTime.setText("");
        Question question = quiz.getQuestion(currentQuestion);
        int chosen = results.get(currentQuestion);
        int correct = answers.get(currentQuestion);
        for (int i = 0; i < 5; i++) {
            if (question.validChoice(i)) {
                buttons[i].setText(question.getChoice(i).getAnswer());
                //buttons[i].setBackgroundResource(R.drawable.answerbutton);  //change
                buttons[i].setVisibility(View.VISIBLE);
                if (i == correct) {
                    buttons[i].setBackgroundResource(R.drawable.correctanswerbutton);
                    //set to green
                } else if(i == chosen && i != correct && chosen > 0){
                    //set to red
                    buttons[i].setBackgroundResource(R.drawable.incorrectanswerbutton);
                } else{
                    //set to normal color
                    buttons[i].setBackgroundResource(R.drawable.answerbutton);
                }

            } else {
                buttons[i].setVisibility(View.INVISIBLE);
            }
            buttons[i].setClickable(false);

        }
        if(chosen < 0){
            //something
            outOfTime.setText("No choice");
        }
    }

}
