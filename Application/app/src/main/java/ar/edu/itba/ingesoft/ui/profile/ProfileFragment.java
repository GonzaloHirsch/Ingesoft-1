package ar.edu.itba.ingesoft.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.recyclerviews.Adapters.ProfileDataAdapter;
import ar.edu.itba.ingesoft.R;

public class ProfileFragment extends Fragment {

    private RecyclerView profileDataRecyclerView;
    private ProfileDataAdapter profileDataAdapter;
    private LinearLayoutManager profileDataLayoutManager;
    private ProgressBar theProgressBar;


    private ProfileViewModel profileViewModel;

    private TextView nameTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //Name TextView
        nameTextView = root.findViewById(R.id.profileNameTextView);


        //The RecyclerView
        profileDataRecyclerView = root.findViewById(R.id.profileDataRecyclerView);
        profileDataLayoutManager = new LinearLayoutManager(getContext());
        profileDataRecyclerView.setLayoutManager(profileDataLayoutManager);
        profileDataAdapter = new ProfileDataAdapter();
        profileDataRecyclerView.setAdapter(profileDataAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), profileDataLayoutManager.getOrientation());
        profileDataRecyclerView.addItemDecoration(dividerItemDecoration);


        //The Observer for user
        profileViewModel.getCurrentUserLiveData().observe(getActivity(), user -> {
            profileDataAdapter.setNewUserData(user);
            nameTextView.setText(user.getName() + " " + user.getSurname());
        });

        //The ProgressBar

        theProgressBar = root.findViewById(R.id.profileProgressBar);
        profileViewModel.getLoading().observe(getActivity(), loading -> {
            if(loading){
                theProgressBar.setVisibility(View.VISIBLE);
            }
            else
                theProgressBar.setVisibility(View.GONE);
        });

        return root;
    }
}