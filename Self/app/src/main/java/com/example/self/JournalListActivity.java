package com.example.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.self.model.Journal;
import com.example.self.ui.RecyclerViewAdapter;
import com.example.self.util.DeleteJournal;
import com.example.self.util.JournalApi;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JournalListActivity extends AppCompatActivity implements DeleteJournal {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private List<Journal> journalList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    CollectionReference collectionReference = db.collection("Journal");
    private TextView emptyListText;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressBar=findViewById(R.id.progressBarList);
        storageReference = FirebaseStorage.getInstance().getReference();
        emptyListText=findViewById(R.id.thoughts_textView);
        recyclerView=findViewById(R.id.list_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        journalList = new ArrayList<>();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_action:

                if (firebaseUser!=null && firebaseAuth!=null){
                    startActivity(new Intent(JournalListActivity.this,PostJournalActivity.class));
                }

                break;
            case R.id.sign_out:
                if (firebaseUser!=null && firebaseAuth!=null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(JournalListActivity.this,MainActivity.class));
                }
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.whereEqualTo("userId", JournalApi.getInstance().getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        journalList.clear();
                        if (queryDocumentSnapshots!=null){
                            for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots){
                                Journal journal = queryDocumentSnapshot.toObject(Journal.class);
                                journalList.add(journal);
                            }
                            recyclerViewAdapter = new RecyclerViewAdapter(JournalListActivity.this,journalList, JournalListActivity.this);
                            recyclerView.setAdapter(recyclerViewAdapter);
                            recyclerViewAdapter.notifyDataSetChanged();
                        }else {
                            emptyListText.setVisibility(View.VISIBLE);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(JournalListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onLongClick(Journal journal) {

        new AlertDialog.Builder(this)
                .setTitle("Do you want to delete this journal?")
                .setMessage("This journal will be permanently deleted from your account!")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       //TODO:Delete operation
                        dialogInterface.dismiss();
                        progressBar.setVisibility(View.VISIBLE);
                        collectionReference.document(journal.getDocumentId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                                StorageReference filePath = firebaseStorage.getReferenceFromUrl(journal.getImageUrl());
                                Log.i("filepath",filePath.toString());
                                filePath.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(JournalListActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                                        journalList.remove(journal);
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(JournalListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(JournalListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();

    }
}