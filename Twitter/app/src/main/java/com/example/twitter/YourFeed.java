package com.example.twitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YourFeed extends AppCompatActivity {

    ListView listView;
    List<HashMap<String,String>> list;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_feed);
        listView=findViewById(R.id.listViewFeed);

        setTitle(ParseUser.getCurrentUser().getUsername()+"'s Feed");

       /* List<HashMap<String,String>> list = new ArrayList<>();

        for(int i=0; i<10;i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("username", "Item "+i);
            map.put("content","Sub Item "+i);
            list.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,new String[]{"username","content"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(simpleAdapter);
*/
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        if (ParseUser.getCurrentUser().getList("isFollowing")!=null){
            query.whereContainedIn("username",ParseUser.getCurrentUser().getList("isFollowing"));
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null && objects.size()>0){
                        list = new ArrayList<>();
                        for (ParseObject object:objects){
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("username", object.getString("username"));
                            map.put("content",object.getString("tweet"));
                            list.add(map);
                        }
                        simpleAdapter = new SimpleAdapter(getApplicationContext(), list, android.R.layout.simple_list_item_2,new String[]{"username","content"},new int[]{android.R.id.text1,android.R.id.text2});
                        listView.setAdapter(simpleAdapter);
                    }
                }
            });
        }else {
            Toast.makeText(this, "No feeds to show, please follow someone.", Toast.LENGTH_LONG).show();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(YourFeed.this)
                        .setIcon(R.drawable.ic_person_black_24dp)
                        .setTitle(list.get(position).get("username"))
                        .setMessage(list.get(position).get("content"))
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.feed_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.logout) {
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.tweet){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText tweetEditText = new EditText(this);
            builder.setTitle("Write a Tweet")
                    .setView(tweetEditText)
                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ParseObject tweet = new ParseObject("Tweet");
                            tweet.put("username",ParseUser.getCurrentUser().getUsername());
                            tweet.put("tweet",tweetEditText.getText().toString());
                            tweet.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e==null){
                                        Toast.makeText(YourFeed.this, "Tweet sent successfully!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(YourFeed.this, "Tweet failed - Try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();

        }else if(item.getItemId()==R.id.myTweet){
            Intent intent = new Intent(getApplicationContext(),MyTweets.class);
            startActivity(intent);
        }else if (item.getItemId()==R.id.follow){
            Intent intent = new Intent(getApplicationContext(),UserList.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);


    }

}
