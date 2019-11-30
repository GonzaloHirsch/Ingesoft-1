package ar.edu.itba.ingesoft.Classes;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(sentBy, message1.sentBy) &&
                Objects.equals(timestamp, message1.timestamp) &&
                Objects.equals(message, message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentBy, timestamp, message);
    }
}
