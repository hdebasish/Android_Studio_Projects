package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    EditText message;
    ListView chatListView;
    String receiver;
    ParseObject chat;
    List<HashMap<String,String>> list;
    SimpleAdapter simpleAdapter;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message=findViewById(R.id.message);
        chatListView=findViewById(R.id.chatListView);
        Bundle bundle = getIntent().getExtras();
        receiver = bundle.getString("receiver");
        chat = new ParseObject("Chat");
        refreshList();




    }

    public void send(View view){
        chat = new ParseObject("Chat");
        chat.put("message",message.getText().toString());
        chat.put("sender", ParseUser.getCurrentUser().getUsername());
        chat.put("chatUsers", Arrays.asList(ParseUser.getCurrentUser().getUsername(),receiver));
        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.i("Save In Background","Successful");
                }else {
                    Log.i("Save In Background","Error: "+e.toString());
                }
            }
        });
        refreshList();
        message.setText("");
    }


    public void refreshList(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chat");
       query.whereContainsAll("chatUsers", Arrays.asList(ParseUser.getCurrentUser().getUsername(),receiver));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    list=new ArrayList<>();
                    for(ParseObject object:objects){
                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("username", object.getString("sender"));
                        map.put("message",object.getString("message"));
                        list.add(map);
                    }
                    simpleAdapter = new SimpleAdapter(getApplicationContext(),list,android.R.layout.simple_list_item_2,new String[]{"username","message"},new int[]{android.R.id.text1,android.R.id.text2});
                    chatListView.setAdapter(simpleAdapter);
                }
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshList();
            }
        },5000);

    }
}