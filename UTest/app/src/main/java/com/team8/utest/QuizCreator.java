package com.team8.utest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizCreator extends AppCompatActivity {

    //LinearLayout layout;
    int numquestions = 0;
    Quiz quiz = new Quiz();
    ArrayList<Integer> correctAnswer = new ArrayList<>();
    int currentQuestion = 0;
    RelativeLayout[] choiceArray = new RelativeLayout[5];
    EditText question;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RelativeLayout parent = (RelativeLayout) findViewById(R.id.question);



        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.activity_quiz_creator, null);

        //layout = (LinearLayout)findViewById(R.id.createLayout);
        question = (EditText) findViewById(R.id.questionText);
        //question.setText("Sample Question");

        numquestions += 1;
        correctAnswer.add(0);   //default 0 correct
        //parent.addView(question);
        RelativeLayout choice = null;
        for(int i = 0; i < 5; i++){
            //RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.choice, null);
            //RelativeLayout layout = findViewById(R.id.)
            switch(i){
                case 0: choice = (RelativeLayout) findViewById(R.id.choice1); break;
                case 1: choice = (RelativeLayout) findViewById(R.id.choice2); break;
                case 2: choice = (RelativeLayout) findViewById(R.id.choice3); break;
                case 3: choice = (RelativeLayout) findViewById(R.id.choice4); break;
                case 4: choice = (RelativeLayout) findViewById(R.id.choice5); break;
            }
            choiceArray[i] = choice;
            setChoice(choice, i);
        }
        RelativeLayout bar = (RelativeLayout) findViewById(R.id.createBar);
        ImageButton previous = (ImageButton) bar.findViewById(R.id.prevquestion);
        ImageButton next = (ImageButton)bar.findViewById(R.id.nextquestion);
        ImageButton add = (ImageButton)bar.findViewById(R.id.newquestion);
        ImageButton delete = (ImageButton)bar.findViewById(R.id.delquestion);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuestion == 0){return;}
                if(saveQuestion()){
                    Question prevQuestion = quiz.getQuestion(currentQuestion - 1);
                    repopulateQuestion(prevQuestion);
                    currentQuestion--;
                } else{
                    //display error, no choices
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quiz.validQuestion(currentQuestion+1)){
                    if(saveQuestion()){
                        Question nextQuestion = quiz.getQuestion(currentQuestion + 1);
                        repopulateQuestion(nextQuestion);
                        currentQuestion++;
                    } else{
                        //display error, no choices
                    }

                } else{
                    //setContentView(R.id.something);
                }


            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveQuestion()){
                    Question blankQuestion = new Question();
                    repopulateQuestion(blankQuestion);
                    currentQuestion++;
                } else{
                    //error, don't need new question yet
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quiz.quizSize() > 1){
                    quiz.removeQuestion(currentQuestion);
                    if(currentQuestion > 0){
                        Question prevQuestion = quiz.getQuestion(currentQuestion - 1);
                        repopulateQuestion(prevQuestion);
                        currentQuestion--;
                    } else{
                        Question nextQuestion = quiz.getQuestion(currentQuestion + 1);
                        repopulateQuestion(nextQuestion);
                        currentQuestion++;
                    }

                }

            }
        });



        //addChoice(true, false);

       /// setContentView(parent);

    }

    public void repopulateQuestion(Question prevQuestion){
        question.setText(prevQuestion.getQuestion());
        for(int i = 0; i < 5; i++){
            if(prevQuestion.validChoice(i)){
                EditText choiceText = (EditText) choiceArray[i].findViewById(R.id.textView);
                Choice choice = prevQuestion.getChoice(i);
                choiceText.setText(choice.getAnswer());
                CheckBox correct = (CheckBox) choiceArray[i].findViewById(R.id.correct);
                correct.setChecked(choice.correctAnswer());
            }
        }

    }

    public boolean saveQuestion(){
        Question newQuestion = new Question();
        newQuestion.question = question.getText().toString();
        int choices = 0;
        for(int i = 0; i < 5; i++){
            EditText choiceText = (EditText) choiceArray[i].findViewById(R.id.textView);
            if(choiceText.getText().toString().length() != 0){
                choices++;
                Choice newChoice = new Choice(choiceText.getText().toString());
                CheckBox correctBox = (CheckBox) choiceArray[i].findViewById(R.id.correct);
                newChoice.setCorrect(correctBox.isChecked());
            }
        }
        if(choices >= 2){
            quiz.editQuestion(currentQuestion, newQuestion);
            return true;
        } else{
            return false;
        }
    }


    private RelativeLayout setChoice(RelativeLayout layout, int i){
        //TextView number = (TextView) layout.findViewById(R.id.number);
        //number.setText(Integer.toString(i+1));
        CheckBox correct = (CheckBox) layout.findViewById(R.id.correct);
        correct.setTag(i);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer k = (Integer) v.getTag();
                EditText text = (EditText) choiceArray[k].findViewById(R.id.textView);
                if(text.getText().toString().length() == 0){
                    CheckBox check = (CheckBox) v;
                    check.setChecked(false);
                    return;
                }
                v.setClickable(false);
                correctAnswer.set(currentQuestion, k);  //get choice number


                for(int i = 0; i < 5; i++){
                    if(i != k){
                        CheckBox button = (CheckBox) choiceArray[i].findViewById(R.id.correct);
                        button.setChecked(false);
                        button.setClickable(true);
                    }
                }
            }
        });
        return layout;
    }

    /*public void addChoice(boolean includeAddButton, boolean includeRemoveButton){   //only adds to end for now
        //always put remove button for these
        LinearLayout questionLayout = new LinearLayout(this);
        questionLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView number = new TextView(this);
        number.setText(Integer.toString(numquestions));
        questionLayout.addView(number);
        EditText answer1 = new EditText(this);
        answer1.setText("Sample Answer Choice");
        questionLayout.addView(answer1);
        questionLayout.setTag(numquestions);

        if(includeAddButton){
            Button newQuestion = new Button(this);
            newQuestion.setOnClickListener(new addChoice());
            //newQuestion.setTag(numquestions);

            questionLayout.addView(newQuestion);
        } if (includeRemoveButton){
            Button removeQuestion = new Button(this);
            removeQuestion.setOnClickListener(new removeChoice());
            //removeQuestion.setTag(numquestions);

            questionLayout.addView(removeQuestion);
        }

        layout.addView(questionLayout);
    }

    public void removeChoice(int index){
        layout.removeViewAt(index);
    }

    private class addChoice implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(numquestions < 4){
                numquestions += 1;
                addChoice(true, true);
            } else if (numquestions == 4){
                //add choice without button
                numquestions += 1;
                addChoice(false, true);
            }
        }
    }

    private class removeChoice implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(numquestions > 1){
                numquestions -= 1;
                removeChoice((int)((View) v.getParent()).getTag());

            }
        }

    }*/

}
