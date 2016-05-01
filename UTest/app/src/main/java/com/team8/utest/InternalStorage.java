package com.team8.utest;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by mgrif on 5/1/2016.
 */
public class InternalStorage {

    private static ArrayList getData(Context context, String filename){
        ArrayList data = null;
        try {
            File file = new File(context.getFilesDir(), filename);
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                data = (ArrayList) in.readObject(); //probably change to results object
                in.close();
            } else{
                data = new ArrayList<>();
                FileOutputStream outputStream = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(outputStream);
                out.writeObject(data);
                out.close();
            }
            if(data == null){
                data = new ArrayList<>();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    private static void writeData(Context context, String filename, Quiz quiz, Results results){
        try {
            File file = new File(context.getFilesDir(), filename);
            ArrayList data = null;
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                data = (ArrayList) in.readObject(); //probably change to results object
                in.close();
            }if(data == null){
                data = new ArrayList<>();
            }
            if(results != null){
                data.add(results);
            } else {
                data.add(quiz);
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(data);
            out.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void clearData(Context context, String filename){
        try {
            ArrayList data;
            File file = new File(context.getFilesDir(), filename);
            if("quizzes.txt".equals(filename)){
                data = new ArrayList<Quiz>();
            } else {
                data = new ArrayList<Results>();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Quiz> getQuizzes(Context context) {
        return (ArrayList<Quiz>) getData(context, "quizzes.txt");
    }

    public static ArrayList<Results> getResults(Context context) {
        return (ArrayList<Results>) getData(context, "results.txt");
    }

    public static void writeQuiz(Context context, Quiz quiz){
        writeData(context, "quizzes.txt", quiz, null);
    }

    public static void writeResults(Context context, Results results){
        writeData(context, "results.txt", null, results);
    }

    public static void clearResults(Context context){
        clearData(context, "results.txt");
    }

    public static void clearQuizzes(Context context){
        clearData(context, "quizzes.txt");
    }




}
