package com.gaytan.c196.project.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gaytan.c196.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import Database.Repository;
import Entity.Term;

public class AddTermActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_add_term);
        termName = getIntent().getStringExtra("termName");
        editTermName = findViewById(R.id.termName);
        editTermName.setText(termName);
        termStartDate = getIntent().getStringExtra("termStartDate");
        editTermStartDate = findViewById(R.id.termstartdate);
        editTermStartDate.setText(termStartDate);
        termEndDate = getIntent().getStringExtra("termEndDate");
        editTermEndDate = findViewById(R.id.termenddate);
        editTermEndDate.setText(termEndDate);
        termID = getIntent().getIntExtra("termID", -1);
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
        editTermStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTermActivity.this, startDate, startDateCalendar
                        .get(Calendar.YEAR), startDateCalendar.get(Calendar.MONTH),
                        startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, monthOfYear);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEndDateLabel();
            }
        };
        editTermEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddTermActivity.this, endDate, endDateCalendar
                        .get(Calendar.YEAR), endDateCalendar.get(Calendar.MONTH),
                        endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateStartDateLabel() {
        String startDateFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(startDateFormat, Locale.US);
        editTermStartDate.setText(sdf.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel() {
        String endDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(endDateFormat, Locale.US);
        editTermEndDate.setText(sdf.format(endDateCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu addTermMenu) {
        getMenuInflater().inflate(R.menu.addterm_menu, addTermMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        // Changed logic
        switch(selectedItem.getItemId()) {
            case R.id.save_term_menu_item:
                Term newTerm;
                if(termID == -1) {
                    int newTermID;
                    if(repository.getAllTerms().isEmpty()) {
                        newTermID = 0;
                    } else {
                        newTermID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
                    }
                    newTerm = new Term(newTermID, editTermName.getText().toString(), editTermStartDate.getText().toString(), editTermEndDate.getText().toString());
                    repository.insert(newTerm);
                    enterAllTermsScreen(selectedItem.getActionView());
                }
                return true;
            case R.id.cancel_menu_item:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(selectedItem);
    }

    public void enterAllTermsScreen(View view) {
        Intent directsToAllTermsScreen = new Intent(AddTermActivity.this, AllTermsActivity.class);
        startActivity(directsToAllTermsScreen);
    }
}