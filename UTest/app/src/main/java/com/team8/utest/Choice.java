package com.team8.utest;

/**
 * Created by mgrif on 3/26/2016.
 */
public class Choice {
    private String answer;
    private boolean isCorrect;

    public Choice(String answer){
        this.answer = answer;
    }
    public Choice(){}

    public void setCorrect(boolean correct){
        this.isCorrect = correct;
    }

    public boolean correctAnswer(){
        return isCorrect;
    }
    public void editAnswer(String answer){
        this.answer = answer;
    }
    public String getAnswer(){
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Choice){
            Choice choice = (Choice) o;
            return answer.equals(choice.getAnswer());
        }
        return false;
    }
}
