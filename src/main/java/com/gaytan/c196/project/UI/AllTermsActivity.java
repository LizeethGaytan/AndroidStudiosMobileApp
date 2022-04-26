package com.gaytan.c196.project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gaytan.c196.R;

import java.util.List;

import Database.Repository;
import Entity.Term;

public class AllTermsActivity extends AppCompatActivity {
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.allTermsRecyclerView);
        Repository repository = new Repository(getApplication());
        List<Term> termsList = repository.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(termsList);
    }

    public boolean onCreateOptionsMenu(Menu allTermsMenu) {
        getMenuInflater().inflate(R.menu.allterms_menu, allTermsMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {

            case R.id.refresh_term_menu_item:
                repository = new Repository(getApplication());
                List<Term> allTerms = repository.getAllTerms();
                RecyclerView recyclerView = findViewById(R.id.allTermsRecyclerView);
                final TermAdapter termAdapter = new TermAdapter(this);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
        }
        return super.onOptionsItemSelected(selectedItem);
    }

    public void enterAddTermScreen(View view) {
        Intent directsToAddTermScreen = new Intent(AllTermsActivity.this, AddTermActivity.class);
        startActivity(directsToAddTermScreen);
    }
}