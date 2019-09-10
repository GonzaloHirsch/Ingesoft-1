package ar.edu.itba.ingesoft.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class User implements DatabaseObject {

    private String mail;
    private String name;
    private String surname;
    private boolean isProfessor;
    private List<Course> courses;
    //private Map<Long, Appointment> appointments;
    private Universidad universidad;
    //private Map<Long, Chat> chats;

    @SuppressWarnings("unchecked")
    public User(Map<String, Object> data){
        this.name = (String) data.get("name");
        this.surname = (String) data.get("surname");
        this.mail = (String) data.get("mail");
        this.isProfessor = (Boolean) data.get("professor");
        this.courses = (List<Course>) data.get("courses");
        //this.appointments = (Map<Long, Appointment>) data.get("appointments");
        //this.chats = (Map<Long, Chat>) data.get("chats");
        this.universidad = new Universidad((Map<String, Object>)data.get("universidad"));
    }

    public User(){
        this.courses = new ArrayList<>();
        //this.appointments = new HashMap<>();
    }

    public User(String name, String surname, String mail, Universidad universidad){
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.isProfessor = false;
        this.courses = new ArrayList<>();
        //this.appointments = new HashMap<>();
        //this.chats = new HashMap<>();
        this.universidad = universidad;
    }

    public Map<String, Object> generateDataToUpdate(){
        Map<String, Object> data = new HashMap<>();

        data.put("name", this.name);
        data.put("surname", this.surname);
        data.put("professor", this.isProfessor);
        data.put("courses", this.courses);
        //data.put("appointments", this.appointments);
        data.put("universidad", this.universidad);
        //data.put("chats", this.chats);

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourses(List<Course> courses) {
        this.courses.addAll(courses);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void deleteCourse(Course course) {
        this.courses.remove(course);
    }

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

    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
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
}
