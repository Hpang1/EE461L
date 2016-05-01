package com.team8.utest;

import android.support.v7.widget.SearchView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mgrif on 4/29/2016.
 */
public class Results implements Serializable {

    public Quiz quiz;
    public ArrayList<Integer> results;

    public Results(Quiz quiz, ArrayList<Integer> results){
        this.quiz = quiz;
        this.results = results;
    }
    public Results(){

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

    public static Results deserialize(byte[] input){
        if(input == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        Results object = null;
        try{
            ObjectInputStream is = new ObjectInputStream(in);
            object = (Results) is.readObject();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return object;
    }

}
