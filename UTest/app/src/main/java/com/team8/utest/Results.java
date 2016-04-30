package com.team8.utest;

import android.support.v7.widget.SearchView;

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

}
