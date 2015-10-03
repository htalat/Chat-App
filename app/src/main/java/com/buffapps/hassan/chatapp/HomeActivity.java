package com.buffapps.hassan.chatapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String USER_ID_KEY = "userId";
    public String mUserId;
    Context mContext;
    EditText messageToSend;
    Button sendButton;
    ListView messagesListView;
    ArrayList<Message> mMessages;
    ListAdapter mAdapter;
    boolean mFirstLoad;
    TextView logOut;


    private Handler handler = new Handler();

    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = HomeActivity.this;
        if(ParseUser.getCurrentUser() != null)
        {
            setUp();
        }else
        {
            login();
        }

        handler.postDelayed(runnable, 100);

    }

    private void setUp()
    {
        mUserId = ParseUser.getCurrentUser().getObjectId();
        messageToSend = (EditText) findViewById(R.id.messageToSend);
        messagesListView = (ListView) findViewById(R.id.messagesListView);
        mMessages = new ArrayList<Message>();
        mFirstLoad = true;
        mAdapter = new ListAdapter(HomeActivity.this, mUserId, mMessages);
        messagesListView.setAdapter(mAdapter);

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String data = messageToSend.getText().toString();
                String firstName = ParseUser.getCurrentUser().getString("firstName");
                ParseObject message = ParseObject.create("Message");
                message.put(USER_ID_KEY,mUserId);
                message.put("body",data);
                message.put("name",ParseUser.getCurrentUser().get("firstName"));
                message.saveInBackground(new SaveCallback()
                {
                    @Override
                    public void done(ParseException e)
                    {
                      //  Toast.makeText(mContext, "Successfully created message on Parse",
                     //           Toast.LENGTH_SHORT).show();
                    }
                });

                messageToSend.setText("");

            }
        });
        logOut = (TextView) findViewById(R.id.logoutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });

    }

    private void receiveMessage()
    {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>()
        {
            public void done(List<Message> messages, ParseException e)
            {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        messagesListView.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }
    // Defines a runnable which is run every 100ms
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };
    private void refreshMessages() {
        receiveMessage();
    }

    private void login()
    {
        startActivity(new Intent(mContext,MainActivity.class));
    }


}
