package com.team8.utest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mgrif on 3/26/2016.
 */
public class Quiz implements Serializable{
    private ArrayList<Question> questions = new ArrayList<>();
    public String name;
    public String creator;
    public long time = -1;
    //private String password;

    public Quiz(String name, String creator){
        this.name = name;
        this.creator = creator;
    }

    public ArrayList<Integer> getCorrectAnswers(){
        ArrayList<Integer> answers = new ArrayList<>();
        for(Question question : questions){
            answers.add(question.correctChoice());
        }
        return answers;
    }

    public void setNames(String creator, String name){
        this.creator = creator;
        this.name = name;
    }

    public void setTime(int minutes){
        time = (long) (minutes * 1000 * 60);
    }

    public Quiz(){
    }

    public byte[] serialize(){
        byte[] data = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(this);
            data = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Quiz deserialize(byte[] input){
        if(input == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        Quiz object = null;
        try{
            ObjectInputStream is = new ObjectInputStream(in);
            object = (Quiz) is.readObject();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return object;
    }

    public int quizSize(){
        return questions.size();
    }

    public Question getQuestion(int i){
        return questions.get(i);
    }

    public boolean validQuestion(int i){

        return (i < questions.size()) && (i >= 0) && (questions.size() > 1);
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

    public int gradeQuiz(ArrayList<Integer> answers){
        int sum = 0;
        for(int i = 0; i < questions.size(); i++){
            int answer = answers.get(i);
            if(questions.get(i).isCorrect(answer)){
                sum += 1;
            }
        }
        return sum;
    }

}
