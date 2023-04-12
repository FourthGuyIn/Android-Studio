package com.example.assignmentapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] courses = {"PROG2007", "OSYS1000", "PROG1400", "APPD1001", "PROG2700", "SAAD1001", "COMM2700"};
    private Button addAssignmentButton;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView assignmentRecyclerView;
    private AssignmentAdapter assignmentAdapter;
    AssignmentAdapter adapter = new AssignmentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();
        setUpCreateAssignmentButton();
        setUpNavigationDrawer();
        // Set up toolbar and navigation drawer
        setUpToolbar();

        // Set up Firebase authentication and check if user is logged in
        setUpFirebaseAuth();

        // Get a reference to the RecyclerView
        assignmentRecyclerView = findViewById(R.id.recyclerView);

        // Create an instance of the adapter and set it on the RecyclerView
        assignmentAdapter = new AssignmentAdapter();
        assignmentRecyclerView.setAdapter(assignmentAdapter);

        // Set up a click listener on the "Add Assignment" button
        addAssignmentButton = findViewById(R.id.Create_Assignment);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a dialog to create a new assignment
                showAddAssignmentDialog();
            }
        });
    }

    private void setUpToolbar() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.NavigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_account) {
                // Handle the account action
            } else if (id == R.id.nav_settings) {
                // Handle the settings action
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }


    private void setUpFirebaseAuth() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

    private void showAddAssignmentDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_assignment, null);
        Spinner courseSpinner = dialogView.findViewById(R.id.course_spinner);

        // Add the courses to the spinner
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        if (datePicker == null) {
            // Handle the error
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle(R.string.add_assignment_dialog_title)
                .setPositiveButton(R.string.add_assignment_dialog_add_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedCourse = courseSpinner.getSelectedItem().toString();
                        String selectedDate = String.format("%04d-%02d-%02d",
                                datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                        createNewAssignment(selectedCourse, selectedDate);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }


    private void createNewAssignment(String selectedCourse, String selectedDate) {
        String selectedAssignment = selectedCourse + " - " + selectedDate;

        // Get the current list of assignments
        List<String> assignments = assignmentAdapter.getAssignments();

        // Add the new assignment to the list
        assignments.add(selectedAssignment);

        // Update the RecyclerView with the new list of assignments
        assignmentAdapter.setAssignments(assignments);
        assignmentAdapter.notifyDataSetChanged(); // Notify adapter of the change
    }


    private void setUpNavigationDrawer() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.NavigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpDrawerToggle();
        setUpNavigationView();
    }

    private void setUpDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_account) {
                // Handle the account action
            } else if (id == R.id.nav_settings) {
                // Handle the settings action
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void setUpRecyclerView() {
        assignmentRecyclerView = findViewById(R.id.recyclerView);
        assignmentAdapter = new AssignmentAdapter();
        assignmentRecyclerView.setAdapter(assignmentAdapter);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCreateAssignmentButton() {
        Button myButton = findViewById(R.id.Create_Assignment);
        myButton.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            String selectedDate = DateFormat.getDateInstance().format(calendar.getTime());

            showCourseSelectionDialog(selectedDate);
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    private void showCourseSelectionDialog(String selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a course");
        String[] courses = new String[]{"Math", "English", "Science", "History"};
        builder.setItems(courses, (dialog, which) -> {
            String selectedCourse = courses[which];
            createNewAssignment(selectedCourse, selectedDate);
        });
        builder.show();
    }

    private void showCourseOptions(String selectedDate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select a course");
        builder.setItems(courses, (dialog, which) -> {
            String selectedCourse = courses[which];
            createNewAssignmentView(selectedCourse, selectedDate);
        });
        builder.show();
    }

    private void createNewAssignmentView(String selectedCourse, String selectedDate) {
        View assignmentView = LayoutInflater.from(this).inflate(R.layout.dialog_add_assignment, null);
        EditText assignmentNameEditText = assignmentView.findViewById(R.id.editTextAssignment);
        TextView dateTextView = assignmentView.findViewById(R.id.date_text_view);
        EditText assignmentDescriptionEditText = assignmentView.findViewById(R.id.editTextDescription);

        assignmentNameEditText.setText("");
        dateTextView.setText(selectedDate);
        assignmentDescriptionEditText.setText("");

        // Find the delete button from the parent layout of the assignment view
        Button deleteButton = assignmentView.findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(v -> {
            List<String> assignments = assignmentAdapter.getAssignments();
            assignments.remove(selectedCourse + " - " + selectedDate + "\n" + assignmentNameEditText.getText().toString() + "\n" + assignmentDescriptionEditText.getText().toString());
            assignmentAdapter.setAssignments(assignments);
        });

        assignmentAdapter.addAssignmentView(assignmentView, selectedCourse, selectedDate, assignmentNameEditText.getText().toString(), assignmentDescriptionEditText.getText().toString());
    }
}
