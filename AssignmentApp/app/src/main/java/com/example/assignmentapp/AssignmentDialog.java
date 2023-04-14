package com.example.assignmentapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class AssignmentDialog {

    private EditText mCourseEditText;
    private ArrayList<String> coursesList = new ArrayList<>(Arrays.asList("PROG2007", "OSYS1000", "PROG1400", "APPD1001", "PROG2700", "SAAD1001", "COMM2700"));

    public void showAddAssignmentDialog(Activity activity, AssignmentAdapter mAssignmentAdapter) {
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_assignment, null);

        Spinner courseSpinner = Objects.requireNonNull(dialogView.findViewById(R.id.course_spinner));
        EditText assignmentNameEditText = Objects.requireNonNull(dialogView.findViewById(R.id.editTextAssignment));
        EditText assignmentDescriptionEditText = Objects.requireNonNull(dialogView.findViewById(R.id.editTextDescription));
        DatePicker datePicker = Objects.requireNonNull(dialogView.findViewById(R.id.date_picker));
        TextView selectedDateTextView = Objects.requireNonNull(dialogView.findViewById(R.id.date_text_view));
        EditText newCourseEditText = Objects.requireNonNull(dialogView.findViewById(R.id.course_edit_text));

        initializeCourseSpinner(activity, courseSpinner);

        selectedDateTextView.setText("Selected Date: " + String.format("%04d-%02d-%02d",
                datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView)
                .setTitle(R.string.add_assignment_dialog_title)
                .setPositiveButton(R.string.add_assignment_dialog_add_button_text, null)
                .setNegativeButton(R.string.add_assignment_dialog_cancel_button_text, null);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button addButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        addButton.setOnClickListener(v -> {
            String selectedCourse = courseSpinner.getSelectedItem().toString();
            @SuppressLint("DefaultLocale") String selectedDateString = String.format("%04d-%02d-%02d",
                    datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
            String assignmentName = assignmentNameEditText.getText().toString().trim();
            String assignmentDescription = assignmentDescriptionEditText.getText().toString().trim();
            String newCourse = newCourseEditText.getText().toString().trim();

            if (!newCourse.isEmpty()) {
                coursesList.add(newCourse);
                initializeCourseSpinner(activity, courseSpinner);
                courseSpinner.setSelection(coursesList.indexOf(newCourse));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate selectedDate;
            try {
                selectedDate = LocalDate.parse(selectedDateString, formatter);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                return;
            }

            // Convert LocalDate to Date object
            Date selectedDateAsDate = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Create a new assignment object and add it to the adapter
            Assignment assignment = new Assignment(selectedCourse, selectedDateAsDate, assignmentName, assignmentDescription);
            mAssignmentAdapter.addAssignment(assignment);

            // Dismiss the dialog
            dialog.dismiss();
        });

        datePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> selectedDateTextView.setText("Selected Date: " + String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)));
    }

    private void initializeCourseSpinner(Activity activity, Spinner courseSpinner) {
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, coursesList);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);
    }

}

