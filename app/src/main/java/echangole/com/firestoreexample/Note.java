package echangole.com.firestoreexample;

public class Note
{
    private String title;
    private String description;
    private int priority;

    public Note()
    {

    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
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
