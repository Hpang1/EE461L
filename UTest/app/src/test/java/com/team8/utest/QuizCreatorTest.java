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

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)

public class QuizCreatorTest {
    private QuizCreator activity;

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.buildActivity(QuizCreator.class)
                .create().get();
        Question question = new Question();
        EditText choice1 = (EditText) activity.choiceArray[0].findViewById(R.id.textView);
        choice1.setText("Choice 1");
        EditText choice2 = (EditText) activity.choiceArray[1].findViewById(R.id.textView);
        choice2.setText("Choice 2");
        CheckBox correct = (CheckBox) activity.choiceArray[1].findViewById(R.id.correct);
        correct.setChecked(true);
        activity.saveQuestion();
        activity.quiz.addQuestion(question);
        activity.quiz.addQuestion(question);
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void validatePrevButton() {
        ImageButton prev = (ImageButton) activity.findViewById(R.id.prevquestion);
        assertNotNull("Button could not be found", prev);
        activity.currentQuestion = 0;
        prev.performClick();
        assertTrue(activity.currentQuestion == 0);
        activity.currentQuestion = 1;
        prev.performClick();
        assertTrue(activity.currentQuestion == 0);
    }
    @Test
    public void validateAddButton() {
        ImageButton add = (ImageButton) activity.findViewById(R.id.newquestion);
        assertNotNull("Button could not be found", add);
        EditText choice1 = (EditText) activity.choiceArray[0].findViewById(R.id.textView);
        choice1.setText("Choice 1");
        EditText choice2 = (EditText) activity.choiceArray[1].findViewById(R.id.textView);
        choice2.setText("Choice 2");
        CheckBox correct = (CheckBox) activity.choiceArray[1].findViewById(R.id.correct);
        correct.setChecked(true);
        int size = activity.quiz.quizSize();
        add.performClick();
        assertNotNull(activity.quiz.getQuestion(2));

    }
    @Test
    public void testSaveQuestion() {

        EditText choice1 = (EditText) activity.choiceArray[0].findViewById(R.id.textView);
        choice1.setText("Choice 1");
        EditText choice2 = (EditText) activity.choiceArray[1].findViewById(R.id.textView);
        choice2.setText("Choice 2");
        CheckBox correct = (CheckBox) activity.choiceArray[0].findViewById(R.id.correct);
        correct.setChecked(true);
        assertTrue(activity.saveQuestion());

    }
    @Test
    public void testSaveQuestion2() {

        EditText choice1 = (EditText) activity.choiceArray[0].findViewById(R.id.textView);
        choice1.setText("Choice 1");
        CheckBox correct = (CheckBox) activity.choiceArray[0].findViewById(R.id.correct);
        correct.setChecked(true);
        assertFalse(activity.saveQuestion());

    }
    @Test
    public void validateNextButton() {
        ImageButton next = (ImageButton) activity.findViewById(R.id.nextquestion);
        assertNotNull("Button could not be found", next);
        activity.currentQuestion = 0;
        next.performClick();
        assertEquals(1,activity.currentQuestion);

        //assertTrue("TextView contains incorrect text",
        // "Hello world!".equals(createQuizzes.performClick()));
    }
    @Test
    public void validateDeleteButton() {
        ImageButton delete = (ImageButton) activity.findViewById(R.id.delquestion);
        assertNotNull("Button could not be found", delete);
        int size = activity.quiz.quizSize();
        delete.performClick();
        assertEquals(size-1,activity.quiz.quizSize());

        //assertTrue("TextView contains incorrect text",
        // "Hello world!".equals(createQuizzes.performClick()));
    }
}