package com.team8.utest;

import android.app.Instrumentation;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivityTest {
    private MainActivity activity;

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.buildActivity(MainActivity.class)
                .create().get();
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void validateCreateButton() {
        Button createQuizzes = (Button) activity.findViewById(R.id.createQuizzes);
        assertNotNull("Button could not be found", createQuizzes);
        createQuizzes.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(QuizCreator.class.getCanonicalName(), intent.getComponent().getClassName());
        //assertTrue("TextView contains incorrect text",
               // "Hello world!".equals(createQuizzes.performClick()));
    }
    @Test
    public void validateSearchButton() {
        Button searchQuizzes = (Button) activity.findViewById(R.id.searchQuizzes);
        assertNotNull("Button could not be found", searchQuizzes);
        searchQuizzes.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(QuizSearch.class.getCanonicalName(), intent.getComponent().getClassName());
        //assertTrue("TextView contains incorrect text",
        // "Hello world!".equals(createQuizzes.performClick()));
    }
    @Test
    public void validateMyQuizzesButton() {
        Button myQuizzes = (Button) activity.findViewById(R.id.myQuizzes);
        assertNotNull("Button could not be found", myQuizzes);
        myQuizzes.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(QuizPast.class.getCanonicalName(), intent.getComponent().getClassName());
        //assertTrue("TextView contains incorrect text",
        // "Hello world!".equals(createQuizzes.performClick()));
    }
    @Test
    public void validateResultsButton() {
        Button myResults = (Button) activity.findViewById(R.id.myResults);
        assertNotNull("Button could not be found", myResults);
        myResults.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(QuizResults.class.getCanonicalName(), intent.getComponent().getClassName());
        //assertTrue("TextView contains incorrect text",
        // "Hello world!".equals(createQuizzes.performClick()));
    }
}
