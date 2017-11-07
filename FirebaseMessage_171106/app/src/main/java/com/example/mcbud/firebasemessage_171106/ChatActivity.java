package com.example.mcbud.firebasemessage_171106;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.mcbud.firebasemessage_171106.Model.Msg;
import com.example.mcbud.firebasemessage_171106.Util.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference msgRef;

    RecyclerView msgList;
    EditText editMsg;
    MsgAdapter adapter;

    String myId = "";
    String myName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String friendId = intent.getStringExtra("friend_id");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        myId = PreferenceUtil.getUserId(this);
        myName = PreferenceUtil.getString(this, "name");

        // node 구조
        // / chat / myid@naver_com / friend@naver_com / msg_key(자동 생성)
        msgRef = database.getReference("chat/"+myId+"/"+friendId);

        initView();

    }

    public void send(View view){

        String text = editMsg.getText().toString();
        if(text != null && !"".equals(text)){

            // 메시지 key 생성
            String msgKey = msgRef.push().getKey();
            Msg msg = new Msg();
            msg.user_id = myId;
            msg.msg = editMsg.getText().toString();
            msg.time = System.currentTimeMillis()+"";
            msg.name = myName;

            // 메시지를 db에 입력
            msgRef.child(msgKey).setValue(msg);
        }

    }

    private void initView(){
        editMsg = findViewById(R.id.editMsg);
        msgList = findViewById(R.id.msgList);
        adapter = new MsgAdapter(this);
        msgList.setAdapter(adapter);
        msgList.setLayoutManager(new LinearLayoutManager(this));

        msgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 바뀔때마다 메시지 데이터 생성, 출력
                List<Msg> data = new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Msg msg = item.getValue(Msg.class);
                    data.add(msg);
                }
                adapter.setDataAndRefresh(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
