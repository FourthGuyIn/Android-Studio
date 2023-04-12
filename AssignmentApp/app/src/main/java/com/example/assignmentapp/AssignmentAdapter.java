package com.example.assignmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<String> mAssignments;
    private List<View> mAssignmentViews;

    public AssignmentAdapter() {
        mAssignments = new ArrayList<>();
        mAssignmentViews = new ArrayList<>();
    }

    public void setAssignments(List<String> assignments) {
        mAssignments.clear();
        mAssignments.addAll(assignments);
        notifyDataSetChanged();
    }

    public List<String> getAssignments() {
        return mAssignments;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_layout, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            return; // skip binding header layout
        }
        String assignment = mAssignments.get(position - 1); // adjust position to exclude header
        holder.bind(assignment);
        holder.itemView.setOnClickListener(v -> {
            // Handle assignment click
        });
    }

    @Override
    public int getItemCount() {
        return mAssignments.size() + 1; // add 1 to account for header layout
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {

        private TextView mAssignmentTextView;

        public AssignmentViewHolder(View itemView) {
            super(itemView);
            mAssignmentTextView = itemView.findViewById(R.id.assignment_text);
        }

        public void bind(String assignment) {
            mAssignmentTextView.setText(assignment);
        }
    }

    public void addAssignmentView(View assignmentView, String course, String date, String assignmentName, String assignmentDescription) {
        int viewPosition = mAssignmentViews.size();
        int itemPosition = mAssignments.size();

        mAssignmentViews.add(assignmentView);
        mAssignments.add(course + " - " + date + "\n" + assignmentName + "\n" + assignmentDescription);

        notifyItemInserted(viewPosition);
        notifyItemInserted(itemPosition + 1); // add 1 to account for header layout
    }
}




