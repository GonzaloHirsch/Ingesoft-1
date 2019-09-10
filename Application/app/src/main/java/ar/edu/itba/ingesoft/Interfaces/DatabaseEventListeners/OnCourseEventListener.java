package ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;

public interface OnCourseEventListener {

    void onCourseRetrieved(Course course);
    void onCoursesRetrieved(List<Course> courses);
    void onCoursesReferencesRetrieved(List<DocumentReference> courses);
    void onTeachersPerCourseRetrieved(Map<DocumentReference, List<User>> drToUser);
}
