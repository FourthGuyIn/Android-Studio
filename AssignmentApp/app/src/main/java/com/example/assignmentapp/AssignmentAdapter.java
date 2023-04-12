package com.example.assignmentapp;

import androidx.recyclerview.widget.RecyclerView;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private List<String> mAssignments;

    public AssignmentAdapter(List<String> assignments) {
        mAssignments = assignments;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_layout, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        String assignment = mAssignments.get(position);
        holder.bind(assignment);
    }

    @Override
    public int getItemCount() {
        return mAssignments.size();
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
}

