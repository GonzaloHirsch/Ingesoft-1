package ar.edu.itba.ingesoft.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.api.Distribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.SearchCoursesAdapter;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private RecyclerView searchRecyclerView;
    private SearchCoursesAdapter searchCoursesAdapter;
    private LinearLayoutManager layoutManager;

    private ProgressBar progressBar;
    private TextView loadingTextView;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        searchRecyclerView = root.findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());

        searchRecyclerView.setLayoutManager(layoutManager);
        searchCoursesAdapter = new SearchCoursesAdapter(new ArrayList<>());
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


        searchViewModel.getDisplayedData().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                searchCoursesAdapter.update(courses);
            }
        });

        return root;
    }


}