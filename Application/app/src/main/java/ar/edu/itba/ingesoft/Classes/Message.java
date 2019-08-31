package ar.edu.itba.ingesoft.Classes;

import java.util.Date;
import java.util.Map;

public class Message {

    private String sentBy;
    private Date timestamp;
    private String message;

    public Message(Map<String, Object> data){
        this.message = (String) data.get("message");
        this.sentBy = (String) data.get("sentBy");
        this.timestamp = (Date) data.get("timestamp");
    }

    public Message(String sentBy, String message, Date timestamp){
        this.sentBy = sentBy;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
