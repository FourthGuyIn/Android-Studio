package com.example.assignmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> implements AssignmentAdapter2 {

    private List<Assignment> assignments;
    private AssignmentAdapter2 adapter2;

    public AssignmentAdapter(List<Assignment> assignments, AssignmentAdapter2 adapter2) {
        this.assignments = assignments;
        this.adapter2 = adapter2;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        holder.bind(assignments.get(position));
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(assignments, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        assignments.remove(position);
        notifyItemRemoved(position);
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder {

        private TextView courseTextView;
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView dueDateTextView;
        private Button deleteButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTextView = itemView.findViewById(R.id.course_text_view);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            dueDateTextView = itemView.findViewById(R.id.due_date_text_view);
            deleteButton = itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                adapter2.onItemDismiss(position);
            });
        }

        public void bind(Assignment assignment) {
            courseTextView.setText(assignment.getCourse());
            nameTextView.setText(assignment.getName());
            descriptionTextView.setText(assignment.getDescription());
            dueDateTextView.setText(DateFormat.getDateInstance().format(assignment.getDueDate()));
        }
    }
}











