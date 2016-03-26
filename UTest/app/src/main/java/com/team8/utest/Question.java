package com.team8.utest;

import java.util.ArrayList;

/**
 * Created by mgrif on 3/26/2016.
 */
public class Question {
    String question;
    ArrayList<Choice> choices;

    public Question(String question){
        this.question = question;
    }

    public Question(){}

    public void addChoice(Choice choice){
        choices.add(choice);
    }

    public void removeChoice(Choice choice){
        choices.remove(choice);
    }
    public String getQuestion(){
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Question){
            Question choice = (Question) o;
            return question.equals(choice.getQuestion());
        }
        return false;
    }

    public boolean isCorrect(int choice){
        return choices.get(choice).correctAnswer();
    }
}

