package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



}

public class CreateAssignment extends AppCompatActivity {

    private TextView mSelectedDateTextView;
    private ViewGroup mContainer;

    String[] courses = {"PROG2007", "OSYS1000", "PROG1400", "APPD1001", "PROG2700", "SAAD1001","COMM2700"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectedDateTextView = findViewById(R.id.selected_date_text_view);
        mContainer = findViewById(R.id.myContainer);

        Button myButton = findViewById(R.id.Create_Assignment);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new DatePickerDialog to allow the user to select a date
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAssignment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%d/%d/%d", month + 1, dayOfMonth, year);

                        // Show an AlertDialog with course options
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAssignment.this);
                        builder.setTitle("Select a course");

                        builder.setItems(courses, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Save the selected course to a variable or use it to create a new assignment
                                String selectedCourse = courses[which];

                                // Add the selected date and course to the container
                                String selectedAssignment = selectedCourse + " - " + selectedDate;
                                TextView selectedAssignmentTextView = new TextView(getApplicationContext());
                                selectedAssignmentTextView.setText(selectedAssignment);
                                mContainer.addView(selectedAssignmentTextView);
                            }
                        });

                        builder.show();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }
}
