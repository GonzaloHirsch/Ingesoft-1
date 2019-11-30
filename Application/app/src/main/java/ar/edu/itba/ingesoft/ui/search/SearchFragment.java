package ar.edu.itba.ingesoft.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.CachedData.CoursesTeachersCache;
import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.courseview.CourseViewActivity;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.SearchCoursesAdapter;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private SearchView searchView=null;
    private SearchView.OnQueryTextListener queryTextListener;

    private RecyclerView searchRecyclerView;
    private SearchCoursesAdapter searchCoursesAdapter;
    private LinearLayoutManager layoutManager;

    private ProgressBar progressBar;
    private TextView loadingTextView;

    private String university;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_appbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {

                    searchCoursesAdapter.getFilter().filter(newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchCoursesAdapter.getFilter().filter(query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //CoursesTeachersCache.refreshCourseTeachers();
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchViewModel =
                ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.SP, MODE_PRIVATE);
        this.university = sharedPreferences.getString(MainActivity.UNIV_SP, "");

        searchRecyclerView = root.findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());

        searchRecyclerView.setLayoutManager(layoutManager);
        searchCoursesAdapter = new SearchCoursesAdapter(new ArrayList<>(), new SearchCoursesAdapter.OnItemClickListener() {

            @Override
            public void onItemClicked(Course c) {
                Intent intent = new Intent(getContext(), CourseViewActivity.class);
                intent.putExtra("SelectedCourse", c);
                startActivity(intent);
            }
        });
        searchRecyclerView.setAdapter(searchCoursesAdapter);


        progressBar = root.findViewById(R.id.searchProgressBar);
        loadingTextView = root.findViewById(R.id.searchLoadingTextView);
        searchViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    searchRecyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    loadingTextView.setVisibility(View.VISIBLE);
                }else{
                    searchRecyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    loadingTextView.setVisibility(View.GONE);
                }
            }
        });


        searchViewModel.getDisplayedData(this.university).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                searchCoursesAdapter.update(courses);
            }
        });

        return root;
    }


}