package echangole.com.firestoreexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String Tag=MainActivity.class.getName();
    public static final String KEY_TITLE="title";
    public static final String KEY_DESCRIPTION="description";
    private   String data="";

    private EditText editTextTitle,editTextDescription,editTextPriority;
    private TextView textViewData;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference bookRef=db.collection("Notebook");
    private DocumentReference noteRef=db.collection("Notebook").document("My first Note");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle=(EditText)findViewById(R.id.editTextTitle);
        editTextDescription=(EditText)findViewById(R.id.editTextDescription);
        editTextPriority=(EditText)findViewById(R.id.editTextPriority);
        textViewData=(TextView)findViewById(R.id.textViewData);



    }

    @Override
    protected void onStart() {
        super.onStart();
        bookRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null)
                {
                    return;
                }

                loadData(queryDocumentSnapshots);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void addNote(View view)
    {
        String title=editTextTitle.getText().toString().trim();
        String description=editTextDescription.getText().toString().trim();
        if(editTextPriority.length()==0)
        {
            editTextPriority.setText("0");
        }
        int priority=Integer.parseInt(editTextPriority.getText().toString());


        Note note=new Note(title,description,priority);


//        Map<String,Object> note=new HashMap<>();
//        note.put(KEY_TITLE,title);
//        note.put(KEY_DESCRIPTION,description);

        bookRef.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {
                Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.d(Tag,e.getMessage());
            }
        });

    }

    public void loadNote(View view)
    {
     Task task1= bookRef.whereLessThan("priority",2).orderBy("priority").get();
     Task task2=bookRef.whereGreaterThan("priority",2).orderBy("priority").get();

     Task<List<QuerySnapshot>> allTask= Tasks.whenAllSuccess(task1,task2);
     allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
         @Override
         public void onSuccess(List<QuerySnapshot> querySnapshots)
         {
             for(QuerySnapshot queryDocumentSnapshots:querySnapshots)
             {
                 loadData(queryDocumentSnapshots);
             }

         }
     });
    }

    private void loadData(QuerySnapshot queryDocumentSnapshots)
    {

        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
        {
            Note note=documentSnapshot.toObject(Note.class);
            String title=note.getTitle();
            String description=note.getDescription();
            int priority=note.getPriority();

            data+="Title: "+title+"\n"+"Description: "+description+"\n Priority: "+priority
                    +"\n\n";

        }
        textViewData.setText(data);


    }


    public void updateDesc(View view)
    {
        String description=editTextDescription.getText().toString().trim();
        Map<String,Object> note=new HashMap<>();
        note.put(KEY_DESCRIPTION,description);
        noteRef.update(note);
    }

    public void deleteRecord(View view)
    {
        noteRef.delete();


    }

    public void deleteDesc(View view)
    {
       Map<String,Object> note=new HashMap<>();
       note.put(KEY_DESCRIPTION, FieldValue.delete());
       noteRef.update(note);

    }
}