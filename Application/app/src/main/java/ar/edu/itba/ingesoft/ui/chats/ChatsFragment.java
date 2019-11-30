package ar.edu.itba.ingesoft.ui.chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.ingesoft.Firebase.Authenticator;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.ChatsAdapter;

public class ChatsFragment extends Fragment {

    public static final String TAG = "chats_fragment";

    private ChatsViewModel chatsViewModel;

    private RecyclerView chatsRecyclerView;
    private ChatsAdapter adapter;

    private ProgressBar progressBar;
    private TextView loadingTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chats, container, false);

        // Recovering the user email
        String userEmail = new Authenticator().getSignedInUser().getEmail();

        progressBar = root.findViewById(R.id.searchProgressBar);
        loadingTextView = root.findViewById(R.id.searchLoadingTextView);
        this.chatsRecyclerView = root.findViewById(R.id.chatsRecyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        this.chatsRecyclerView.setLayoutManager(manager);
        this.adapter = new ChatsAdapter(new ArrayList<>(), userEmail);
        chatsRecyclerView.setAdapter(this.adapter);

        chatsViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    chatsRecyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    loadingTextView.setVisibility(View.VISIBLE);
                }else{
                    chatsRecyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    loadingTextView.setVisibility(View.GONE);
                }
            }
        });

        // Setting the email and the data for the adapter
        this.chatsViewModel.setUserEmail(userEmail);
        this.chatsViewModel.recoverData().observe(getViewLifecycleOwner(), new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                adapter.updateData(chats);
            }
        });

        return root;
    }
}