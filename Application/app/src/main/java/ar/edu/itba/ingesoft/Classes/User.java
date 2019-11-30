package ar.edu.itba.ingesoft.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class User implements DatabaseObject, Parcelable {

    private String mail;
    private String name;
    private boolean isProfessor;
    private List<String> courses;
    private List<String> chats;
    private String universidad;

    public static User GenerateAnonymousUser(String universidad){
        User user = new User();
        user.mail = "N/a";
        user.name = "Anonymous User";
        user.isProfessor = false;
        user.universidad = universidad;
        return user;
    }

    @SuppressWarnings("unchecked")
    public User(Map<String, Object> data){
        this.name = (String) data.get("name");
        this.mail = (String) data.get("mail");
        if(data.get("isProfessor") != null)
            this.isProfessor = (Boolean) data.get("isProfessor");
        else
            this.isProfessor = false;
        this.courses = (List<String>) data.get("courses");
        this.chats = (List<String>) data.get("chats");
        this.universidad = (String) data.get("universidad");

    }

    public User(){
        this.courses = new ArrayList<>();
        this.chats = new ArrayList<>();
    }

    public User(String name, String mail, String universidad){
        this.name = name;
        this.mail = mail;
        this.isProfessor = false;
        this.courses = new ArrayList<>();
        this.chats = new ArrayList<>();
        this.universidad = universidad;
    }

    public Map<String, Object> generateDataToUpdate(){
        Map<String, Object> data = new HashMap<>();

        data.put("name", this.name);
        data.put("mail", this.mail);
        data.put("professor", this.isProfessor);
        data.put("courses", this.courses);
        data.put("universidad", this.universidad);
        data.put("chats", this.chats);

        return data;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void addCourses(List<String> courses) {
        this.courses.addAll(courses);
    }

    public void addCourse(String course) {
        this.courses.add(course);
    }

    public void deleteCourse(String course) {
        this.courses.remove(course);
    }

    public void setCourses(List<String> courses){
        this.courses = courses;
    }

    public List<String> getChats(){ return this.chats; }

    public void addChat(String chatID){ this.chats.add(chatID); }
    /*
    public Map<Long, Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Date date, Appointment appointment) {
        //this.appointments.put(date.getTime(), appointment);
    }

    public boolean hasAppointment(Date date) {
        return this.appointments.containsKey(date.getTime());
    }

    public Set<Long> getAppointmentDates() {
        return this.appointments.keySet();
    }
*/
    /*
    public Map<Long, Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        this.chats.put(chat.getChatID(), chat);
    }


     */

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return mail.equals(user.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.mail);
        dest.writeStringList(this.courses);
        dest.writeString(this.universidad);
    }

    protected User(Parcel in){
        this.name = in.readString();
        this.mail = in.readString();
        this.courses = in.createStringArrayList();
        this.universidad = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel source){
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
