package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ar.edu.itba.ingesoft.Classes.Message;
import ar.edu.itba.ingesoft.R;

public class ChatsMessagesAdapter extends RecyclerView.Adapter<ChatsMessagesAdapter.MessageViewHolder> {
    private List<Message> messages;
    private String userEmail;

    private final int MESSAGE_SENT = 1;
    private final int MESSAGE_RECEIVED = 2;

    /**
     * Constructor for the message adapter
     * @param messages is the list of messages
     * @param userEmail is the user using the app
     */
    public ChatsMessagesAdapter(List<Message> messages, String userEmail) {
        this.messages = messages;
        this.userEmail = userEmail;

        // Check for the build version in order to be able to sort by timestamp
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.messages.sort(new Comparator<Message>() {
                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getTimestamp().compareTo(o2.getTimestamp());
                }
            });
        }
    }

    /**
     * Class for the message viewholder
     * Retrieves ui components for binding
     */
    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView body;
        public TextView time;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.text_message_body);
            time = itemView.findViewById(R.id.text_message_time);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Check the type of message sent to inflate the correct layout
        if (viewType == MESSAGE_SENT){
            View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_sent, viewGroup, false);
            return new MessageViewHolder(layoutView);
        } else {
            View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_received, viewGroup, false);
            return new MessageViewHolder(layoutView);
        }
    }

    /**
     * Method to notify the adapter that a new message was added.
     * Should be called when logged user sends new message
     * @param message
     */
    public void addMessage(Message message){
        // Add the message to the list
        messages.add(message);
        // Notify the item was added
        this.notifyItemInserted(messages.size() - 1);
    }

    public void setData(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    /**
     * Method to notify the adapter that there are new messages from the database
     * @param newMessages
     */
    public void messagesChanged(List<Message> newMessages){
        // Store the previous length for the listener
        int prevLength = this.messages.size();

        if (prevLength < newMessages.size()){
            // Get a sub list of the new messages list
            List<Message> newMessagesList = newMessages.subList(prevLength, newMessages.size());
            // Add all the messages to the list
            messages.addAll(newMessagesList);
            // Notify which items have changed
            this.notifyItemRangeInserted(prevLength, newMessages.size());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);

        if (message.getSentBy().equals(this.userEmail)) {
            // If the current user is the sender of the message
            return MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return MESSAGE_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        if (messages != null && i < messages.size()){
            Message message = messages.get(i);

            DateFormat df = new SimpleDateFormat("dd/MM HH:mm", Locale.FRANCE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(message.getTimestamp());
            String date = df.format(calendar.getTime());

            messageViewHolder.body.setText(message.getMessage());
            messageViewHolder.time.setText(date);
        }
    }

    @Override
    public int getItemCount()  {
        return messages != null ? messages.size() : 0;
    }
}
