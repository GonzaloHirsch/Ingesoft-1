package ar.edu.itba.ingesoft.ui.chats;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Message;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Firebase.DatabaseConnection;
import ar.edu.itba.ingesoft.Firebase.MessagingConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnChatEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;

public class ChatMessagesViewModel extends ViewModel {

    public static final String TAG = "CHAT MESSAGE VIEW MODEL";
    private Chat chatObj = null;
    private String chatID = null;

    private MessagingConnection mc = new MessagingConnection();

    public ChatMessagesViewModel(){
    }

    /**
     * Metodo para settear el id del chat que se esta guardando en el viewmodel.
     * @param chatID of chat
     */
    public void setChatID(String chatID){
        this.chatID = chatID;
        new DatabaseConnection().GetChat(chatID, new OnChatEventListener() {
            @Override
            public void onChatRetrieved(Chat chat) {
                if (chat != null)
                    chatObj = chat;
            }

            @Override
            public void onChatChanged(Chat chat) {
                throw new RuntimeException("Not Implemented");
            }
        });
    }

    /**
     * Method to notify the view model about the new message the logged user sent
     * @param message
     */
    public void addMessage(Message message, OnChatEventListener eventListener){
        // Store the new message in the object
        this.chatObj.addMessage(message);

        mc.sendMessage(this.chatID, message.getSentBy(), message.getMessage());

        // Update the database with the new message
        new DatabaseConnection().UpdateChat(this.chatObj, new OnChatEventListener() {
            @Override
            public void onChatRetrieved(Chat chat) {
                throw new RuntimeException("Not Implemented");
            }

            @Override
            public void onChatChanged(Chat chat) {
                chatObj = chat;
                eventListener.onChatChanged(chat);
            }
        });
    }

    /**
     * Method to set up the chat change listener.
     * The Fragment should send another listener to make a chain of listeners.
     * @param chatID of the chat
     * @param eventListener for the event of a chat change
     */
    public void setUpChatChangeListener(String chatID, OnChatEventListener eventListener){
        new DatabaseConnection().SetUpChatListener(chatID, new OnChatEventListener() {
            @Override
            public void onChatRetrieved(Chat chat) {
                throw new RuntimeException("Not Implemented");
            }

            @Override
            public void onChatChanged(Chat chat) {
                eventListener.onChatChanged(chat);
            }
        });
    }

    public List<Message> retrieveMessages(){
        List<Message> messages = new LinkedList<>();

        if (chatID != null && chatObj != null){
            messages = chatObj.getMessages();
        } else {
            if (chatID == null)
                Log.e(TAG, "Null chat ID");
            if (chatObj == null)
                Log.e(TAG, "Null chat object");
        }

        return messages;
    }

    public int getChatMessageCount(){
        int count = 0;

        if (chatID != null && chatObj != null){
            count = chatObj.getMessages().size();
        } else {
            if (chatID == null)
                Log.e(TAG, "Null chat ID");
            if (chatObj == null)
                Log.e(TAG, "Null chat object");
        }

        return count;
    }

    public String createChat(String userFrom, String userTo, String userFromName, String userToName){
        this.chatObj = new Chat(userFrom, userTo, userFromName, userToName);
        this.chatID = this.chatObj.getChatID();

        new DatabaseConnection().InsertChat(this.chatObj);
        Log.v(TAG, "Created chat");

        new DatabaseConnection().GetUser(userTo, new OnUserEventListener() {
            @Override
            public void onUserRetrieved(User user) {
                user.addChat(chatID);
                new DatabaseConnection().UpdateUser(user);
            }

            @Override
            public void onUsersRetrieved(List<User> users) {
                throw new RuntimeException("Not Implemented");
            }

            @Override
            public void onTeachersRetrieved(List<User> teachers) {
                throw new RuntimeException("Not Implemented");
            }
        });
        Log.v(TAG, "Added chat to " + userTo);

        new DatabaseConnection().GetUser(userFrom, new OnUserEventListener() {
            @Override
            public void onUserRetrieved(User user) {
                user.addChat(chatID);
                new DatabaseConnection().UpdateUser(user);
            }

            @Override
            public void onUsersRetrieved(List<User> users) {
                throw new RuntimeException("Not Implemented");
            }

            @Override
            public void onTeachersRetrieved(List<User> teachers) {
                throw new RuntimeException("Not Implemented");
            }
        });
        Log.v(TAG, "Added chat to " + userFrom);

        return this.chatID;
    }
}
