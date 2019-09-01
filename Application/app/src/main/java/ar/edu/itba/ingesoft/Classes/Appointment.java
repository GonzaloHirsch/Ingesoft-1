package ar.edu.itba.ingesoft.Classes;

import java.util.Date;
import java.util.Map;

public class Appointment {

    private Long date;
    private String userEmail;

    public Appointment(Map<String, Object> data){
        this.date = (Long) data.get("date");
        this.userEmail = (String) data.get("userEmail");
    }

    public Appointment(Date date, String userEmail){
        this.date = date.getTime();
        this.userEmail = userEmail;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
