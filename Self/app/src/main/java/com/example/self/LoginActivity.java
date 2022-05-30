package com.example.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.self.util.JournalApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private Button createAccountButton;
    private Button loginButton;
    private ProgressBar progressBar;
    private AutoCompleteTextView email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        createAccountButton=findViewById(R.id.create_account_button);
        loginButton=findViewById(R.id.email_signIn_button);
        progressBar=findViewById(R.id.login_progress);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(emailString) && !TextUtils.isEmpty(passwordString)){
            firebaseAuth.signInWithEmailAndPassword(emailString,passwordString)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                String currUserId = user.getUid();
                                collectionReference.whereEqualTo("userId",currUserId)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                if (error==null && value!=null){

                                                    for (QueryDocumentSnapshot queryDocumentSnapshot:value){
                                                        JournalApi journalApi = JournalApi.getInstance();
                                                        journalApi.setUsername(queryDocumentSnapshot.getString("username"));
                                                        journalApi.setUserId(queryDocumentSnapshot.getString("userId"));
                                                        progressBar.setVisibility(View.GONE);
                                                        startActivity(new Intent(LoginActivity.this,JournalListActivity.class));
                                                        finish();

                                                    }
                                                }else {
                                                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}