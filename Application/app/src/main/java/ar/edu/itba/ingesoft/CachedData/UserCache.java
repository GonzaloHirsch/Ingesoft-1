package ar.edu.itba.ingesoft.CachedData;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Message;
import ar.edu.itba.ingesoft.Classes.User;

public class UserCache {

    private static User user;
    private final static Map<String, Chat> chatsMap = new HashMap();

    public static void SetUser(User userToStore){
        user = userToStore;
        //todo para los chats
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
//        for(String s : user.getChats()) {
//            tasks.add(db.collection("Chats")
//                    .document(s)
//                    .get());
//
//        }
//        Tasks.whenAllSuccess(tasks).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> tasks) {
//                chats.clear();
//
//            }
//        });
    }

    public synchronized static void SetChats(List<Chat> chats){
        synchronized (chatsMap){
            chatsMap.clear();
            for (Chat chat : chats){
                chatsMap.put(chat.getChatID(), chat);
            }
        }
    }

    public static List<Chat> GetChats(){
        return new ArrayList<>(chatsMap.values());
    }

    public static void AddMessage(String chatID, Message message){
        synchronized (chatsMap){
            chatsMap.get(chatID).addMessage(message);
        }
    }

    public static void AddChat(Chat chat){
        synchronized (chatsMap){
            chatsMap.put(chat.getChatID(), chat.clone());
        }
    }

    public static User GetUser(){
        return user;
    }
}
