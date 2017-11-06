package com.example.mcbud.firebasechat_171103;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.mcbud.firebasechat_171103.Model.Msg;
import com.example.mcbud.firebasechat_171103.Model.User;
import com.example.mcbud.firebasechat_171103.Util.DialogUtil;
import com.example.mcbud.firebasechat_171103.Util.PreferenceUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference roomRef;

    Toolbar toolbar;
    private RelativeLayout popup;
    private EditText editEmail;
    private Button btnAdd;
    private EditText editMsg;
    private RecyclerView msgList;
    private MsgListAdapter adapter;

    String room_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Intent intent = getIntent();
        room_id = intent.getStringExtra("room_id");

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        roomRef = database.getReference("chatting_room").child(room_id);
        initView();
    }

    public void send(View view){
        String msgText = editMsg.getText().toString();
        String msgKey = roomRef.child("msg").push().getKey();

        Msg msg = new Msg();
        msg.id = msgKey;
        msg.name = PreferenceUtil.getString(this,"name");
        msg.user_id = PreferenceUtil.getString(this,"user_id");
        msg.msg = msgText;

        roomRef.child("msg").child(msgKey).setValue(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.room_sub_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add_friend:
                popup.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_exit:

                break;
        }
        return true;
    }

    private void initView() {
        popup = (RelativeLayout) findViewById(R.id.popup);
        editEmail = (EditText) findViewById(R.id.editEmail);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setVisibility(View.GONE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().replace(".","_");
                userRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() >0 ){
                            User friend = dataSnapshot.getValue(User.class);
                            roomRef.child(room_id)
                                    .child("member")
                                    .child(friend.email.replace(".","_"))
                                    .setValue(friend);
                        }else{
                            DialogUtil.showDialog("검색결과가 없습니다", RoomActivity.this, false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        editMsg = findViewById(R.id.editMsg);
        msgList = findViewById(R.id.msgList);
        adapter = new MsgListAdapter();
        msgList.setAdapter(adapter);
        msgList.setLayoutManager(new LinearLayoutManager(this));

        roomRef.child("msg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
