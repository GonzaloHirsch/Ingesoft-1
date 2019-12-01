package ar.edu.itba.ingesoft.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ar.edu.itba.ingesoft.Firebase.Authenticator;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.coursestaught.CoursesTaughtActivity;
import ar.edu.itba.ingesoft.ui.login.LoginActivity;
import ar.edu.itba.ingesoft.ui.search.SearchViewModel;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private ProgressBar theProgressBar;
    private TextView loadingTextView;

    private ProfileViewModel profileViewModel;
    private SearchViewModel searchViewModel;

    private TextView nameTextView;
    private TextView statusTextView;
    private TextView emailTextView;
    private TextView universityTextView;
    private TextView coursesTextView;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_appbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_profile_logout:
                new Authenticator().signOut(getActivity());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                break;
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
        searchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SP, MODE_PRIVATE);
        String univ = sharedPreferences.getString(MainActivity.UNIV_SP, "");
        this.profileViewModel.setUniversity(univ);

        //Name TextView
        nameTextView = root.findViewById(R.id.profileNameTextView);
        statusTextView = root.findViewById(R.id.profileStatusTextView);

        //Email
        LinearLayout ll = root.findViewById(R.id.profileEmail);
        emailTextView = ll.findViewById(R.id.itemProfileDataDescription);
        ((TextView)ll.findViewById(R.id.itemProfileDataTitle)).setText(R.string.email);
        //University
        ll = root.findViewById(R.id.profileUniversity);
        universityTextView = ll.findViewById(R.id.itemProfileDataDescription);
        ((TextView)ll.findViewById(R.id.itemProfileDataTitle)).setText(R.string.university);
        //Courses
        ll = root.findViewById(R.id.profileCourses);
        coursesTextView = ll.findViewById(R.id.itemProfileDataDescription);
        ((TextView)ll.findViewById(R.id.itemProfileDataTitle)).setText(R.string.courses_taught);
        ll.setOnClickListener(x->{
            FirebaseUser firebaseUser = new Authenticator().getSignedInUser();

            if (firebaseUser.isAnonymous()){
                Toast.makeText(getContext(), "An account is needed", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getContext(), CoursesTaughtActivity.class);
                intent.putExtra(getActivity().getString(R.string.user_parcel), profileViewModel.getCurrentUserLiveData().getValue());
                startActivity(intent);
            }
        });

        //ProgressBar + Loading TextView
        theProgressBar = root.findViewById(R.id.profileProgressBar);
        loadingTextView = root.findViewById(R.id.loadingTextView);

        profileViewModel.getCurrentUserLiveData().observe(getActivity(), new Observer<User>(){
            @Override
            public void onChanged(User u) {
                nameTextView.setText(u.getName());
                emailTextView.setText(u.getMail());
                statusTextView.setText(u.getCourses().size() > 0 ? "Tutor" : "Student");
                universityTextView.setText(u.getUniversidad());
                coursesTextView.setText(u.getCourses().size() + " courses");
            }
        });

        profileViewModel.getLoading().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if(loading){
                    (root.findViewById(R.id.profileLinearLayout)).setVisibility(View.GONE);
                    theProgressBar.setVisibility(View.VISIBLE);
                    loadingTextView.setVisibility(View.VISIBLE);
                }
                else{
                    (root.findViewById(R.id.profileLinearLayout)).setVisibility(View.VISIBLE);
                    theProgressBar.setVisibility(View.GONE);
                    loadingTextView.setVisibility(View.GONE);
                }
            }
        });

        return root;
    }
}