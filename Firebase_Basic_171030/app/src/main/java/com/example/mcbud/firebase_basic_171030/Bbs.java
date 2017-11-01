package com.example.mcbud.firebase_basic_171030;

/**
 * Created by mcbud on 2017-10-30.
 */

public class Bbs {

    public String id;       // 노드, key값으로 쓰인다
    public String title;
    public String date;
    public String content;
    public String user_id;

    public Bbs(){
        // Firebase에서 parsing할 때 한 번 호출한다.
    }

    public Bbs(String title, String content, String date, String user_id){
        this.title = title;
        this.content = content;
        this.date = date;
        this.user_id = user_id;
    }

}
