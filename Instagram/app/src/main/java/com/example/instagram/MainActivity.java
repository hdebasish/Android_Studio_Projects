package com.example.instagram;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView,imageView2;
    EditText username,username2;
    EditText password,password2,confirmPassword;
    Button button,button2;
    TextView textView,textView3;
    TextView textView2,textView4;
    RelativeLayout login;
    ScrollView home,signUp;

    public void startUsers(){
        Intent intent = new Intent(getApplicationContext(),Users.class);
        startActivity(intent);
    }

    public void signUp(View view){

        imageView.setVisibility(View.INVISIBLE);
        username.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        signUp.setVisibility(View.VISIBLE);
        signUp.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        username2.addTextChangedListener(signUpButtonTextWatcher);
        password2.addTextChangedListener(signUpButtonTextWatcher);
        confirmPassword.addTextChangedListener(signUpButtonTextWatcher);

    }

    public void logIn(View view){
        imageView.setVisibility(View.VISIBLE);
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.INVISIBLE);
    }

    public void logInButtonTapped(View view){

        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null){
                    Log.i("Login","Successful");
                    startUsers();
                }
                else {
                    Log.i("Login","Failed"+e.toString());
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void signUpButtonTapped(View view){
        ParseUser user = new ParseUser();
        user.setUsername(username2.getText().toString());
        user.setPassword(password2.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.i("Sign Up","Successful");
                    startUsers();
                }else {
                    Log.i("Sign Up","Failed");
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        imageView2=findViewById(R.id.imageView2);
        username2=findViewById(R.id.username2);
        password2=findViewById(R.id.password2);
        button2=findViewById(R.id.button2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        signUp=findViewById(R.id.signUp);
        login=findViewById(R.id.login);
        confirmPassword=findViewById(R.id.confirmPassword2);
        home=findViewById(R.id.home);
        home.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        username.addTextChangedListener(logInButtonTextWatcher);
        password.addTextChangedListener(logInButtonTextWatcher);
        if(ParseUser.getCurrentUser()!=null)
        {
            startUsers();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {

            Rect r = new Rect();
            home.getWindowVisibleDisplayFrame(r);
            int screenHeight = home.getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;
            if (keypadHeight > screenHeight * 0.15) {
                bottomOfScreen();
            }
        }
    };


    public void bottomOfScreen(){

            home.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    home.scrollTo(0,home.getBottom());
                }

            });

    }

     TextWatcher logInButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(user.length()>=4&&pass.length()>=8){
                    button.setEnabled(true);
                }else {
                    button.setEnabled(false);
                }

                if (button.isEnabled()){
                    button.setBackgroundColor(getResources().getColor(R.color.blue));
                }else {
                    button.setBackgroundColor(getResources().getColor(R.color.whiteBlue));
                }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher signUpButtonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String user = username2.getText().toString().trim();
            String pass = password2.getText().toString().trim();
            String cnfpass = confirmPassword.getText().toString().trim();
            if(user.length()>=4&&pass.length()>=8&&pass.equals(cnfpass)){
                button2.setEnabled(true);
            }else {
                button2.setEnabled(false);
            }

            if (button2.isEnabled()){
                button2.setBackgroundColor(getResources().getColor(R.color.blue));
            }else {
                button2.setBackgroundColor(getResources().getColor(R.color.whiteBlue));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
