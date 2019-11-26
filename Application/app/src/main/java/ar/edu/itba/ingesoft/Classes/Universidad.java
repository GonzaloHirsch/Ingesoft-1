package ar.edu.itba.ingesoft.Classes;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class Universidad implements DatabaseObject {

    private String name;
    private List<Course> courses;

    @SuppressWarnings("unchecked")
    public Universidad(Map<String, Object> data){
        if(this.name!=null)
            this.name = (String) data.get("name");
        else //todo change this
            this.name = "NoUniversityName";
        if(this.courses !=null)
            this.courses = (List<Course>) data.get("courses");
        else //todo change this
            this.courses = new ArrayList<Course>();
    }

    public Universidad(String name){
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void deleteCourse(Course courses) {
        this.courses.remove(courses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Universidad that = (Universidad) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public Map<String, Object> generateDataToUpdate() {
        return null;
    }
}
