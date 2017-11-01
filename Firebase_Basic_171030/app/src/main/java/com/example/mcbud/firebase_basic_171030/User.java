package com.example.mcbud.firebase_basic_171030;

import java.util.List;

/**
 * Created by mcbud on 2017-10-30.
 */

public class User {
    public String user_id;
    public String username;
    public String email;
    public int age;

    public List<Bbs> bbs;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // 항상 디폴트 생성자가 필요함
    }

    public User(String user_id, String username, int age, String email) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.age = age;
    }

}
