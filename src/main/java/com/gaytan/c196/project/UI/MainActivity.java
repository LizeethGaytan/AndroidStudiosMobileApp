package com.gaytan.c196.project.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gaytan.c196.R;

import Database.Repository;
import Entity.Assessment;
import Entity.Course;
import Entity.Term;

public class MainActivity extends AppCompatActivity {
    public static int alertNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterHomeScreen(View view) {
        Intent directsToHomeScreen = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(directsToHomeScreen);
        Repository repository = new Repository(getApplication());
    }
}