package ar.edu.itba.ingesoft.CachedData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.User;

public class CoursesTeachersCache {

    static Map<String, List<User>> courseTeachers=new HashMap<>();

    public static Map<String, List<User>> getCourseTeachers(){
        return courseTeachers;
    }

    public static void setCourseTeachers(Map<String, List<User>> courseTeachers1){

        //todo chequear si esto funciona
        // ver
        courseTeachers = courseTeachers1;
    }
}
