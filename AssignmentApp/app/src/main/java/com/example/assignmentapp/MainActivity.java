package com.example.assignmentapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AssignmentAdapter mAssignmentAdapter;
    private RecyclerView mAssignmentRecyclerView;
    private AssignmentDialog mAssignmentDialog;
    private List<String> mCourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the assignment dialog
        mAssignmentDialog = new AssignmentDialog();

        // Initialize the list of courses
        mCourses.add("PROG2007");
        mCourses.add("OSYS1000");
        mCourses.add("PROG1400");
        mCourses.add("APPD1001");
        mCourses.add("PROG2700");
        mCourses.add("SAAD1001");
        mCourses.add("COMM2700");

        // Find and set the click listener for the add assignment button
        Button addAssignmentButton = findViewById(R.id.Create_Assignment);
        addAssignmentButton.setOnClickListener(v -> mAssignmentDialog.showAddAssignmentDialog(MainActivity.this, mAssignmentAdapter));

        ToolbarHelper.setUpToolbar(this);

        // Set up the views
        setUpViews(mAssignmentDialog);
    }

    private void setUpViews(AssignmentDialog assignmentDialog) {
        List<Assignment> assignments = new ArrayList<>();
        mAssignmentAdapter = new AssignmentAdapter(assignments, new AssignmentAdapter2() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                mAssignmentAdapter.onItemMove(fromPosition, toPosition);
            }

            @Override
            public void onItemDismiss(int position) {
                mAssignmentAdapter.onItemDismiss(position);
            }
        });

        mAssignmentRecyclerView = findViewById(R.id.assignment_recycler_view);
        mAssignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAssignmentRecyclerView.setAdapter(mAssignmentAdapter);

        // Add the touch helper to enable drag and drop reordering of items
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new AssignmentTouchHelperCallback(mAssignmentAdapter));
        itemTouchHelper.attachToRecyclerView(mAssignmentRecyclerView);
    }
}








