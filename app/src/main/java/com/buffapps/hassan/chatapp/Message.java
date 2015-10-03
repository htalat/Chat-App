package com.buffapps.hassan.chatapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by hassan on 9/11/15.
 */
@ParseClassName("Message")
public class Message extends ParseObject
{
    public String getUserId()
    {
        return getString("userId");
    }

    public String getBody()
    {
        return getString("body");
    }

    public void setUserId(String userId)
    {
        put("userId", userId);
    }

    public void setBody(String body)
    {
        put("body", body);
    }

    public String getName()
    {
        return  getString("name");

    }
    public void setString(String n)
    {
        put("name",n);
    }
}