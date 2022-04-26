package com.gaytan.c196.project.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaytan.c196.R;

import java.util.List;

import Entity.Assessment;
import Entity.Course;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentListItemTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Assessment currentAssessment = mAssessments.get(position);
                    Intent intent = new Intent(context, EditAssessmentActivity.class);
                    intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                    intent.putExtra("assessmentName", currentAssessment.getAssessmentName());
                    intent.putExtra("assessmentStartDate", currentAssessment.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", currentAssessment.getAssessmentEndDate());
                    intent.putExtra("courseID", currentAssessment.getCourseID());
                    intent.putExtra("assessmentType", currentAssessment.getAssessmentType());
                    context.startActivity(intent);
                }
            });

        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    // Adapter Constructor
    public AssessmentAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context= context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        // Places objects in textview
        if(mAssessments != null){
            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentName();
            holder.assessmentItemView.setText(name);
        }
        else{
            holder.assessmentItemView.setText("No assessment name");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        }
        else return 0;
    }

}
