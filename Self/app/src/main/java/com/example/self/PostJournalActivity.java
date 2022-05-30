package com.example.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.self.model.Journal;
import com.example.self.util.JournalApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.Objects;

public class PostJournalActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_CODE = 1;
    private Button saveButton;
    private ProgressBar progressBar;
    private ImageButton addPhotoButton;
    private ImageView imageView;
    private EditText titleEditText;
    private EditText descEditText;
    private TextView currentUserTextView;
    private String currentUserId;
    private String currentUsername;
    private String documentID;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Journal");
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_journal);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        saveButton=findViewById(R.id.post_save_journal_button);
        progressBar=findViewById(R.id.post_progressBar);
        addPhotoButton=findViewById(R.id.postCameraButton);
        titleEditText=findViewById(R.id.post_title_editText);
        descEditText=findViewById(R.id.post_desc_editText);
        currentUserTextView=findViewById(R.id.post_user_textView);
        imageView=findViewById(R.id.imageView);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);
        saveButton.setOnClickListener(this);
        addPhotoButton.setOnClickListener(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        if(JournalApi.getInstance()!=null){
            currentUserId=JournalApi.getInstance().getUserId();
            currentUsername=JournalApi.getInstance().getUsername();
            currentUserTextView.setText(currentUsername);
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
                if (user!=null){

                }else {

                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.post_save_journal_button:
                saveJournal();
                break;
            case R.id.postCameraButton:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_CODE);
                break;
                
        }
    }

    private void saveJournal() {
        String title = titleEditText.getText().toString().trim();
        String desc = descEditText.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && imageUri!=null){
            StorageReference filePath = storageReference
                    .child("journal_images")
                    .child("my_image"+ Timestamp.now().getSeconds());

            filePath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    Journal journal = new Journal();
                                    collectionReference
                                            .add(journal)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    journal.setTitle(title);
                                                    journal.setThought(desc);
                                                    journal.setImageUrl(imageUrl);
                                                    journal.setTimeAdded(new Timestamp(new Date()));
                                                    journal.setUsername(currentUsername);
                                                    journal.setUserId(currentUserId);
                                                    journal.setDocumentId(documentReference.getId());
                                                    collectionReference.document(documentReference.getId()).set(journal);
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    startActivity(new Intent(PostJournalActivity.this,JournalListActivity.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(PostJournalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PostJournalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostJournalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            String toastMessage = "";
            String titleString = "";
            String thoughtString = "";
            String imageString = "";
            if (TextUtils.isEmpty(title)){
                titleString="Title ";
            }else if (TextUtils.isEmpty(desc)){
                thoughtString="Thought  ";
            }else if (imageUri==null){
                imageString="Image ";
            }
            progressBar.setVisibility(View.INVISIBLE);
            toastMessage=titleString+thoughtString+imageString+"cannot be empty!";
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_CODE && resultCode == RESULT_OK){
            if (data!=null){
                imageUri = data.getData();
                imageView.setImageURI(imageUri);

            }
        }
    }
}