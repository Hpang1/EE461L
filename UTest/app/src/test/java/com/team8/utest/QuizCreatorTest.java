package com.team8.utest;

import android.app.Instrumentation;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
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

    }
    @Test
    public void validateAddButton() {
        ImageButton add = (ImageButton) activity.findViewById(R.id.newquestion);
        assertNotNull("Button could not be found", add);
        add.performClick();

    }
    @Test
    public void validateNextButton() {
        ImageButton next = (ImageButton) activity.findViewById(R.id.nextquestion);
        assertNotNull("Button could not be found", next);
        next.performClick();

        //assertTrue("TextView contains incorrect text",
        // "Hello world!".equals(createQuizzes.performClick()));
    }
    @Test
    public void validateDeleteButton() {
        ImageButton delete = (ImageButton) activity.findViewById(R.id.delquestion);
        assertNotNull("Button could not be found", delete);
        delete.performClick();

        //assertTrue("TextView contains incorrect text",
        // "Hello world!".equals(createQuizzes.performClick()));
    }
}