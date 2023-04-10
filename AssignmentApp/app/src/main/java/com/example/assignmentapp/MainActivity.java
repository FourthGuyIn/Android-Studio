package com.example.assignmentapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.assignmentapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Button logout;
    FirebaseAuth auth;
    FirebaseUser user;
    String[] courses = {"PROG2007", "OSYS1000", "PROG1400", "APPD1001", "PROG2700", "SAAD1001", "COMM2700"};
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavigationView navigationView;
    private TextView mSelectedDateTextView;
    private ViewGroup mContainer;

    private ActionBar actionBar;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.nav_account:
                // Handle My Account click
                return true;
            case R.id.nav_settings:
                // Handle Settings click
                return true;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the ActionBar object
        actionBar = getSupportActionBar();

        // Set up the navigation drawer
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Set up the ActionBar if it exists
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set up the navigation menu item click listener
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation menu item clicks here
            return true;
        });

        Toolbar toolbar = binding.myToolbar;
        setSupportActionBar(toolbar);

        mSelectedDateTextView = findViewById(R.id.selected_date_text_view);
        mContainer = findViewById(R.id.myContainer);

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logOut);
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


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


