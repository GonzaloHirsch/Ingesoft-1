package ar.edu.itba.ingesoft.Database;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ar.edu.itba.ingesoft.Classes.Chat;
import ar.edu.itba.ingesoft.Classes.Contact;
import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.Message;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnChatEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnContactEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;
import ar.edu.itba.ingesoft.MainActivity;

public class DatabaseConnection {

    public static final String TAG = "DATABASE_CONNECTION";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /*--------------------------------------USERS----------------------------------------*/

    /**
     * Inserts the user into the database.
     * @param user to be inserted
     */
    public void InsertUser(User user){
        // Add the new document for the user using the email as the ID of the document
        db.collection("Users")
                .document(user.getMail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Updates the given user.
     * Uses the getter for the updateable fields.-
     * @param user to be updated.
     */
    public void UpdateUser(final User user){
        // Update the user in the given document
        db.collection("Users")
                .document(user.getMail())
                .update(user.generateDataToUpdate())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User " + user.getMail() + " updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating " + user.getMail() + " user", e);
                    }
                });
    }

    /**
     * Gets the user identified by the given email.
     * @param email of the user to get
     */
    public void GetUser(String email, final OnUserEventListener eventListener){

        // Add the new document for the user using the email as the ID of the document
        db.collection("Users")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                Map<String, Object> data = document.getData();




                                if (data != null){

                                    //todo delete this test

                                    for(Map.Entry e : data.entrySet()){
                                        Log.v("DBC-GetUser", e.getKey() + e.getValue().toString());
                                    }

                                    // Stores all the info in the class
                                    User user = new User(data);
                                    eventListener.onUserRetrieved(user);
                                } else {
                                    Log.d(TAG, "No data in document");
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    /**
     * Getter for all the users in the database.
     * @param eventListener with callback to this function.
     */
    public void GetUsers(final OnUserEventListener eventListener){
        // Add the new document for the user using the email as the ID of the document
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();

                            List<User> users = new ArrayList<>();

                            if (document != null){
                                for (DocumentSnapshot ds : document) {
                                    if (ds.getData() != null){
                                        users.add(new User(ds.getData()));
                                    }
                                }
                                eventListener.onUsersRetrieved(users);
                            } else {
                                Log.d(TAG, "Query GetUsers returned null");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    /**
     * Getter for all the teachers in the system.
     * @param eventListener with callback to this function.
     */
    public void GetTeachers(final OnUserEventListener eventListener){
        db.collection("Users")
                .whereEqualTo("isTeacher", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();

                            List<User> users = new ArrayList<>();

                            if (document != null){
                                for (DocumentSnapshot ds : document) {
                                    if (ds.getData() != null){
                                        users.add(new User(ds.getData()));
                                    }
                                }
                                eventListener.onTeachersRetrieved(users);
                            } else {
                                Log.d(TAG, "Query GetTeachers returned null");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    /*--------------------------------------CONTACTS----------------------------------------*/

    /**
     * Inserts the given contact under the collection of the given user.
     * When called, it should be done twice, so that both users have each other as a contact.
     * @param userEmail of the user who has the new contact.
     * @param contact of the user given before.
     */
    public void InsertContact(String userEmail, Contact contact){
        // Add the new document for the user using the email as the ID of the document
        db.collection("Contacts")
                .document(userEmail)
                .collection("Contacts")
                .document(contact.getUser())
                .set(contact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Getter for the contacts of a given user.
     * @param userEmail of the user who has the contacts.
     * @param eventListener that has the callback to this function.
     */
    public void GetContacts(String userEmail, final OnContactEventListener eventListener){
        db.collection("Contacts")
                .document(userEmail)
                .collection("Contacts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();

                            List<Contact> contacts = new ArrayList<>();

                            // Fills the list with the recovered contacts
                            if (document != null){
                                for (DocumentSnapshot ds : document) {
                                    if (ds.getData() != null){
                                        contacts.add(new Contact(ds.getData()));
                                    }
                                }
                                eventListener.onContactsRetrieved(contacts);
                            } else {
                                Log.d(TAG, "Query GetContacts returned null");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    /*--------------------------------------CHATS----------------------------------------*/

    /**
     * Inserts the new chat in the chat documents.
     * @param chat to be inserted.
     */
    public void InsertChat(Chat chat){
        db.collection("Chats")
                .document(chat.getChatID().toString())
                .set(chat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Gets the chat by the chat ID.
     * @param chatID of the given chat.
     * @param eventListener that has the callback to this function.
     */
    public void GetChat(Long chatID, final OnChatEventListener eventListener){
        db.collection("Chats")
                .document(chatID.toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                Map<String, Object> data = document.getData();

                                if (data != null){
                                    // Stores all the info in the class
                                    Chat chat = new Chat(data);

                                    eventListener.onChatRetrieved(chat);
                                } else {
                                    Log.d(TAG, "No data in document");
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    /**
     * Updates the given chat.
     * @param chat to be updated.
     */
    public void UpdateChat(final Chat chat){
        db.collection("Chats")
                .document(chat.getChatID().toString())
                .update(chat.generateDataToUpdate())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User " + chat.getChatID() + " updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating " + chat.getChatID() + " user", e);
                    }
                });
    }

    public void SetUpChatListener(Long chatID){
        DocumentReference ref = db.collection("Chats").document(chatID.toString());

        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                if (documentSnapshot != null){
                    Map<String, Object> data = documentSnapshot.getData();

                    if (data != null){
                        //Recover the chat in the database
                        Chat chat = new Chat(data);

                        /*
                        //Verify there is a cache chat
                        if (((DataCache) context.getApplicationContext()).getChats() != null){

                            //Recover the cache chats
                            Chat cacheChat = ((DataCache) context.getApplicationContext()).GetChat(chatID);
                            if (cacheChat != null && cacheChat.getMessageCount() < chat.getMessageCount()) {

                                //Instantiate a new list of messages
                                List<ChatMessage> newMessages = new ArrayList<>();

                                //Recover the list of messages from the database
                                List<ChatMessage> recoveredMessages = chat.getMessages();

                                //Insert the new messages
                                for (int i = cacheChat.getMessageCount(); i < chat.getMessageCount(); i++){
                                    //Store the instance temporarily
                                    ChatMessage message = recoveredMessages.get(i);

                                    //Inserting in the new list
                                    newMessages.add(message);

                                    //Inserting in the cache
                                    ((DataCache) context.getApplicationContext()).AddMessageToChat(chatID, message);
                                }

                                //Notify the recycler that there are new messages
                                if (newMessages.size() != 0) {
                                    //Call the method to reload the recycler view
                                    ((MessageRecyclerViewAdapter) recyclerView.getAdapter()).MessageWasAdded(newMessages);

                                    recyclerView.smoothScrollToPosition(chat.getMessageCount() - 1);
                                }
                            }
                        } else {
                            //Recover the list of messages from the database
                            List<ChatMessage> recoveredMessages = chat.getMessages();

                            if (((DataCache) context.getApplicationContext()).getChats() == null){
                                ((DataCache) context.getApplicationContext()).setChats(new HashMap<>());
                            }

                            ((DataCache) context.getApplicationContext()).AddChat(chat);

                            //Notify the recycler that there are new messages
                            if (recoveredMessages.size() != 0) {
                                //Call the method to reload the recycler view
                                ((MessageRecyclerViewAdapter) recyclerView.getAdapter()).MessageWasAdded(recoveredMessages);

                                recyclerView.smoothScrollToPosition(chat.getMessageCount() - 1);
                            }
                        }
                        */
                    }
                }
            }
        });
    }

    /**
     * Getter for all the courses, by object.
     * @param listener for the event to use the data.
     */
    public void GetAllCourses(OnCourseEventListener listener){
        db.collection("Courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();

                    List<Course> courses = new ArrayList<>();

                    if (document != null){
                        for (DocumentSnapshot ds : document) {
                            if (ds.getData() != null){
                                courses.add(new Course(ds.getData()));
                            }
                        }
                        listener.onCoursesRetrieved(courses);
                    } else {
                        Log.d(TAG, "Query GetAllCourses returned null");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * Getter for the teacher for each course, grouped by course.
     * @param listener for the event.
     */
    public void GetTeachersPerCourse(OnCourseEventListener listener){
        db.collection("Users").whereEqualTo("professor", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();

                    List<User> teachers = new ArrayList<>();

                    Map<DocumentReference, List<User>> refToUsers = new HashMap<>();
                    Map<Course, List<User>> coursesToUsers = new HashMap<>();

                    // Fill the user list
                    if (document != null){
                        for (DocumentSnapshot ds : document) {
                            if (ds.getData() != null){
                                teachers.add(new User(ds.getData()));
                            }
                        }

                        for (User teacher : teachers){
                            for (DocumentReference dr : teacher.getCourses()){
                                List<User> aux;
                                if (refToUsers.containsKey(dr)){
                                    aux = refToUsers.get(dr);
                                } else {
                                    aux = new ArrayList<>();
                                }
                                aux.add(teacher);
                                refToUsers.put(dr, aux);
                            }
                        }

                        // Recupera el curso para cada una
                        for (DocumentReference dr : refToUsers.keySet()){
                            GetCourseByReference(dr.toString(), new OnCourseEventListener() {
                                @Override
                                public void onCourseRetrieved(Course course) {
                                    coursesToUsers.put(course, refToUsers.get(dr));
                                }

                                @Override
                                public void onCoursesRetrieved(List<Course> courses) {}

                                @Override
                                public void onCoursesReferencesRetrieved(List<DocumentReference> courses) {}

                                @Override
                                public void onTeachersPerCourseRetrieved(Map<Course, List<User>> drToUser) {}
                            });
                        }

                        listener.onTeachersPerCourseRetrieved(coursesToUsers);
                    } else {
                        Log.d(TAG, "Query GetAllCoursesReferences returned null");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * Getter for all the courses, by reference to the document.
     * @param listener for the event to use the data.
     */
    public void GetAllCoursesReferences(OnCourseEventListener listener){
        db.collection("Courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();

                    List<DocumentReference> courses = new ArrayList<>();

                    if (document != null){
                        for (DocumentSnapshot ds : document) {
                            if (ds.getData() != null){
                                courses.add(ds.getReference());
                            }
                        }
                        listener.onCoursesReferencesRetrieved(courses);
                    } else {
                        Log.d(TAG, "Query GetAllCoursesReferences returned null");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void GetCourseByReference(String theReference, OnCourseEventListener listener){
        db.document(theReference).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document !=null && document.exists()){
                        Map<String, Object> data = document.getData();
                        if(data!=null){
                            Course course = new Course(data);
                            listener.onCourseRetrieved(course);
                            Log.v("getCourseByReference", "everything successful");
                        }
                        else{
                            Log.v("getCourseByReference", "data null");
                        }
                    }
                    else{
                        Log.v("getCourseByReference", "document null or non-existing");
                    }
                }
        });
    }
}
