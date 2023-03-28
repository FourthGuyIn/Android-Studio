package com.example.assignmentapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.assignmentapp.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private TextView mSelectedDateTextView;
    private ViewGroup mContainer;

    String[] courses = {"PROG2007", "OSYS1000", "PROG1400", "APPD1001", "PROG2700", "SAAD1001","COMM2700"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        mSelectedDateTextView = findViewById(R.id.selected_date_text_view);
        mContainer = findViewById(R.id.myContainer);

        Button myButton = findViewById(R.id.Create_Assignment);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new DatePickerDialog to allow the user to select a date
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%d/%d/%d", month + 1, dayOfMonth, year);

                        // Show an AlertDialog with course options
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Select a course");

                        builder.setItems(courses, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Save the selected course to a variable or use it to create a new assignment
                                String selectedCourse = courses[which];

                                // Add the selected date and course to the container
                                final String[] selectedAssignment = {selectedCourse + " - " + selectedDate};
                                TextView selectedAssignmentTextView = new TextView(getApplicationContext());
                                selectedAssignmentTextView.setText(selectedAssignment[0]);
                                mContainer.addView(selectedAssignmentTextView);

                                // Show a text prompt asking for the assignment details
                                AlertDialog.Builder detailsBuilder = new AlertDialog.Builder(MainActivity.this);
                                detailsBuilder.setTitle("Add Assignment Details");
                                detailsBuilder.setMessage("Enter any additional details about the assignment:");

                                // Add an EditText view for the user to input the assignment details
                                final EditText input = new EditText(MainActivity.this);
                                detailsBuilder.setView(input);

                                detailsBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Get the assignment details from the EditText view
                                        String assignmentDetails = input.getText().toString();

                                        // Append the assignment details to the selected assignment string
                                        selectedAssignment[0] += "\n" + assignmentDetails;
                                        selectedAssignmentTextView.setText(selectedAssignment[0]);
                                    }
                                });

                                detailsBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                detailsBuilder.show();
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
