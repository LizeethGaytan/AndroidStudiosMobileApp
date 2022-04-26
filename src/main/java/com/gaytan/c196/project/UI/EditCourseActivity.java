package com.gaytan.c196.project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gaytan.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Database.Repository;
import Entity.Assessment;
import Entity.Course;
import Entity.Term;

public class EditCourseActivity extends AppCompatActivity {
    int courseID;
    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseStatus;
    String courseInstructorName;
    String courseInstructorPhone;
    String courseInstructorEmail;
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
        setContentView(R.layout.activity_edit_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        courseInstructorName = getIntent().getStringExtra("courseInstructorName");
        editInstructorName = findViewById(R.id.courseInstructorName);
        editInstructorName.setText(courseInstructorName);
        courseInstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
        editInstructorPhoneNumber = findViewById(R.id.courseInstructorPhoneNumber);
        editInstructorPhoneNumber.setText(courseInstructorPhone);
        courseInstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
        editInstructorEmail = findViewById(R.id.courseInstructorEmail);
        editInstructorEmail.setText(courseInstructorEmail);
        termID = getIntent().getIntExtra("termID", -1);
        note = getIntent().getStringExtra("note");
        editNote = findViewById(R.id.courseNote);
        editNote.setText(note);
        RecyclerView recyclerView = findViewById(R.id.editCourseRecyclerView);
        repository = new Repository(getApplication());
        List<Assessment> assessmentsList = repository.getAllAssessmentsForCourse(courseID);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessmentsList);

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
                new DatePickerDialog(EditCourseActivity.this, startDate, startDateCalendar
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

                new DatePickerDialog(EditCourseActivity.this, endDate, endDateCalendar
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

    public boolean onCreateOptionsMenu(Menu editCourseMenu) {
        getMenuInflater().inflate(R.menu.editcourse_menu, editCourseMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.edit_course_update_menu_item:
                Course courseToUpdate;
                courseToUpdate = new Course(courseID, editCourseName.getText().toString(), editCourseStartDate.getText().toString(), editCourseEndDate.getText().toString(),
                                            editCourseStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhoneNumber.getText().toString(),
                                            editInstructorEmail.getText().toString(), termID, editNote.getText().toString());
                repository.update(courseToUpdate);
                this.finish();
                return true;

            case R.id.edit_course_delete_menu_item:
                Course currentCourse = repository.getSingleCourse(courseID);
                List<Assessment> courseAssessmentList = repository.getAllAssessmentsForCourse(courseID);
                if(courseAssessmentList.size() == 0) {
                    repository.delete(currentCourse);
                    Toast.makeText(EditCourseActivity.this, currentCourse.getCourseName() + " has no assessments and was deleted.", Toast.LENGTH_LONG).show();
                } else {
                    repository.deleteAssessmentsForCourse(courseID);
                    repository.delete(currentCourse);
                    Toast.makeText(EditCourseActivity.this, currentCourse.getCourseName() + " and it's assessments were deleted.", Toast.LENGTH_LONG).show();
                }
                this.finish();
                return true;

            case R.id.edit_course_refresh_menu_item:
                repository = new Repository(getApplication());
                List<Assessment> refreshedAssessmentList = repository.getAllAssessmentsForCourse(courseID);
                RecyclerView recyclerView = findViewById(R.id.editCourseRecyclerView);
                final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
                recyclerView.setAdapter(assessmentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                assessmentAdapter.setAssessments(refreshedAssessmentList);
                return true;

            case R.id.edit_course_share_note_menu_item:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE,"Course Note");
                sendIntent.setType("text/plain");
                Intent shareIntentChooser = Intent.createChooser(sendIntent,null);
                startActivity(shareIntentChooser);
                return true;

            case R.id.edit_course_notify_menu_item:
                String startDateFromScreen = editCourseStartDate.getText().toString();
                String endDateFromScreen = editCourseEndDate.getText().toString();
                String dateFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                Date currentDate = new Date();
                try {
                    currentDate = sdf.parse(sdf.format(currentDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date startDateNotify = null;
                try {
                    startDateNotify = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date endDateNotify = null;
                try {
                    endDateNotify = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(currentDate.equals(startDateNotify) || currentDate.before(startDateNotify)){

                    Long trigger = startDateNotify.getTime();
                    Intent intent = new Intent(EditCourseActivity.this, University100Receiver.class);
                    intent.putExtra("key",editCourseName.getText() + " starts today!");
                    PendingIntent sender = PendingIntent.getBroadcast(EditCourseActivity.this, MainActivity.alertNum++, intent,0);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }
                if(currentDate.equals(endDateNotify) || currentDate.before(endDateNotify)) {

                    Long trigger = endDateNotify.getTime();
                    Intent intent = new Intent(EditCourseActivity.this, University100Receiver.class);
                    intent.putExtra("key",editCourseName.getText() + " ends today!");
                    PendingIntent sender = PendingIntent.getBroadcast(EditCourseActivity.this, MainActivity.alertNum++,intent,0);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }
                return true;

            case R.id.edit_course_cancel_course_menu_item:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(selectedItem);
    }

    public void enterAddAssessmentScreen(View view) {
        Intent directsToAddAssessmentScreen = new Intent(EditCourseActivity.this, AddAssessmentActivity.class);
        directsToAddAssessmentScreen.putExtra("courseID", courseID);
        startActivity(directsToAddAssessmentScreen);
    }
}