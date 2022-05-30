package com.example.firestoreintro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.model.Document;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TITLE_KEY = "title";
    private static final String DESCRIPTION_KEY = "description";
    private static final String TAG = "DEBUG";
    private EditText titleEditText;
    private EditText descEditText;
    private TextView titleTextView;
    private TextView descTextView;
    private Button showTextButton;
    private Button saveButton;
    private Button updateButton;
    private Button deleteButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = db.collection("Journal").document("My document");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleEditText=findViewById(R.id.title_editText);
        descEditText=findViewById(R.id.desc_editText);
        saveButton=findViewById(R.id.save_button);
        titleTextView=findViewById(R.id.title_textView);
        descTextView=findViewById(R.id.desc_textView);
        showTextButton=findViewById(R.id.showData_button);
        updateButton=findViewById(R.id.updatw_button);
        deleteButton=findViewById(R.id.delete_button);

        saveButton.setOnClickListener(view -> {

            String title = titleEditText.getText().toString().trim();
            String desc = descEditText.getText().toString().trim();

//            Map<String,Object> data = new HashMap<>();
//            data.put(TITLE_KEY,title);
//            data.put(DESCRIPTION_KEY,desc);

            Journal journal = new Journal();

            journal.setTitle(title);
            journal.setDescription(desc);

           documentReference.set(journal)
                    .addOnSuccessListener(aVoid ->  Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        });

        showTextButton.setOnClickListener((View view) -> {
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()){
                    Journal journal = documentSnapshot.toObject(Journal.class);

                    if (journal != null) {
                        titleTextView.setText(journal.getTitle());
                        descTextView.setText(journal.getDescription());
                    }

                }else {
                    Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        });

        updateButton.setOnClickListener(view -> {

            String title = titleEditText.getText().toString().trim();
            String desc = descEditText.getText().toString().trim();


            Journal journal = new Journal();

            journal.setTitle(title);
            journal.setDescription(desc);

            documentReference.update(TITLE_KEY,journal.getTitle()).addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Updated!", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            documentReference.update(DESCRIPTION_KEY,journal.getDescription()).addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Updated!", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        });

        deleteButton.setOnClickListener(view -> documentReference.delete().addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!")).addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e)));

    }

    @Override
    protected void onStart() {
        super.onStart();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null){
                    if (value != null && value.exists()) {
                        Journal journal = value.toObject(Journal.class);

                        if (journal != null) {
                            titleTextView.setText(journal.getTitle());
                            descTextView.setText(journal.getDescription());
                        }
                    }else {
                        titleTextView.setText("");
                        descTextView.setText("");
                    }
                }else {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}