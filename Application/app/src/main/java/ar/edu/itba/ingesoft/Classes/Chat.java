package ar.edu.itba.ingesoft.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Chat {

    private Long chatID;
    private String from;
    private String to;
    private List<Message> messages;

    @SuppressWarnings("unchecked")
    public Chat(Map<String, Object> data){
        this.from = (String) data.get("from");
        this.to = (String) data.get("to");
        this.chatID = (Long) data.get("chatID");
        this.messages = (List<Message>) data.get("messages");
    }

    public Chat(Long chatID, String from, String to){
        this.chatID = chatID;
        this.to = to;
        this.from = from;
        this.messages = new ArrayList<>();
    }

    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<Message> getMensajes() {
        return messages;
    }

    public void addMensaje(Message mensaje) {
        this.messages.add(mensaje);
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
}
