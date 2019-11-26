package ar.edu.itba.ingesoft.Interfaces.GeneralListeners;

import java.util.List;

public interface OnObjectEventListener<T> {

    void onObjectRetrieved(List<T> obj);
}
