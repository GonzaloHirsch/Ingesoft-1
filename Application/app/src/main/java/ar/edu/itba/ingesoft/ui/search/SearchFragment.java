package ar.edu.itba.ingesoft.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ar.edu.itba.ingesoft.recyclerviews.Adapters.SearchCoursesAdapter;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private RecyclerView searchRecyclerView;
    private SearchCoursesAdapter searchCoursesAdapter;
    private LinearLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);


        searchRecyclerView = root.findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());

        searchRecyclerView.setLayoutManager(layoutManager);
        searchCoursesAdapter = new SearchCoursesAdapter(new ArrayList<>());
        searchRecyclerView.setAdapter(searchCoursesAdapter);

        searchViewModel.getDisplayedData().observe(getActivity(), new Observer<List<Map.Entry<Course, List<User>>>>() {
            @Override
            public void onChanged(List<Map.Entry<Course, List<User>>> entries) {

                //todo remove this
                for(Map.Entry<Course, List<User>> e : entries){
                    Log.v("SearchFragment", e.getKey().getCode());
                }
                searchCoursesAdapter.update(entries);
            }
        });

        return root;
    }
}