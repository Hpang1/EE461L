package com.team8.utest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mgrif on 3/26/2016.
 */
public class Quiz implements Serializable{
    private ArrayList<Question> questions = new ArrayList<>();
    public String name;
    public String creator;
    //private String password;

    public Quiz(String name, String creator){
        this.name = name;
        this.creator = creator;
    }

    public Quiz(){
    }

    public int quizSize(){
        return questions.size();
    }

    public Question getQuestion(int i){
        return questions.get(i);
    }

    public boolean validQuestion(int i){
        return i < questions.size();
    }

    public void editQuestion(int index, Question question){
        if(index < questions.size()){
            questions.set(index, question);
        } else{
            questions.add(index, question);
        }
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public void removeQuestion(Question question){ questions.remove(question);}

    public void removeQuestion(int i){
        questions.remove(i);
    }

    /*public void editPassword(String password){
        this.password = password;
    }*/

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
