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

import com.gaytan.c196.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.Repository;
import Entity.Assessment;

public class AddAssessmentActivity extends AppCompatActivity {
    int assessmentID;
    String assessmentName;
    String assessmentStartDate;
    String assessmentEndDate;
    String assessmentType;
    int courseID;
    EditText editAssessmentName;
    EditText editAssessmentStartDate;
    EditText editAssessmentEndDate;
    EditText editAssessmentType;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        editAssessmentName = findViewById(R.id.assessmentName);
        editAssessmentName.setText(assessmentName);
        assessmentStartDate = getIntent().getStringExtra("assessmentStartDate");
        editAssessmentStartDate = findViewById(R.id.assessmentStartDate);
        editAssessmentStartDate.setText(assessmentStartDate);
        assessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        editAssessmentEndDate = findViewById(R.id.assessmentEndDate);
        editAssessmentEndDate.setText(assessmentEndDate);
        assessmentType = getIntent().getStringExtra("assessmentType");
        editAssessmentType = findViewById(R.id.assessmentType);
        editAssessmentType.setText(assessmentType);
        courseID = getIntent().getIntExtra("courseID", -1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());

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
        editAssessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAssessmentActivity.this, startDate, startDateCalendar
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
        editAssessmentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAssessmentActivity.this, endDate, endDateCalendar
                        .get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH),
                        endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateStartDateLabel() {
        String startDateFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(startDateFormat, Locale.US);
        editAssessmentStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel() {
        String endDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(endDateFormat, Locale.US);
        editAssessmentEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu addAssessmentMenu) {
        getMenuInflater().inflate(R.menu.addassessment_menu, addAssessmentMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch(selectedItem.getItemId()) {
            case R.id.cancel_assessment_menu_item:
                this.finish();
                return true;
            case R.id.save_assessment_menu_item:
                Assessment newAssessment;
                if(assessmentID == -1) {
                    int newAssessmentID;
                    if(repository.getAllAssessments().isEmpty()) {
                        newAssessmentID = 0;
                    } else {
                        newAssessmentID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
                    }
                    newAssessment = new Assessment(newAssessmentID, editAssessmentName.getText().toString(), editAssessmentStartDate.getText().toString(),
                                    editAssessmentEndDate.getText().toString(), editAssessmentType.getText().toString(), courseID);
                    repository.insert(newAssessment);
                    this.finish();
                    return true;
                }
        }
        return super.onOptionsItemSelected(selectedItem);
    }

    public void enterAllTermScreen(View view) {
        Intent directsToAllTermScreen = new Intent(AddAssessmentActivity.this, AllTermsActivity.class);
        startActivity(directsToAllTermScreen);
    }
}
