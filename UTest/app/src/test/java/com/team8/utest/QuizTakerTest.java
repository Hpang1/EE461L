package com.team8.utest;

import android.app.Instrumentation;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.thoughtworks.xstream.mapper.Mapper;

import org.robolectric.Shadows;
import org.robolectric.internal.Shadow;
import org.robolectric.shadows.ShadowToast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)

public class QuizTakerTest {
    private QuizTaker activity;
    Quiz testquiz = new Quiz();

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.buildActivity(QuizTaker.class)
                .create().get();
        Question question1 = new Question();
        Question question2 = new Question();
        Question question3 = new Question();
        Choice choice1 = new Choice("wrong 1");
        Choice choice2 = new Choice("wrong 2");
        Choice choice3 = new Choice("correct");
        choice3.setCorrect(true);
        question1.addChoice(choice1);
        question1.addChoice(choice3);
        question2.addChoice(choice3);
        question2.addChoice(choice2);
        question3.addChoice(choice1);
        question3.addChoice(choice2);
        question3.addChoice(choice3);
        testquiz.addQuestion(question1);
        testquiz.addQuestion(question2);
        testquiz.addQuestion(question3);

        activity.quiz = testquiz;
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void testButtonSetup() {
        activity.currentQuestion = 0;
        activity.setupButtons();
        assertEquals("correct", activity.buttons[1].getText());
    }
    @Test
    public void testPrev() {
        activity.currentQuestion = 1;
        ImageButton previous = (ImageButton) activity.findViewById(R.id.goprev);
        previous.performClick();
        assertEquals("correct", activity.buttons[1].getText());
    }
    @Test
    public void testNext() {
        activity.currentQuestion = 1;
        ImageButton previous = (ImageButton) activity.findViewById(R.id.gonext);
        previous.performClick();
        assertEquals("wrong 2", activity.buttons[1].getText());
        assertEquals(2, activity.currentQuestion);
    }
    @Test
    public void testResultStore() {
        String filename = "results.txt";
        File file = new File(activity.getFilesDir(), filename);
        ArrayList<Results> allResults = null;
        activity.saveResults();
        try {
            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                allResults = (ArrayList) in.readObject(); //probably change to results object
                in.close();
            }
            assertEquals(testquiz,allResults.get(0).quiz);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}