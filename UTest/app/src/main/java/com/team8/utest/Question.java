package com.team8.utest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mgrif on 3/26/2016.
 */
public class Question implements Serializable {
    String question;
    ArrayList<Choice> choices = new ArrayList<>();


    public int correctChoice() {
        int correct = 0;
        for (int i = 0; i < choices.size(); i++) {
            if(choices.get(i).correctAnswer()) correct = i;
        }
        return correct;
    }

    public boolean validChoice(int i){
        return i < choices.size();
    }

    public Question(String question){
        this.question = question;
    }

    public Question(){}

    public Choice getChoice(int i){
        return choices.get(i);
    }

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
        return (choice > 0 ) && choices.get(choice).correctAnswer();
    }
}

