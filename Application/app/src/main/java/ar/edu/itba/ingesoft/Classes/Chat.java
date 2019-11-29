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
    private String to;
    private String from;
    private String toName;
    private String fromName;
    private List<Message> messages;

    @SuppressWarnings("unchecked")
    public Chat(Map<String, Object> data){
        this.to = (String) data.get("to");
        this.toName = (String) data.get("toName");
        this.from = (String) data.get("from");
        this.fromName = (String) data.get("fromName");
        this.chatID = (String) data.get("chatID");

        this.messages = new ArrayList<>();
        if (data.get("messages") != null){
            for(Map<String, Object> map : (List<Map<String, Object>>)data.get("messages")){
                this.messages.add(new Message(map));
            }
        }
    }

    public Chat(String from, String to, String fromName, String toName){
        this.chatID = UUID.randomUUID().toString();;
        this.from = from;
        this.fromName = fromName;
        this.to = to;
        this.toName = toName;
        this.messages = new ArrayList<>();
    }

    public static Chat Merge(Chat originalChat, Chat newChat){
        Chat result = new Chat(originalChat.from, originalChat.to, originalChat.fromName, originalChat.toName);

        result.setChatID(originalChat.getChatID());

        int originalIndex = 0;
        int newIndex = 0;
        List<Message> originalMsg = originalChat.getMessages();
        List<Message> newMsg = newChat.getMessages();
        while(originalIndex < originalMsg.size() || newIndex < newMsg.size()){
            if (originalIndex < originalMsg.size() && newIndex < newMsg.size()){
                if (newMsg.get(newIndex).equals(originalMsg.get(originalIndex))){
                    result.addMessage(newMsg.get(newIndex));
                    newIndex++;
                    originalIndex++;
                } else if (newMsg.get(newIndex).getTimestamp() < originalMsg.get(originalIndex).getTimestamp()){
                    result.addMessage(newMsg.get(newIndex++));
                } else {
                    result.addMessage(originalMsg.get(originalIndex++));
                }
            } else if (originalIndex < originalMsg.size()){
                result.addMessage(originalMsg.get(originalIndex++));
            } else {
                result.addMessage(newMsg.get(newIndex++));
            }
        }

        return result;
    }

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setTo(String to) {
        this.to = to;
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
        data.put("to", this.to);
        data.put("toName", this.toName);
        data.put("from", this.from);
        data.put("fromName", this.fromName);

        return data;
    }
}
