package ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.User;

public interface OnUserEventListener {

    void onUserRetrieved(User user);
    void onUsersRetrieved(List<User> users);
    void onTeachersRetrieved(List<User> teachers);
}
