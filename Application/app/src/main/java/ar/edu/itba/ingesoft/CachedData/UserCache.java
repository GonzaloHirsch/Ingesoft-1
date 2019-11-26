package ar.edu.itba.ingesoft.CachedData;

import ar.edu.itba.ingesoft.Classes.User;

public class UserCache {

    private static User user;

    public static void SetUser(User userToStore){
        user = userToStore;
    }

    public static User GetUser(){
        return user;
    }
}
