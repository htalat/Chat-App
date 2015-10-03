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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity
{
    EditText firstName, lastName, email, password, confirmPassword;
    Button registerButton;
    TextView signInButton;
    ParseUser mParseUser;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = RegisterActivity.this;

        setup();



    }

    private void setup()
    {
        firstName = (EditText) findViewById(R.id.firstName);
        lastName  = (EditText) findViewById(R.id.lastName);
        email     = (EditText) findViewById(R.id.email);
        password  = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        signInButton = (TextView) findViewById(R.id.signInTextView);
        registerButton = (Button) findViewById(R.id.registerButton);

        mParseUser = new ParseUser();

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(!confirmPassword.getText().toString().equals(password.getText().toString()))
                {
                    Toast.makeText(mContext,"Passwords do not match!",Toast.LENGTH_LONG).show();
                    return;

                }else
                {
                    mParseUser.setEmail(email.getText().toString());
                    mParseUser.setUsername(email.getText().toString());
                    mParseUser.setPassword(password.getText().toString());
                    mParseUser.put("firstName", firstName.getText().toString());
                    mParseUser.put("lastName", lastName.getText().toString());

                    registration();
                }
            }
        });
    }

    private void registration()
    {
        mParseUser.signUpInBackground(new SignUpCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    firstName.setText(""); lastName.setText("");
                    email.setText("");password.setText(""); confirmPassword.setText("");

                    Intent intent = new Intent(mContext,MainActivity.class);
                    startActivity(intent);

                }else
                {
                    Toast.makeText(mContext,"Error Occurred! Try again!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }


}
