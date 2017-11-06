package com.example.mcbud.firebasechat_171103.Model;

import android.widget.TextView;

import com.google.firebase.database.Exclude;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcbud on 2017-11-03.
 */

public class Room {
    public String id;
    public String title;
    public String last_msg;
    public long last_msg_time;
    public long msg_count;
    public long creation_time;
    public List<User> member;

    @Exclude
    public String getFriendString() {
        String friendString = "";
        if(member != null && member.size() > 0) {
            for (User friend : member) {
                friendString += ", " + friend.email;
            }
        }
        return friendString;
    }
}
