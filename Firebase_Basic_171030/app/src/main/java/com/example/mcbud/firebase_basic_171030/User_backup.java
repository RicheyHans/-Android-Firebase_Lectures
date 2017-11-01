package com.example.mcbud.firebase_basic_171030;

/**
 * Created by mcbud on 2017-10-30.
 */

public class User_backup {
    public String username;
    public String email;
    public int age;

    public User_backup() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // 항상 디폴트 생성자가 필요함
    }

    public User_backup(String username, int age, String email) {
        this.username = username;
        this.email = email;
        this.age = age;
    }

}
