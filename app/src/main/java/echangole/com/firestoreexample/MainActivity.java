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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    public static final String Tag=MainActivity.class.getName();
    public static final String KEY_TITLE="title";
    public static final String KEY_DESCRIPTION="description";

    private EditText editTextTitle,editTextDescription;
    private TextView  textViewTitle;
    private TextView textViewDesc;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private DocumentReference noteRef=db.collection("Notebook").document("My first Note");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle=(EditText)findViewById(R.id.editTextTitle);
        editTextDescription=(EditText)findViewById(R.id.editTextDescription);
        textViewTitle=(TextView)findViewById(R.id.textViewTitle);
        textViewDesc=(TextView)findViewById(R.id.textViewDescription);


    }

    @Override
    protected void onStart() {
        super.onStart();
        noteRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e)
            {
                if(e!=null)
                {
                    Toast.makeText(MainActivity.this, "Error while loading", Toast.LENGTH_SHORT).show();
                    Log.d(Tag,e.toString());
                    return;
                }

                if(documentSnapshot.exists())
                {
                     String title=documentSnapshot.getString(KEY_TITLE);
                     String description=documentSnapshot.getString(KEY_DESCRIPTION);
                     textViewTitle.setText(title);
                     textViewDesc.setText(description);

                }
                else
                    {


                    }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void saveNote(View view)
    {
        String title=editTextTitle.getText().toString().trim();
        String description=editTextDescription.getText().toString().trim();

        Map<String,Object> note=new HashMap<>();
        note.put(KEY_TITLE,title);
        note.put(KEY_DESCRIPTION,description);

        noteRef.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(MainActivity.this, "Note  saved", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to create note", Toast.LENGTH_SHORT).show();
                Log.d(Tag,e.getMessage());
            }
        });



    }

    public void loadNote(View view)
    {
       noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot)
           {
               if(documentSnapshot.exists())
               {
                   String title=documentSnapshot.getString(KEY_TITLE);
                   String description=documentSnapshot.getString(KEY_DESCRIPTION);

                   textViewTitle.setText("Title:"+title);
                   textViewDesc.setText("Description:"+description);


               }
               else
                   {
                       Toast.makeText(MainActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                   }



           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e)
           {
               Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
               Log.d(Tag,e.getMessage());

           }
       });


    }
}