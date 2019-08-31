package ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.Contact;

public interface OnContactEventListener {

    void onContactsRetrieved(List<Contact> contacts);
}
