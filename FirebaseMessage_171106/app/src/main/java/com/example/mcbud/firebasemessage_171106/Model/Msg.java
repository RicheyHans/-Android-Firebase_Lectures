package com.example.mcbud.firebasemessage_171106.Model;

import android.net.Uri;

import com.google.firebase.database.Exclude;

/**
 * Created by mcbud on 2017-11-06.
 */

public class Msg {

    @Exclude
    public static final String FILE = "file";
    @Exclude
    public static final String TEXT = "text";
    @Exclude
    public Uri localUri;


    public String id;
    public String msg;
    public String user_id;
    public String name;
    public String time;

    public String type; // 전송 타입(파일, 텍스트 구분)
    public String url;

}

