package ar.edu.itba.ingesoft.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class Chat implements DatabaseObject {

    private String chatID;
    private String userA;
    private String userB;
    private List<Message> messages;

    @SuppressWarnings("unchecked")
    public Chat(Map<String, Object> data){
        this.userA = (String) data.get("userA");
        this.userB = (String) data.get("userB");
        this.chatID = (String) data.get("chatID");
        this.messages = (List<Message>) data.get("messages");
    }

    public Chat(String userA, String userB){
        this.chatID = UUID.randomUUID().toString();;
        this.userB = userB;
        this.userA = userA;
        this.messages = new ArrayList<>();
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getFrom() {
        return userA;
    }

    public void setFrom(String userA) {
        this.userA = userA;
    }

    public String getTo() {
        return userB;
    }

    public void setTo(String userB) {
        this.userB = userB;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return chatID.equals(chat.chatID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatID);
    }

    @Override
    public Map<String, Object> generateDataToUpdate() {
        Map<String, Object> data = new HashMap<>();

        data.put("messages", this.messages);

        return data;
    }
}
