package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.CachedData.CoursesTeachersCache;
import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.CourseListDiffUtil;

public class SearchCoursesAdapter extends RecyclerView.Adapter<SearchCoursesAdapter.SearchCoursesViewHolder> implements Filterable{

    private List<Course> courseList;
    private List<Course> courseListFiltered;
    private List<Course> currentList;
    private SearchCoursesAdapter.OnItemClickListener listener;

    public SearchCoursesAdapter(List<Course> courseList, SearchCoursesAdapter.OnItemClickListener listener){
        this.courseList = courseList;
        this.currentList = new ArrayList<>(courseList);
        courseListFiltered = new ArrayList<>();
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClicked(Course c);
    }

    //displaying updates to list contents
    public void update(List<Course> newList){

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CourseListDiffUtil(this.currentList, newList));
        Log.v("SearchCAdapter", "new List");

        if(newList!=null) {
            currentList.clear();
            courseList.clear();
            currentList.addAll(newList);
            courseList.addAll(newList);
        }
        diffResult.dispatchUpdatesTo(this);

    }

    public void updateFilter(List<Course> newList){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CourseListDiffUtil(this.currentList, newList));
        if(newList!=null){
            currentList.clear();
            currentList.addAll(newList);
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public SearchCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_course, parent, false);
        return new SearchCoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCoursesViewHolder holder, int position)
    {
            Log.v("SearchCAdapter", "onBindViewHolder" + currentList.get(position).getName());
            Course aux = currentList.get(position);

            holder.bind(aux, listener);
            holder.courseNameTextView.setText(aux.getName());
            holder.universityTextView.setText(aux.getCode());
            List<User> usrList = CoursesTeachersCache.getCourseTeachers().get(aux.getCode());
            if(usrList!=null){/*
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i<usrList.size(); i++){
                    sb.append(usrList.get(i));
                    if(i==usrList.size()-1){

                    }
                }
                holder.teachersTextView.setText(sb.toString());*/
                holder.teachersTextView.setText(usrList.size() + " tutors");
            }

    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String query = constraint.toString().toLowerCase();
                courseListFiltered.clear();
                if(query == null){
                    courseListFiltered = courseList;
                }
                else{
                    List<Course> filteredList = new ArrayList<Course>();
                    for(Course c : courseList){
                        if(c.getName().toLowerCase().contains(query) || c.getCode().toLowerCase().contains(query))
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


    public class SearchCoursesViewHolder extends RecyclerView.ViewHolder {

        TextView universityTextView;
        TextView courseNameTextView;
        TextView teachersTextView;
        TextView specialTextView;
        ConstraintLayout clickable;

        public SearchCoursesViewHolder(@NonNull View itemView) {

            super(itemView);

            universityTextView = itemView.findViewById(R.id.universityNameTextView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            teachersTextView = itemView.findViewById(R.id.courseTeachersTextView);
            specialTextView = itemView.findViewById(R.id.courseSpecialTextView);
            clickable = itemView.findViewById(R.id.itemSearchCourseClickable);
        }

        public void bind(Course c, SearchCoursesAdapter.OnItemClickListener listener){
            clickable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(c);
                }
            });
        }
    }
}
