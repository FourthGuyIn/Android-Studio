package com.example.assignmentapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Calendar;
import com.google.android.material.navigation.NavigationView;
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    Button logout;
    FirebaseAuth auth;
    FirebaseUser user;
    String[] courses = {"PROG2007", "OSYS1000", "PROG1400", "APPD1001", "PROG2700", "SAAD1001", "COMM2700"};
    private ViewGroup mContainer;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.NavigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
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
            }
        });

        findViewById(R.id.selected_date_text_view);
        mContainer = findViewById(R.id.myContainer);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        Button myButton = findViewById(R.id.Create_Assignment);
        myButton.setOnClickListener(v -> {
            // Create a new DatePickerDialog to allow the user to select a date
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, (view, year, month, dayOfMonth) -> {
                String selectedDate = String.format("%d/%d/%d", month + 1, dayOfMonth, year);

                // Show an AlertDialog with course options
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select a course");

                builder.setItems(courses, (dialog, which) -> {
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

                    detailsBuilder.setPositiveButton("OK", (dialog1, which1) -> {
                        // Get the assignment details from the EditText view
                        String assignmentDetails = input.getText().toString();

                        // Append the assignment details to the selected assignment string
                        selectedAssignment[0] += " - " + assignmentDetails;

                        // Update the selected assignment TextView with the new details
                        selectedAssignmentTextView.setText(selectedAssignment[0]);
                    });

                    detailsBuilder.setNegativeButton("Cancel", (dialog12, which12) -> {
                        // Do nothing
                    });

                    detailsBuilder.show();
                });

                builder.show();
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle the ActionBarDrawerToggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


