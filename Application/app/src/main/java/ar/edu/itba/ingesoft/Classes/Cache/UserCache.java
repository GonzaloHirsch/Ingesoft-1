package ar.edu.itba.ingesoft.Classes.Cache;

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
