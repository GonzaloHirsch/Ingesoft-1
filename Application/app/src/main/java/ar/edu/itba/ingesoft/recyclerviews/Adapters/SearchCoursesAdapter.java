package ar.edu.itba.ingesoft.recyclerviews.Adapters;

import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.recyclerviews.diffutil_callbacks.SearchDiffUtil;
import ar.edu.itba.ingesoft.utils.Pair;

public class SearchCoursesAdapter extends RecyclerView.Adapter<SearchCoursesAdapter.SearchCoursesViewHolder> {

    private List<Course> courseList;

    public SearchCoursesAdapter(List<Course> courseList){
        this.courseList = courseList;
    }

    //displaying updates to list contents
    public void update(List<Course> newList){

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SearchDiffUtil(this.courseList, newList));
        Log.v("SearchCAdapter", "new List");

        courseList.clear();
        courseList.addAll(newList);

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
            holder.universityTextView.setText(aux.getCode().substring(aux.getCode().indexOf("-")));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class SearchCoursesViewHolder extends RecyclerView.ViewHolder {

        TextView universityTextView;
        TextView courseNameTextView;
        TextView teachersTextView;
        TextView specialTextView;

        public SearchCoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            universityTextView = itemView.findViewById(R.id.universityNameTextView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            teachersTextView = itemView.findViewById(R.id.courseTeachersTextView);
            specialTextView = itemView.findViewById(R.id.courseSpecialTextView);
        }
    }
}
