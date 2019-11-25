package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

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
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnCoursesTaughtEventListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnSelectionModeListener;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.CourseListDiffUtil;

public abstract class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CoursesTaughtViewHolder>{

    protected List<Course> courses = new ArrayList<>();
    protected boolean selectionMode;
    protected List<Course> selectedCourses = new ArrayList<>();
    protected OnCoursesTaughtEventListener listener;

    public void update(List<Course> newList){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CourseListDiffUtil(this.courses, newList));
        //Log.v("SearchCAdapter", "new List");


        diffResult.dispatchUpdatesTo(this);
        if(newList!=null) {
            this.courses.clear();
            this.courses.addAll(newList);
        }
    }


    @NonNull
    @Override
    public CoursesTaughtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_taught, parent, false);
        return new CoursesTaughtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesTaughtViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        onBindViewHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesTaughtViewHolder holder, int position) {

        Course c = courses.get(position);
        String s = courses.get(position).getCode() + "-" + courses.get(position).getName();
        holder.courseTextView.setText(s);

        //set background case when (not) selected
        if(c.getSelected())holder.clickableLayout.setBackgroundResource(R.drawable.ripple_turquoise_blue);
        else holder.clickableLayout.setBackgroundResource(R.drawable.ripple_white);

        //todo selection logic. This seems too branchy and slightly code-repeating. Improve
        // if possible (or necessary)
        holder.clickableLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(selectionMode){
                    c.toggleSelected();
                    notifyItemChanged(holder.getPosition());
                }
                else{
                    selectionMode = true;
                    ((OnSelectionModeListener)listener).onSelectionModeEnabled();
                    c.toggleSelected();
                    notifyItemChanged(holder.getPosition());
                }
                if(c.getSelected())selectedCourses.add(c);
                else{
                    selectedCourses.remove(c);
                    if(selectedCourses.size()==0){
                        selectionMode = false;
                        ((OnSelectionModeListener)listener).onSelectionModeDisabled();
                    }
                }
                return true;
            }
        });
        holder.clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionMode) {
                    c.toggleSelected();
                    notifyItemChanged(holder.getPosition());
                    if(c.getSelected()) selectedCourses.add(c);
                    else{
                        selectedCourses.remove(c);
                        if(selectedCourses.size()==0) {
                            selectionMode = false;
                            ((OnSelectionModeListener)listener).onSelectionModeDisabled();
                        }
                    }
                }
            }
        });
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

    public void clearSelection(){
        this.selectionMode = false;
        for(Course c : courses){
            c.unsetSelected();
        }
    }

}
