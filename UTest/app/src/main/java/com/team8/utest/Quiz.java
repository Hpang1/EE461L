package com.team8.utest;

import java.util.ArrayList;

/**
 * Created by mgrif on 3/26/2016.
 */
public class Quiz {
    private ArrayList<Question> questions;
    private String name;
    private String creator;
    private String password;

    public Quiz(String name, String creator){
        this.name = name;
        this.creator = creator;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public void removeQuestion(Question question){
        questions.remove(question);
    }

    public void editPassword(String password){
        this.password = password;
    }

    public double gradeQuiz(ArrayList<Integer> answers){
        double sum = 0;
        for(int i = 0; i < questions.size(); i++){
            int answer = answers.get(i);
            if(questions.get(i).isCorrect(answer)){
                sum += 1;
            }
        }
        return (sum / questions.size()) * 100;
    }

}
