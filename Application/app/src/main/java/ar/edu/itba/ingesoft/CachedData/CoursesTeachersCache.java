package ar.edu.itba.ingesoft.CachedData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;

public class CoursesTeachersCache {

    static Map<String, List<User>> courseTeachers=new HashMap<>();
    static List<Course> coursesList = new ArrayList<>();
    static List<User> usersList = new ArrayList<>();

    public static Map<String, List<User>> getCourseTeachers(){
        return courseTeachers;
    }

    public static void setCourseTeachers(Map<String, List<User>> courseTeachers1){

        //todo chequear si esto funciona
        courseTeachers = courseTeachers1;
    }

    //updatear el hash
    public static void refreshCourseTeachers(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DatabaseConnection database = new DatabaseConnection();
        if(usersList == null || coursesList == null || usersList.size() == 0 || coursesList.size() == 0) {
            database.GetUsers(new OnUserEventListener() {
                @Override
                public void onUserRetrieved(User user) {

                }

                @Override
                public void onUsersRetrieved(List<User> users) {
                    usersList.clear();
                    usersList.addAll(users);
                    database.GetAllCourses(new OnCourseEventListener() {
                        @Override
                        public void onCourseRetrieved(Course course) {

                        }

                        @Override
                        public void onCoursesRetrieved(List<Course> courses) {
                            coursesList.clear();
                            coursesList.addAll(courses);
                            courseTeachers.clear();
                            for (Course c : coursesList) {
                                ArrayList<User> aux = new ArrayList<>();
                                for (User u : usersList) {
                                    if (u.getCourses().contains(c.getCode()))
                                        aux.add(u);
                                }
                                courseTeachers.put(c.getCode(), aux);
                            }
                        }

                        @Override
                        public void onCoursesReferencesRetrieved(List<DocumentReference> courses) {

                        }

                        @Override
                        public void onTeachersPerCourseRetrieved(Map<Course, List<User>> drToUser) {

                        }
                    });
                }

                @Override
                public void onTeachersRetrieved(List<User> teachers) {

                }
            });

        }
    }
}
