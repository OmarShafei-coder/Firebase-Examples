package com.omarshafei.firebaseex1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText titleEditText;
    private EditText descriptionEditText;
    private RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    ArrayList<Note> data;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noteRef = db.collection("Notebook");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.edit_text_title);
        descriptionEditText = findViewById(R.id.edit_text_description);
        recyclerView = findViewById(R.id.recycler_view);
        data = new ArrayList<>();
        noteAdapter = new NoteAdapter(getApplicationContext(), data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //In onEvent method we can get real time updates as soon as something changes in our document
        noteRef.whereEqualTo("title", "Omar").whereEqualTo("description", "aa").limit(5)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }
                if (querySnapshot != null) {
                    data.clear();
                    for(QueryDocumentSnapshot queryDocumentSnapshot: querySnapshot) {
                        Note note = queryDocumentSnapshot.toObject(Note.class);

                        String title = note.getTitle();
                        String description = note.getDescription();
                        data.add(new Note(title, description));
                    }
                    noteAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void addNote(View view) {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Note note = new Note(title, description);
        noteRef.add(note);
    }

    public void loadNote(View view) {

        noteRef.whereEqualTo("title", "gogo").limit(3).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                data.clear();
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                    Note note = documentSnapshot.toObject(Note.class);

                    String title = note.getTitle();
                    String description = note.getDescription();
                    data.add(new Note(title, description));
                }
                noteAdapter.notifyDataSetChanged();
            }
        });
    }
}