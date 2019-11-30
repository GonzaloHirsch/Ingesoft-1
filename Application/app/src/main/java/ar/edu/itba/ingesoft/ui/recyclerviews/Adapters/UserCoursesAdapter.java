package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.itba.ingesoft.CachedData.CoursesTeachersCache;
import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.CourseListDiffUtil;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.UserDiffutil;

public class UserCoursesAdapter extends RecyclerView.Adapter<UserCoursesAdapter.UserCoursesViewHolder> {

    private List<Course> courseList = new ArrayList<>();

    public UserCoursesAdapter(List<String> courseIds){
        //todo ver la fucking race condition
        for(Course c : CoursesTeachersCache.getCoursesList()){
            if(courseIds.contains(c.getCode())){
                courseList.add(c);
            }
        }
    }

    public void update(List<Course> newList){

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CourseListDiffUtil(this.courseList, newList));
        Log.v("UsersAdapter", "new List");

        if(newList!=null) {
            courseList.clear();
            courseList.addAll(newList);
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public UserCoursesAdapter.UserCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_course_taught, parent, false);
        return new UserCoursesAdapter.UserCoursesViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull UserCoursesAdapter.UserCoursesViewHolder holder, int position) {

        Course c = courseList.get(position);
        holder.title.setText(c.getName());
        holder.subtitle.setText(c.getCode());
        //todo holder.btn.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class UserCoursesViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView subtitle;

        UserCoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemUserCourseTaughtTitle);
            subtitle = itemView.findViewById(R.id.itemUserCourseTaughtSubtitle);
        }


    }
}
