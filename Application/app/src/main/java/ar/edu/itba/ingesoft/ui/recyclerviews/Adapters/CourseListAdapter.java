package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public abstract class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CoursesTaughtViewHolder> implements Filterable {

    protected List<Course> courses = new ArrayList<>();
    protected boolean selectionMode;
    protected List<Course> selectedCourses = new ArrayList<>();
    protected OnCoursesTaughtEventListener listener;

    private List<Course> courseListFiltered;
    private List<Course> currentList;

    public CourseListAdapter(){
        this.currentList = new ArrayList<>();
        courseListFiltered = new ArrayList<>();
    }

    public void updateFilter(List<Course> newList){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CourseListDiffUtil(this.currentList, newList));
        if(newList!=null){
            currentList.clear();
            currentList.addAll(newList);
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String query = constraint.toString().toLowerCase();
                courseListFiltered.clear();
                if(query == null){
                    courseListFiltered = courses;
                }
                else{
                    List<Course> filteredList = new ArrayList<Course>();
                    for(Course c : courses){
                        if(c.getName().toLowerCase().contains(query))
                            filteredList.add(c);
                    }
                    courseListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = courseListFiltered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                courseListFiltered = (List<Course>) results.values;
                updateFilter(courseListFiltered);
            }
        };
    }

    public void update(List<Course> newList){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CourseListDiffUtil(this.courses, newList));
        //Log.v("SearchCAdapter", "new List");


        diffResult.dispatchUpdatesTo(this);
        if(newList!=null) {
            this.currentList.clear();
            this.courses.clear();
            this.courses.addAll(newList);
            this.currentList.addAll(newList);
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

        Course c = currentList.get(position);
        String s = currentList.get(position).getCode() + "-" + currentList.get(position).getName();
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
        return currentList.size();
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
