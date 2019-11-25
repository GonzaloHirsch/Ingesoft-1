package ar.edu.itba.ingesoft.ui.chats;

import android.os.Debug;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Message;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnChatEventListener;

public class ChatMessagesViewModel extends ViewModel {

    public static final String TAG = "CHAT MESSAGE VIEW MODEL";
    private Chat chatObj = null;
    private Long chatID = null;

    public ChatMessagesViewModel(){
    }

    /**
     * Metodo para settear el id del chat que se esta guardando en el viewmodel.
     * @param chatID of chat
     */
    public void setChatID(Long chatID){
        this.chatID = chatID;
        new DatabaseConnection().GetChat(chatID, new OnChatEventListener() {
            @Override
            public void onChatRetrieved(Chat chat) {
                if (chat != null)
                    chatObj = chat;
            }
        });
    }

    /**
     * Method to notify the view model about the new message the logged user sent
     * @param message
     */
    public void addMessage(Message message){
        // Store the new message in the object
        this.chatObj.addMensaje(message);

        // Update the database with the new message
        new DatabaseConnection().UpdateChat(this.chatObj);
    }

    public List<Message> retrieveMessages(){
        List<Message> messages = new LinkedList<>();

        if (chatID != null && chatObj != null){
            messages = chatObj.getMensajes();
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
            count = chatObj.getMensajes().size();
        } else {
            if (chatID == null)
                Log.e(TAG, "Null chat ID");
            if (chatObj == null)
                Log.e(TAG, "Null chat object");
        }

        return count;
    }
}
