package ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;

public interface OnCourseEventListener {

    void onCourseRetrieved(Course course);
    void onCoursesRetrieved(List<Course> courses);
}
