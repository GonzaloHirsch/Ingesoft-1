package ar.edu.itba.ingesoft.ui.chats;

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

import ar.edu.itba.ingesoft.R;

public class ChatsFragment extends Fragment {

    private ChatsViewModel chatsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatsViewModel =
                ViewModelProviders.of(this).get(ChatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chats, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        chatsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}