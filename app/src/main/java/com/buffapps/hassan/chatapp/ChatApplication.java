package com.buffapps.hassan.chatapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by hassan on 9/10/15.
 */
public class ChatApplication extends Application
{
    public static String APPLICATION_ID = "Bu6Xd7yC9eMgNuAUcohyKC2GkHwbKD5qejrKxd8O";
    public static String CLIENT_KEY = "V0iOfChPq20KDTQTQWO7T0gezOQ2874I9PD9MxGN";


    @Override
    public void onCreate()
    {
        super.onCreate();
        ParseObject.registerSubclass(Message.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this,APPLICATION_ID,CLIENT_KEY);
    }
}
