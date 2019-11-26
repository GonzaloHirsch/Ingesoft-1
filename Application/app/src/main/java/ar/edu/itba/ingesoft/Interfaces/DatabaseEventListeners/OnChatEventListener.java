package ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners;

import ar.edu.itba.ingesoft.Classes.Chat;

public interface OnChatEventListener {

    void onChatRetrieved(Chat chat);

    void onChatChanged(Chat chat);
}
