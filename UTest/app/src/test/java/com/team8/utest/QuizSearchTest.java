package com.team8.utest;

import android.app.Instrumentation;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
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

public class QuizSearchTest {
    private QuizSearch activity;
    SearchView search;
    LinearLayout scroll;

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.buildActivity(QuizSearch.class)
                .create().get();
        search = (SearchView) activity.findViewById(R.id.searchView2);
        scroll = (LinearLayout) activity.findViewById(R.id.searchScroll2);
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    // The test simply checks that our TextView exists and has the text "Hello world!"
    @Test
    public void testCreatorSearch() {
        Button searchButton = (Button) activity.findViewById(R.id.searchCreator);
        assertNotNull("Button could not be found", searchButton);
        search.setQuery("creator 9",false);
        search.clearFocus();
        searchButton.performClick();
        int count = scroll.getChildCount();
        assertEquals(100,count);
    }
    @Test
    public void testCreatorSearch2() {
        Button searchButton = (Button) activity.findViewById(R.id.searchCreator);
        assertNotNull("Button could not be found", searchButton);
        String searchText = "creator2";
        search.setQuery(searchText,false);
        search.clearFocus();
        searchButton.performClick();
        int count = scroll.getChildCount();
        for(int i = 0; i < count; i++) {
            TextView text = (TextView) scroll.getChildAt(i).findViewById(R.id.creator);
            assertEquals(searchText, text.getText());
        }
    }
    @Test
    public void testCreatorSearch3() {
        Button searchButton = (Button) activity.findViewById(R.id.searchAuthor);
        assertNotNull("Button could not be found", searchButton);
        String searchText = "quiz0";
        search.setQuery(searchText,false);
        search.clearFocus();
        searchButton.performClick();
        int count = scroll.getChildCount();
        for(int i = 0; i < count; i++) {
            TextView text = (TextView) scroll.getChildAt(i).findViewById(R.id.quiztitle);
            assertEquals(searchText, text.getText());
        }
    }
}