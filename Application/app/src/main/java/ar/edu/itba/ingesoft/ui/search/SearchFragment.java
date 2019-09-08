package ar.edu.itba.ingesoft.ui.search;

import android.os.Bundle;
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
        searchCoursesAdapter = new SearchCoursesAdapter();
        searchRecyclerView.setAdapter(searchCoursesAdapter);



        return root;
    }
}