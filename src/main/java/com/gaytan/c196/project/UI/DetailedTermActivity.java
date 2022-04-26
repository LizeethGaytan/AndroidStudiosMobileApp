package com.gaytan.c196.project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gaytan.c196.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Database.Repository;
import Entity.Course;
import Entity.Term;

public class DetailedTermActivity extends AppCompatActivity {
    String termName;
    String termStartDate;
    String termEndDate;
    int termID;
    EditText editTermName;
    EditText editTermStartDate;
    EditText editTermEndDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        termName = getIntent().getStringExtra("termName");
        editTermName = findViewById(R.id.termName);
        editTermName.setText(termName);
        termStartDate = getIntent().getStringExtra("termStartDate");
        editTermStartDate = findViewById(R.id.termStartDate);
        editTermStartDate.setText(termStartDate);
        termEndDate = getIntent().getStringExtra("termEndDate");
        editTermEndDate = findViewById(R.id.termEndDate);
        editTermEndDate.setText(termEndDate);
        termID = getIntent().getIntExtra("termID", -1);
        RecyclerView recyclerView = findViewById(R.id.detailedTermsRecyclerView);
        repository = new Repository(getApplication());
        List<Course> filteredCourses = repository.getAllCoursesForTerm(termID);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(filteredCourses);

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, monthOfYear);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateStartDateLabel();
            }
        };
        editTermStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DetailedTermActivity.this, startDate, startDateCalendar
                        .get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH),
                        startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, monthOfYear);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEndDateLabel();
            }
        };
        editTermEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DetailedTermActivity.this, endDate, endDateCalendar
                        .get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH),
                        endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartDateLabel() {
        String startDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(startDateFormat, Locale.US);
        editTermStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel() {
        String endDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(endDateFormat, Locale.US);
        editTermEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu editTermMenu) {
        getMenuInflater().inflate(R.menu.detailedterm_menu, editTermMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.detailed_term_update_term_menu_item:
                Term termToUpdate;
                termToUpdate = new Term(termID, editTermName.getText().toString(), editTermStartDate.getText().toString(), editTermEndDate.getText().toString());
                repository.update(termToUpdate);
                enterAllTermsScreen(selectedItem.getActionView());
                return true;

            case R.id.detailed_term_delete_term_menu_item:
                List<Term> currentTermList = repository.getSingleTerm(termID);
                Term currentTerm = currentTermList.get(0);
                List<Course> currentCourseList = repository.getAllCoursesForTerm(termID);
                if(currentCourseList.size() ==  0) {
                    repository.delete(currentTerm);
                    Toast.makeText(DetailedTermActivity.this, currentTerm.getTermName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailedTermActivity.this, "Term can't be deleted. Delete courses tied to term first.", Toast.LENGTH_LONG).show();
                }
                enterAllTermsScreen(selectedItem.getActionView());
                return true;

            case R.id.detailed_term_refresh_menu_item:
                repository = new Repository(getApplication());
                List<Course> refreshedCourseList = repository.getAllCoursesForTerm(termID);
                RecyclerView recyclerView = findViewById(R.id.detailedTermsRecyclerView);
                final CourseAdapter courseAdapter = new CourseAdapter(this);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                courseAdapter.setCourses(refreshedCourseList);
                return true;
        }
        return super.onOptionsItemSelected(selectedItem);
    }

    public void enterAllTermsScreen(View view) {
        Intent directsToAllTermsScreen = new Intent(DetailedTermActivity.this, AllTermsActivity.class);
        startActivity(directsToAllTermsScreen);
    }

    public void enterAddCourseScreen(View view) {
        Intent directsToAddCourseScreen = new Intent(DetailedTermActivity.this, AddCourseActivity.class);
        directsToAddCourseScreen.putExtra("termID", termID);
        startActivity(directsToAddCourseScreen);
    }
}