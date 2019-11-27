package ar.edu.itba.ingesoft.ui.chats;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.lifecycle.ViewModel;
import ar.edu.itba.ingesoft.CachedData.UserCache;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnChatEventListener;
import ar.edu.itba.ingesoft.Interfaces.GeneralListeners.OnObjectEventListener;

public class ChatsViewModel extends ViewModel {

    private String userEmail;
    private List<Chat> chats;

    private AtomicInteger recoveredChats;

    public ChatsViewModel() {
        this.chats = new ArrayList<>();
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public void recoverData(OnObjectEventListener<Chat> eventListener){
        User user = UserCache.GetUser();
        for (String id : user.getChats()){
            new DatabaseConnection().GetChat(id, new OnChatEventListener() {
                @Override
                public void onChatRetrieved(Chat chat) {
                    chats.add(chat);
                    // Verify the amount of chats recovered is correct
                    if (chats.size() == user.getChats().size())
                        eventListener.onObjectRetrieved(chats);
                }

                @Override
                public void onChatChanged(Chat chat) {
                    throw new RuntimeException("Not Implemented");
                }
            });
        }
    }
}