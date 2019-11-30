package ar.edu.itba.ingesoft.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;
import java.util.Objects;

import ar.edu.itba.ingesoft.Interfaces.Adapters.Selectable;
import ar.edu.itba.ingesoft.Interfaces.DatabaseObject;

public class Course extends Selectable implements DatabaseObject, Parcelable {

    private String name;
    private String code;

    public Course(Map<String, Object> data){
        this.code = (String) data.get("code");
        this.name = (String) data.get("name");
    }

    public Course(){}

    public Course(String name, String code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Map<String, Object> generateDataToUpdate() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) &&
                code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(code);
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            Course c = new Course();
            c.name = in.readString();
            c.code = in.readString();
            return c;
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public static Creator<Course> getCREATOR(){
        return CREATOR;
    }
}
