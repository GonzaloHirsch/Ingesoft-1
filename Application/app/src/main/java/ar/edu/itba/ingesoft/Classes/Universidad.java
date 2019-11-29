package ar.edu.itba.ingesoft.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class Universidad implements DatabaseObject, Parcelable {

    private String name;
    private List<Course> courses;

    @SuppressWarnings("unchecked")
    public Universidad(Map<String, Object> data){
        this.name = (String) data.get("name");
        this.courses = (List<Course>) data.get("courses");
    }

    public Universidad(String name){
        this.name = name;
        this.courses = new ArrayList<>();
    }

    protected Universidad(Parcel in) {
        name = in.readString();
        courses = in.createTypedArrayList(Course.CREATOR);
    }

    public static final Parcelable.Creator<Universidad> CREATOR = new Parcelable.Creator<Universidad>() {
        @Override
        public Universidad createFromParcel(Parcel in) {
            return new Universidad(in);
        }

        @Override
        public Universidad[] newArray(int size) {
            return new Universidad[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.courses);
    }
}
