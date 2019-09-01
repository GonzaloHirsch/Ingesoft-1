package ar.edu.itba.ingesoft.Classes;

import java.util.Date;
import java.util.Map;

public class Message {

    private String sentBy;
    private Long timestamp;
    private String message;

    public Message(Map<String, Object> data){
        this.message = (String) data.get("message");
        this.sentBy = (String) data.get("sentBy");
        this.timestamp = (Long) data.get("timestamp");
    }

    public Message(String sentBy, String message, Date timestamp){
        this.sentBy = sentBy;
        this.message = message;
        this.timestamp = timestamp.getTime();
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp.getTime();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
