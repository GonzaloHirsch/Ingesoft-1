package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.CourseListDiffUtil;


public class CoursesTaughtAdapter extends RecyclerView.Adapter<CoursesTaughtAdapter.CoursesTaughtViewHolder> {

    private List<Course> courses;

    public CoursesTaughtAdapter(){
        this.courses = new ArrayList<>();
    }


    //todo hacer una clase abstracta con este metodo... Ya lo use en SearchCoursesAdapter y el comportamiento es el mismo
    public void update(List<Course> newList){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CourseListDiffUtil(this.courses, newList));
        Log.v("SearchCAdapter", "new List");

        if(newList!=null) {
            courses.clear();
            courses.addAll(newList);
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public CoursesTaughtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_taught, parent, false);
        return new CoursesTaughtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesTaughtViewHolder holder, int position) {

        String s = courses.get(position).getCode() + "-" + courses.get(position).getName();
        holder.courseTextView.setText(s);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CoursesTaughtViewHolder extends RecyclerView.ViewHolder{

        TextView courseTextView;
        ConstraintLayout clickableLayout;

        public CoursesTaughtViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTextView = itemView.findViewById(R.id.itemCourseTaughtTextView);
            clickableLayout = itemView.findViewById(R.id.itemCourseTaughtClickableLayout);
        }
    }
}
