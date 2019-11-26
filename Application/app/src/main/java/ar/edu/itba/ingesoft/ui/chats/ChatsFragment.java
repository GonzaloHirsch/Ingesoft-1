package ar.edu.itba.ingesoft.ui.chats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.ingesoft.Authentication.Authenticator;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Interfaces.GeneralListeners.OnObjectEventListener;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.ChatsAdapter;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.ChatsMessagesAdapter;

public class ChatsFragment extends Fragment {

    public static final String TAG = "chats_fragment";

    private ChatsViewModel chatsViewModel;
    private RecyclerView chatsRecyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chats, container, false);

        // Recovering the user email
        String userEmail = new Authenticator().getSignedInUser().getEmail();

        this.chatsRecyclerView = root.findViewById(R.id.chatsRecyclerView);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        this.chatsRecyclerView.setLayoutManager(manager);

        // Setting the email and the data for the adapter
        this.chatsViewModel.setUserEmail(userEmail);
        this.chatsViewModel.recoverData(new OnObjectEventListener<Chat>() {
            @Override
            public void onObjectRetrieved(List<Chat> obj) {
                ChatsAdapter adapter = new ChatsAdapter(obj, userEmail);
                chatsRecyclerView.setAdapter(adapter);
            }
        });

        return root;
    }
}