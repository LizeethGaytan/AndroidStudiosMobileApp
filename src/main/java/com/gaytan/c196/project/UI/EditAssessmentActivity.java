package com.gaytan.c196.project.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.gaytan.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Database.Repository;
import Entity.Assessment;

public class EditAssessmentActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        editAssessmentName = findViewById(R.id.assessmentNameEdit);
        editAssessmentName.setText(assessmentName);
        assessmentStartDate = getIntent().getStringExtra("assessmentStartDate");
        editAssessmentStartDate = findViewById(R.id.assessmentStartDateEdit);
        editAssessmentStartDate.setText(assessmentStartDate);
        assessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        editAssessmentEndDate = findViewById(R.id.assessmentEndDateEdit);
        editAssessmentEndDate.setText(assessmentEndDate);
        assessmentType = getIntent().getStringExtra("assessmentType");
        editAssessmentType = findViewById(R.id.assessmentTypeEdit);
        editAssessmentType.setText(assessmentType);
        courseID = getIntent().getIntExtra("courseID", -1);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());
    }

    public boolean onCreateOptionsMenu(Menu editAssessmentMenu) {
        getMenuInflater().inflate(R.menu.editassessment_menu, editAssessmentMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.edit_assessment_update_menu_item:
                Assessment assessmentToUpdate;
                assessmentToUpdate = new Assessment(assessmentID, editAssessmentName.getText().toString(),
                                     editAssessmentStartDate.getText().toString(), editAssessmentEndDate.getText().toString(),
                                     editAssessmentType.getText().toString(), courseID);
                repository.update(assessmentToUpdate);
                this.finish();
                return true;

            case R.id.edit_assessment_delete_menu_item:
                Assessment currentAssessment = repository.getSingleAssessment(assessmentID);
                repository.delete(currentAssessment);
                Toast.makeText(EditAssessmentActivity.this, currentAssessment.getAssessmentName() + " was deleted.", Toast.LENGTH_LONG).show();
                this.finish();
                return true;

            case R.id.edit_assessment_notify_menu_item:
                String startDateFromScreen = editAssessmentStartDate.getText().toString();
                String endDateFromScreen = editAssessmentEndDate.getText().toString();
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
                    Intent intent = new Intent(EditAssessmentActivity.this, University100Receiver.class);
                    intent.putExtra("key",editAssessmentName.getText() + " starts today!");
                    PendingIntent sender = PendingIntent.getBroadcast(EditAssessmentActivity.this, MainActivity.alertNum++, intent,0);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }
                if(currentDate.equals(endDateNotify) || currentDate.before(endDateNotify)) {

                    Long trigger = endDateNotify.getTime();
                    Intent intent = new Intent(EditAssessmentActivity.this, University100Receiver.class);
                    intent.putExtra("key",editAssessmentName.getText() + " ends today!");
                    PendingIntent sender = PendingIntent.getBroadcast(EditAssessmentActivity.this, MainActivity.alertNum++, intent,0);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                }

                return true;

            case R.id.edit_assessment_cancel_menu_item:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(selectedItem);
    }
}