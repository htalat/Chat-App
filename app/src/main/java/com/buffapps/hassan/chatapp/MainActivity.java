package com.buffapps.hassan.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity
{

    EditText username;
    EditText password;
    Button   signInButton;
    TextView registerButton;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;
        checkCurrentUser();
        setContentView(R.layout.activity_main);
        setup();
    }

    private void checkCurrentUser()
    {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null)
        {
            gotoHome();
        }
    }

    private void setup()
    {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.signInButton);
        registerButton = (TextView) findViewById(R.id.registerTextView);

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                login();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login()
    {
        String u = username.getText().toString();
        String p = password.getText().toString();

        ParseUser.logInInBackground( u , p , new LogInCallback()
        {
            public void done(ParseUser user, ParseException e)
            {
                if (user != null)
                {
                   gotoHome();

                } else
                {
                    Toast.makeText(mContext,"Invalid username and/or password", Toast.LENGTH_LONG)
                            .show();;
                }
            }
        });
    }


    private void gotoHome()
    {
        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
    }
}
