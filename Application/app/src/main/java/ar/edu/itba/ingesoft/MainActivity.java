package ar.edu.itba.ingesoft;

import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import ar.edu.itba.ingesoft.Authentication.Authenticator;
import ar.edu.itba.ingesoft.CachedData.UserCache;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnChatEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;

public class MainActivity extends AppCompatActivity {

    public static final String SP = "sharedPrefs";
    public static final String UNIV_SP = "univSharedPrefs";

    public static final String CHAT_ID_EXTRA = "chat_id_extra";
    public static final String CHAT_RECIPIENT_EXTRA = "chat_recipient_extra";
    public static final String CHAT_RECIPIENT_NAME_EXTRA = "chat_recipient_name_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        FirebaseUser firebaseUser = new Authenticator().getSignedInUser();
        AppBarConfiguration appBarConfiguration;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        if (firebaseUser.isAnonymous()){
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_search, R.id.navigation_profile)
                    .build();

            navView.inflateMenu(R.menu.bottom_nav_menu_alt);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
        } else {
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_search, R.id.navigation_chats, R.id.navigation_profile)
                    .build();

            navView.inflateMenu(R.menu.bottom_nav_menu);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);

            new DatabaseConnection().GetUser(new Authenticator().getSignedInUser().getEmail(), new OnUserEventListener() {
                @Override
                public void onUserRetrieved(User user) {
                    if (user != null){
                        UserCache.SetUser(user);
                        List<Chat> chats = new ArrayList<>();
                        for (String id : user.getChats()){
                            new DatabaseConnection().GetChat(id, new OnChatEventListener() {
                                @Override
                                public void onChatRetrieved(Chat chat) {
                                    chats.add(chat);
                                    // Verify the amount of chats recovered is correct
                                    if (chats.size() == user.getChats().size())
                                        UserCache.SetChats(chats);
                                }

                                @Override
                                public void onChatChanged(Chat chat) {
                                    throw new RuntimeException("Not Implemented");
                                }
                            });
                        }
                    }
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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
