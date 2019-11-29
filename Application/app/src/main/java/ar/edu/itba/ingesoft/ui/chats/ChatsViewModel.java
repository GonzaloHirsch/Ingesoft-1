package ar.edu.itba.ingesoft.ui.chats;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ar.edu.itba.ingesoft.CachedData.CoursesTeachersCache;
import ar.edu.itba.ingesoft.CachedData.UserCache;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnChatEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;
import ar.edu.itba.ingesoft.Interfaces.GeneralListeners.OnObjectEventListener;

public class ChatsViewModel extends ViewModel {

    public static final String TAG = "ChatsViewModel";

    private String userEmail;

    private MutableLiveData<List<Chat>> chats = new MutableLiveData<>();

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public ChatsViewModel() { }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public MutableLiveData<List<Chat>> recoverData(){
        if(chats.getValue()==null) {
            // Posting value to the loading mutable live data obj
            loading.postValue(true);
            Log.v(TAG, "MutableLiveDataCreation");

            if (UserCache.GetUser() == null){
                new DatabaseConnection().GetUser(this.userEmail, new OnUserEventListener() {
                    @Override
                    public void onUserRetrieved(User user) {
                        UserCache.SetUser(user);
                        chatRecoveryWithUser(user);
                    }

                    @Override
                    public void onUsersRetrieved(List<User> users) {
                        //
                    }

                    @Override
                    public void onTeachersRetrieved(List<User> teachers) {
                        //
                    }
                });
            } else {
                User user = UserCache.GetUser();
                chatRecoveryWithUser(user);
            }
        }
        return chats;
    }

    private void chatRecoveryWithUser(User user){
        List<Chat> chatsList = new ArrayList<>();
        List<Chat> storedChats = UserCache.GetChats();

        if (user != null && user.getChats() != null) {
            if (storedChats != null && user.getChats().size() == storedChats.size()){
                chats.setValue(storedChats);
                loading.postValue(false);
                Log.v(TAG, "Chats posted");
            } else {
                for (String id : user.getChats()) {
                    new DatabaseConnection().GetChat(id, new OnChatEventListener() {
                        @Override
                        public void onChatRetrieved(Chat chat) {
                            chatsList.add(chat);
                            Log.v(TAG, "Chat retrieved");
                            // Verify the amount of chats recovered is correct
                            if (chatsList.size() == user.getChats().size()) {
                                chats.setValue(chatsList);
                                loading.postValue(false);
                                Log.v(TAG, "Chats posted");
                                UserCache.SetChats(chatsList);
                            }
                        }

                        @Override
                        public void onChatChanged(Chat chat) {
                            throw new RuntimeException("Not Implemented");
                        }
                    });
                }
            }
        }
    }

    public MutableLiveData<Boolean> getLoading(){
        return loading;
    }
}