package com.gaytan.c196.project.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gaytan.c196.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void enterAllTermsScreen(View view) {
        Intent directsToAllTermsScreen = new Intent(HomeActivity.this, AllTermsActivity.class);
        startActivity(directsToAllTermsScreen);
    }
}