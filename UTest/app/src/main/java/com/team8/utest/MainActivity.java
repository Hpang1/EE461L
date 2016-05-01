package com.team8.utest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void goSearch(View view){
        Intent intent = new Intent(this, QuizSearch.class);
        //swap back after testing
        //Intent intent = new Intent(this, QuizTaker.class);
        startActivity(intent);
    }

    public void goCreate(View view){
        Intent intent = new Intent(this, QuizCreator.class);
        startActivity(intent);
    }

    public void goResults(View view){
        Intent intent = new Intent(this, QuizResults.class);
        startActivity(intent);
    }

    public void goPast(View view){
        Intent intent = new Intent(this, QuizPast.class);
        startActivity(intent);
    }

    public void logOut(View view){
    }

    public void clearQuizzes(View view){
        String filename = "quizzes.txt";
        ArrayList<Quiz> allResults = null;
        try {
            File file = new File(this.getFilesDir(), filename);
            allResults = new ArrayList<Quiz>();
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(allResults);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Cleared Quizzes", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void clearResults(View view) {
        String filename = "results.txt";
        ArrayList<Results> allResults = null;
        try {
            File file = new File(this.getFilesDir(), filename);
            allResults = new ArrayList<Results>();
            FileOutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(allResults);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Cleared Results", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
