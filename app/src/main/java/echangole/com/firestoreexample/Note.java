package echangole.com.firestoreexample;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class Note
{
    private String title;
    private String description;
    private int priority;
    private List<String> tags;


    public Note()
    {

    }

    public Note(String title, String description,List<String> tags) {
        this.title = title;
        this.description = description;
        this.tags=tags;
    }

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public List<String> getTags() {
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
