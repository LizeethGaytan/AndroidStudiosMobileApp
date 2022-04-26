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

import Entity.Course;
import Entity.Term;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseListItemTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Course currentCourse = mCourses.get(position);
                    // Send the course object from the current area to another screen
                    Intent intent = new Intent(context, EditCourseActivity.class);
                    intent.putExtra("courseID", currentCourse.getCourseID());
                    intent.putExtra("courseName", currentCourse.getCourseName());
                    intent.putExtra("courseStartDate", currentCourse.getCourseStartDate());
                    intent.putExtra("courseEndDate", currentCourse.getCourseEndDate());
                    intent.putExtra("courseStatus", currentCourse.getCourseStatus());
                    intent.putExtra("courseInstructorName", currentCourse.getCourseInstructorName());
                    intent.putExtra("courseInstructorPhone", currentCourse.getCourseInstructorPhone());
                    intent.putExtra("courseInstructorEmail", currentCourse.getCourseInstructorEmail());
                    intent.putExtra("termID", currentCourse.getTermID());
                    intent.putExtra("note", currentCourse.getNote());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    // Adapter Constructor
    public CourseAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context= context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        // Places objects in textview
        if(mCourses != null){
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        }
        else{
            holder.courseItemView.setText("No term name");
        }
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses != null){
            return mCourses.size();
        }
        else return 0;
    }

}
