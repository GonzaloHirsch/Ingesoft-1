package ar.edu.itba.ingesoft.recyclerviews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

    private List<Chat> chats = new ArrayList<>();

    private ChatsAdapter() {

    }

    public void update(List<Chat> newList){


    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chats, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {

        Chat chat = chats.get(position);
        holder.titleTextView.setText(chat.getTo());

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView subtitleTextView;
        TextView timestampTextView;
        CircleImageView imageView;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.itemChatTitleTextView);
            subtitleTextView = itemView.findViewById(R.id.itemChatSubtitleTextView);
            timestampTextView = itemView.findViewById(R.id.itemChatTimestapmTextView);
            imageView = itemView.findViewById(R.id.itemChatImage);
        }
    }

}
