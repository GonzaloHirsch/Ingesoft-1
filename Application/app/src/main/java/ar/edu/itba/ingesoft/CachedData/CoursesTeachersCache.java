package ar.edu.itba.ingesoft.CachedData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Firebase.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;

public class CoursesTeachersCache{

    static final Map<String, List<User>> courseTeachers=new HashMap<>();
    static Map<String, Course> coursesMap = new HashMap<>();
    static List<User> usersList = new ArrayList<>();

    public static List<Course> getCoursesList() {
        return (List<Course>)coursesMap.values();
    }

    public static synchronized List<User> getUsersList() {
        return usersList;
    }

    public static synchronized void setCoursesList(List<Course> coursesList) {
        synchronized(coursesMap){
            coursesMap.clear();
            for (Course c : coursesList){
                coursesMap.put(c.getCode(), c);
            }
        }
    }
    public static synchronized void setUsersList(List<User> usersList) {
        CoursesTeachersCache.usersList = usersList;
    }
    public static synchronized Map<String, List<User>> getCourseTeachers(){
        synchronized (courseTeachers) {
            return courseTeachers;
        }
    }

    public static void setCourseTeachers(Map<String, List<User>> courseTeachers1){
        //todo chequear si esto funciona
        courseTeachers.clear();
        for(Map.Entry<String, List<User>> e : courseTeachers1.entrySet()){
            courseTeachers.put(e.getKey(), e.getValue());
        };
    }

    //updatear el hash
    public static void refreshCourseTeachers(String university){
        synchronized(courseTeachers) {
            DatabaseConnection databaseConnection = new DatabaseConnection();

            // todo esto duele a la vista, pero encadenar los tasks implicaba repetir el código
            // de DatabaseConnection...
            if (true /*usersList == null || usersList.size() == 0*/) {
                databaseConnection.GetUsers(new OnUserEventListener() {
                    @Override
                    public void onUserRetrieved(User user) {
                    }

                    @Override
                    public void onUsersRetrieved(List<User> users) {
                        usersList = users;
                        if (coursesMap == null || coursesMap.size() == 0) {
                            databaseConnection.GetAllCourses(university, new OnCourseEventListener() {
                                @Override
                                public void onCourseRetrieved(Course course) {
                                }

                                @Override
                                public void onCoursesRetrieved(List<Course> courses) {
                                    setCoursesList(courses);
                                    generateCourseTeachersHashMap();
                                }

                                @Override
                                public void onCoursesReferencesRetrieved(List<DocumentReference> courses) {
                                }

                                @Override
                                public void onTeachersPerCourseRetrieved(Map<Course, List<User>> drToUser) {
                                }
                            });
                        } else {
                            generateCourseTeachersHashMap();
                        }
                    }

                    @Override
                    public void onTeachersRetrieved(List<User> teachers) {
                    }
                });
            } else if (coursesMap == null || coursesMap.size() == 0) {
                databaseConnection.GetAllCourses(university, new OnCourseEventListener() {
                    @Override
                    public void onCourseRetrieved(Course course) {
                    }

                    @Override
                    public void onCoursesRetrieved(List<Course> courses) {
                        setCoursesList(courses);
                        generateCourseTeachersHashMap();
                    }

                    @Override
                    public void onCoursesReferencesRetrieved(List<DocumentReference> courses) {
                    }

                    @Override
                    public void onTeachersPerCourseRetrieved(Map<Course, List<User>> drToUser) {
                    }
                });
            } else {
                generateCourseTeachersHashMap();
            }
        }
    }

    public static void generateCourseTeachersHashMap(){
        synchronized(courseTeachers){
            courseTeachers.clear();
            for (User u : usersList){
                for (String code : u.getCourses()){
                    List<User> aux = new ArrayList<>();
                    if (courseTeachers.containsKey(code)){
                        aux = courseTeachers.get(code);
                        aux.add(u);
                        courseTeachers.put(code, aux);
                    } else {
                        aux.add(u);
                        courseTeachers.put(code, aux);
                    }
                }
            }

        /*
                for (Course c : coursesList) {
            ArrayList<User> aux = new ArrayList<>();
            for (User u : usersList) {
                if (u.getCoursesHashSet().contains(c.getCode()))
                    aux.add(u);
            }
            courseTeachers.put(c.getCode(), aux);
        }*/
        }
    }

    public static List<Course> getCoursesWithTutors(List<Course> courses){
        synchronized (courseTeachers){
            List<Course> finalList = new ArrayList<>();
            for (String key : courseTeachers.keySet()){
                if (coursesMap.get(key) != null)
                    finalList.add(coursesMap.get(key));
            }
            /*
            for(Course c : courses){
                if(courseTeachers.get(c.getCode()) != null && courseTeachers.get(c.getCode()).size()>0)
                    finalList.add(c);
            }*/
            return finalList;
        }
    }

}
