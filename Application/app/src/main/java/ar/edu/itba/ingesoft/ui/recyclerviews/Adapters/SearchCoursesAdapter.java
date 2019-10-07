package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

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
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Interfaces.AdapterListeners.OnItemClickListener;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.SearchDiffUtil;

public class SearchCoursesAdapter extends RecyclerView.Adapter<SearchCoursesAdapter.SearchCoursesViewHolder> implements Filterable{

    private List<Course> courseList;
    private List<Course> courseListFiltered;


    public SearchCoursesAdapter(List<Course> courseList){
        this.courseList = courseList;
        courseListFiltered = new ArrayList<>();
    }

    //displaying updates to list contents
    public void update(List<Course> newList){

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SearchDiffUtil(this.courseList, newList));
        Log.v("SearchCAdapter", "new List");

        if(newList!=null) {
            courseList.clear();
            courseList.addAll(newList);
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
    public void onBindViewHolder(@NonNull SearchCoursesViewHolder holder, int position) {
            Log.v("SearchCAdapter", "onBindViewHolder" + courseList.get(position).getName());
            Course aux = courseList.get(position);
            holder.courseNameTextView.setText(aux.getName());
            holder.universityTextView.setText(aux.getCode());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String query = constraint.toString().toLowerCase();

                if(query == null){
                    courseListFiltered = courseList;
                }
                else{
                    List<Course> filteredList = new ArrayList<Course>();
                    for(Course c : courseList){
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
                    update(courseListFiltered);
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
            clickable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, courseNameTextView.getText(), Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
