package ar.edu.itba.ingesoft.Interfaces.Adapters;

import java.util.List;

public interface OnListContentUpdatedListener<K> extends OnCoursesTaughtEventListener {

    void onContentUpdated(List<K> newList);
}
