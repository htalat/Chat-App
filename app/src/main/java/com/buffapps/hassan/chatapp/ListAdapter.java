package com.buffapps.hassan.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;

/**
 * Created by hassan on 9/11/15.
 */
public class ListAdapter extends ArrayAdapter<Message>
{
    String mUserId;

    public ListAdapter(Context context, String userId, List<Message> messages)
    {
        super(context, 0, messages);
        this.mUserId = userId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
         {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.chat_name);
            holder.body = (TextView)convertView.findViewById(R.id.chat_body);
            convertView.setTag(holder);
        }
        final Message message = (Message)getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        holder.name.setText(message.getName());
        holder.body.setText(message.getBody());
        return convertView;
    }

    final class ViewHolder
    {
        public TextView name;
        public TextView body;
    }

}
