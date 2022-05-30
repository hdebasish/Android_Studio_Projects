package com.example.facebooklogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.facebook.ParseFacebookUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collection<String> permissions = Arrays.asList("public_profile","email");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(MainActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e!=null){
                            ParseUser.logOut();
                            Log.e("error","Error",e);
                        }if (user==null){
                            ParseUser.logOut();
                            Toast.makeText(MainActivity.this, "The user cancelled the Facebook Login", Toast.LENGTH_SHORT).show();
                        }else if (user.isNew()){
                            Toast.makeText(MainActivity.this, "User signed up and Logged in through Facebook", Toast.LENGTH_SHORT).show();
                            getUserDetailFromFB();
                        }else {
                            Toast.makeText(MainActivity.this, "User logged in through Facebook", Toast.LENGTH_SHORT).show();
                            getUserDetailFromParse();
                        }
                    }
                });

            }
        });
    }

    private void getUserDetailFromParse() {
        ParseUser user = ParseUser.getCurrentUser();
        String title = "Welcome Back";
        String message = "User: " + user.getUsername() + "\n" + "Login Email: " + user.getEmail();
        alertDisplayer(title,message);
    }

    private void alertDisplayer(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                        Intent intent = new Intent(MainActivity.this,LogoutActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }


    private void getUserDetailFromFB() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                ParseUser user = ParseUser.getCurrentUser();
                try {
                    user.setUsername(object.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    user.setEmail(object.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        alertDisplayer("First time Login","Welcome!");
                    }
                });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode,resultCode,data);
    }
}
