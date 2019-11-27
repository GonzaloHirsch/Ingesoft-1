package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Message;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.chats.ChatMessagesActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

    private List<Chat> chats = new ArrayList<>();
    private String user;

    public ChatsAdapter(List<Chat> chats, String user) {
        this.chats = chats;
        this.user = user;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.chats.sort(new Comparator<Chat>() {
                @Override
                public int compare(Chat o1, Chat o2) {
                    return o1.getMessages().get(o1.getMessages().size() - 1).getTimestamp().compareTo(o2.getMessages().get(o2.getMessages().size() - 1).getTimestamp());
                }
            });
        }
    }

    public void addToDataSet(List<Chat> chats){
        this.chats.addAll(chats);
        this.notifyItemRangeInserted(0, chats.size());
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        Chat chat = chats.get(position);
        List<Message> messages = chat.getMessages();
        Message lastMessage;
        if (messages.size() > 0){
            lastMessage = messages.get(messages.size() - 1);
            Date date = new Date();
            date.setTime(lastMessage.getTimestamp());
            DateFormat dateFormat = new SimpleDateFormat("mm-dd hh:mm");
            String strDate = dateFormat.format(date);

            holder.subtitleTextView.setText(lastMessage.getMessage());
            holder.timestampTextView.setText(strDate);
        } else {
            holder.subtitleTextView.setText("");
            holder.timestampTextView.setText("");
        }

        holder.titleTextView.setText(chat.getToName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = holder.itemView.getContext();
                Intent intent = new Intent(ctx, ChatMessagesActivity.class);
                intent.putExtra(MainActivity.CHAT_ID_EXTRA, chats.get(holder.getAdapterPosition()).getChatID());
                intent.putExtra(MainActivity.CHAT_RECIPIENT_NAME_EXTRA, chats.get(holder.getAdapterPosition()).getToName());
                intent.putExtra(MainActivity.CHAT_RECIPIENT_EXTRA, chats.get(holder.getAdapterPosition()).getFrom().equals(user) ? chats.get(holder.getAdapterPosition()).getTo() : chats.get(holder.getAdapterPosition()).getFrom());
                ctx.startActivity(intent);
            }
        });
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
