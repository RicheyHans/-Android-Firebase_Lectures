package com.example.mcbud.firebasemessage_171106;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mcbud.firebasemessage_171106.Model.User;
import com.example.mcbud.firebasemessage_171106.Util.PreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout addLayout;
    EditText editEmail;
    ProgressBar progressBar;
    RecyclerView friendList;
    FriendAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference friendRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        friendRef = database.getReference("friend");
        friendRef.child(PreferenceUtil.getUserId(getBaseContext())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> data =new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    User friend = item.getValue(User.class);
                    data.add(friend);
                }
                adapter.setDataAndRefresh(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        initView();
    }
    // 이메일로 친구 검색하기
    public void addFriend(View view){
        progressBar.setVisibility(View.VISIBLE);
        String email = editEmail.getText().toString().replace(".","_");
        // 이메일에 해당하는 노드가 있는지 검사
        if(email != null && !"".equals(email)){
            userRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // node 가 있으면
                    if(dataSnapshot.getChildrenCount() > 0){
                        String key = dataSnapshot.getKey();
                        User user = dataSnapshot.getValue(User.class);
                        friendRef.child(PreferenceUtil.getUserId(getBaseContext()))
                                .child(key).setValue(user);
                    }else{

                    }
                    progressBar.setVisibility(View.GONE);
                    addLayout.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    // 옵션메뉴 사용하기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // R.menu.menu.xml 을 옵션메뉴로 사용
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_add_friend:
                addLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_test:
                Toast.makeText(this,"menu clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void initView(){
        addLayout = findViewById(R.id.addLayout);
        editEmail = findViewById(R.id.editEmail);
        progressBar = findViewById(R.id.progressBar);

        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLayout.setVisibility(View.GONE);
            }
        });

        friendList = findViewById(R.id.friendList);
        adapter = new FriendAdapter();
        friendList.setAdapter(adapter);
        friendList.setLayoutManager(new LinearLayoutManager(this));
    }
}

