package ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.Universidad;
import ar.edu.itba.ingesoft.Classes.User;

public interface OnUniversityEventListener {

    void onUniversitiesRetrieved(List<Universidad> universities);
    void onUnievrsityRetrieved(Universidad university);
}
