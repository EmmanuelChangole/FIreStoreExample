package echangole.com.firestoreexample;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;
import java.util.Map;

public class Note
{
    private String title;
    private String description;
    private int priority;
    private Map<String,Boolean> tags;
    private String id;


    public Note()
    {

    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;

    }



    public Note(String title, String description, int priority ,Map<String,Boolean> tags) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.tags=tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Boolean> getTags() {
        return tags;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
