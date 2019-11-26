package ar.edu.itba.ingesoft.Classes;

import java.util.Map;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class Contact implements DatabaseObject {

    private String chatID;
    private String user;

    public Contact(Map<String, Object> data){
        this.chatID = (String) data.get("chatID");
        this.user = (String) data.get("user");
    }

    public Contact(String chatID, String user){
        this.chatID = chatID;
        this.user = user;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public Map<String, Object> generateDataToUpdate() {
        return null;
    }
}
