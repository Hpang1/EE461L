package com.team8.utest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class QuizCreator extends AppCompatActivity {

    //LinearLayout layout;
    int numquestions = 0;
    Quiz quiz = new Quiz();
    //ArrayList<Integer> correctAnswer = new ArrayList<>();
    int currentQuestion = 0;
    RelativeLayout[] choiceArray = new RelativeLayout[5];
    EditText question;


    LinearLayout layout;
    DBPush db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DBPush();

        //layout = (LinearLayout)((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout, null);

        //RelativeLayout parent = (RelativeLayout) findViewById(R.id.question);



        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.activity_quiz_creator, null);

        //layout = (LinearLayout)findViewById(R.id.createLayout);
        question = (EditText) findViewById(R.id.questionText);
        //question.setText("Sample Question");

        numquestions += 1;
        //correctAnswer.add(0);   //default 0 correct
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

        Question startingQuestion = new Question();
        repopulateQuestion(startingQuestion);

        RelativeLayout bar = (RelativeLayout) findViewById(R.id.createBar);
        ImageButton previous = (ImageButton) bar.findViewById(R.id.prevquestion);
        ImageButton next = (ImageButton)bar.findViewById(R.id.nextquestion);
        ImageButton add = (ImageButton)bar.findViewById(R.id.newquestion);
        ImageButton delete = (ImageButton)bar.findViewById(R.id.delquestion);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentQuestion == 0){return;}
                saveQuestion();
                Question prevQuestion = quiz.getQuestion(currentQuestion - 1);
                repopulateQuestion(prevQuestion);
                currentQuestion--;
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
                        //display error, no choices or no correct answer
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid question", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } else {
                    if(saveQuestion()){
                        setContentView(R.layout.content_quiz_submit);
                        //toolbar here if i feel like it
                        final EditText creator= (EditText) findViewById(R.id.namefield);
                        final EditText name = (EditText) findViewById(R.id.titlefield);
                        final EditText time = (EditText) findViewById(R.id.timeField);
                        Button submit = (Button) findViewById(R.id.submitbutton);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quiz.setNames(creator.getText().toString(), name.getText().toString());
                                int minutes = -1;
                                try{
                                    minutes = Integer.parseInt(time.getText().toString());
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                                quiz.setTime(minutes);
                                storeQuiz();
                                //submit quiz
                                //save quiz locally
                                Intent intent = new Intent(QuizCreator.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid question", Toast.LENGTH_SHORT);
                        toast.show();
                    }

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
                    Toast toast = Toast.makeText(getApplicationContext(), "Finish this question first", Toast.LENGTH_SHORT);
                    toast.show();
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
        int size = prevQuestion.choices.size();
        for(int i = 0; i < 5; i++){
            EditText choiceText = (EditText) choiceArray[i].findViewById(R.id.textView);
            CheckBox correct = (CheckBox) choiceArray[i].findViewById(R.id.correct);
            if(prevQuestion.validChoice(i)){
                Choice choice = prevQuestion.getChoice(i);
                choiceText.setText(choice.getAnswer());
                boolean correctAnswer = choice.correctAnswer();
                if(correctAnswer){
                    correct.setClickable(false);
                } else{
                    correct.setClickable(true);
                }
                correct.setChecked(correctAnswer);
            } else{
                choiceText.setText("");
                correct.setChecked(false);
                correct.setClickable(true);
            }
        }
        /*if (size == 0){
            CheckBox correct = (CheckBox) choiceArray[0].findViewById(R.id.correct);
            correct.setChecked(true);
            correct.setClickable(false);
        }*/

    }

    public boolean saveQuestion(){
        Question newQuestion = new Question();
        newQuestion.question = question.getText().toString();
        int choices = 0;
        boolean hasCorrectAnswer = false;
        for(int i = 0; i < 5; i++){
            EditText choiceText = (EditText) choiceArray[i].findViewById(R.id.textView);
            if(choiceText.getText().toString().length() != 0){
                choices++;
                Choice newChoice = new Choice(choiceText.getText().toString());
                CheckBox correctBox = (CheckBox) choiceArray[i].findViewById(R.id.correct);
                boolean correctAnswer = correctBox.isChecked();
                if(correctAnswer) hasCorrectAnswer = true;
                newChoice.setCorrect(correctAnswer);
                newQuestion.addChoice(newChoice);
            }
        }
        if(choices >= 2 && hasCorrectAnswer){
            quiz.editQuestion(currentQuestion, newQuestion);
            return true;
        } else {
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
                v.setClickable(false);
                if(text.getText().toString().length() == 0){
                    CheckBox check = (CheckBox) v;
                    check.setChecked(false);
                    v.setClickable(true);
                    return;
                }

                //correctAnswer.(currentQuestion, k);  //get choice number


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

    public void storeQuiz(){
        InternalStorage.writeQuiz(QuizCreator.this, quiz);
        db.execute(quiz);
        //push to database here
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
