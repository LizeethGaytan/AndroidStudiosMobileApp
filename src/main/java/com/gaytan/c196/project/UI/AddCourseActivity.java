package com.gaytan.c196.project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
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
import Entity.Course;


public class AddCourseActivity extends AppCompatActivity {
    int courseID;
    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseStatus;
    String instructorName;
    String instructorPhoneNumber;
    String instructorEmail;
    int termID;
    String note;
    EditText editCourseName;
    EditText editCourseStartDate;
    EditText editCourseEndDate;
    EditText editCourseStatus;
    EditText editInstructorName;
    EditText editInstructorPhoneNumber;
    EditText editInstructorEmail;
    EditText editNote;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseID = getIntent().getIntExtra("courseID", -1);
        courseName = getIntent().getStringExtra("courseName");
        editCourseName = findViewById(R.id.courseName);
        editCourseName.setText(courseName);
        courseStartDate = getIntent().getStringExtra("courseStartDate");
        editCourseStartDate = findViewById(R.id.courseStartDate);
        editCourseStartDate.setText(courseStartDate);
        courseEndDate = getIntent().getStringExtra("courseEndDate");
        editCourseEndDate = findViewById(R.id.courseEndDate);
        editCourseEndDate.setText(courseEndDate);
        courseStatus = getIntent().getStringExtra("courseStatus");
        editCourseStatus = findViewById(R.id.courseStatus);
        editCourseStatus.setText(courseStatus);
        instructorName = getIntent().getStringExtra("instructorName");
        editInstructorName = findViewById(R.id.courseInstructorName);
        editInstructorName.setText(instructorName);
        instructorPhoneNumber = getIntent().getStringExtra(instructorPhoneNumber);
        editInstructorPhoneNumber = findViewById(R.id.courseInstructorPhoneNumber);
        editInstructorPhoneNumber.setText(instructorPhoneNumber);
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        editInstructorEmail = findViewById(R.id.courseInstructorEmail);
        editInstructorEmail.setText(instructorEmail);
        termID = getIntent().getIntExtra("termID", -1);
        note = getIntent().getStringExtra("note");
        editNote = findViewById(R.id.courseNote);
        editNote.setText(note);
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
        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddCourseActivity.this, startDate, startDateCalendar
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
        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddCourseActivity.this, endDate, endDateCalendar
                        .get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH),
                        endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateStartDateLabel() {
        String startDateFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(startDateFormat, Locale.US);
        editCourseStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel() {
        String endDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(endDateFormat, Locale.US);
        editCourseEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu addCourseMenu) {
        getMenuInflater().inflate(R.menu.addcourse_menu, addCourseMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch(selectedItem.getItemId()) {
            case R.id.cancel_course_menu_item:
                this.finish();
                return true;
            case R.id.save_course_menu_item:
                Course newCourse;
                if(courseID == -1) {
                    int newCourseID;
                    if(repository.getAllCourses().isEmpty()) {
                        newCourseID = 0;
                    } else {
                        newCourseID = repository.getAllCourses().get(repository.getAllCourses().size() -1).getCourseID() +1;
                    }
                    newCourse = new Course(newCourseID, editCourseName.getText().toString(), editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(),
                                            editCourseStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhoneNumber.getText().toString(),
                                            editInstructorEmail.getText().toString(), termID, editNote.getText().toString());
                    repository.insert(newCourse);
                    this.finish();
                    return true;
                }
        }
        return super.onOptionsItemSelected(selectedItem);
    }
}