package com.example.twitter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MyTweets extends AppCompatActivity {

    ListView myTweetListView;
    ArrayList<String> mytweets;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tweets);
        setTitle("My Tweets");
        myTweetListView=findViewById(R.id.myTweetListView);
        mytweets= new ArrayList<>();
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mytweets);
        myTweetListView.setAdapter(adapter);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Tweet");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null && objects.size()>0){
                    for (ParseObject object:objects){
                        mytweets.add(object.getString("tweet"));
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });

        myTweetListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MyTweets.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Do you want to delete this tweet?")
                        .setMessage("This tweet will be deleted.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ParseQuery<ParseObject> deleteTweet = new ParseQuery<ParseObject>("Tweet");
                                deleteTweet.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
                                deleteTweet.whereEqualTo("tweet",mytweets.get(position));
                                deleteTweet.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {

                                        if (e==null && objects.size()>0) {
                                            for (ParseObject object : objects) {
                                                object.deleteInBackground();
                                                mytweets.remove(position);
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();



                return true;
            }

        });

        myTweetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(MyTweets.this)
                        .setIcon(R.drawable.ic_person_black_24dp)
                        .setTitle("Your Tweet")
                        .setMessage(mytweets.get(position))
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
}
