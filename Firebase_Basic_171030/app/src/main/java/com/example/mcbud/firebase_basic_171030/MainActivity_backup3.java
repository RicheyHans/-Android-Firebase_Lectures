package com.example.mcbud.firebase_basic_171030;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/*
public class MainActivity_backup3 extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference bbsRef;
    DatabaseReference userRef;

    private EditText editId;
    private EditText editName;
    private Button btnSignUp;
    private RecyclerView userList;
    private EditText editTitle;
    private RecyclerView bbsList;

    private UserAdapter userAdapter;
    private BbsAdapter bbsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");
        userRef = database.getReference("user");

        initView();
        initListener();
    }

    // 사용자 등록을 위한 메서드(on Click 연계)
    public void signUp(View view){
        String id = editId.getText().toString();
        String name = editName.getText().toString();

        User user = new User(id, name, 17, "none");
        userRef.child(id).setValue(user);
    }

    public String user_id = "";

    private void initView(){
        editId = (EditText) findViewById(R.id.editId);
        editName = (EditText) findViewById(R.id.editName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        editTitle = (EditText) findViewById(R.id.editTitle);


        userList = (RecyclerView) findViewById(R.id.userList);
        bbsList = (RecyclerView) findViewById(R.id.bbsList);


        userAdapter = new UserAdapter();
        userList.setAdapter(userAdapter);
        userList.setLayoutManager(new LinearLayoutManager(this));

        bbsAdapter = new BbsAdapter();
        bbsList.setAdapter(bbsAdapter);
        bbsList.setLayoutManager(new LinearLayoutManager(this));


    }

    private void initListener(){
        // user라는 테이블에 어댑터를 달고, 여기에 변동사항이 생기면 전체 내용이 스냅샷에 들어가고
        // for문에서는 hans 한 사람의 데이터가 dataSnapshot.getChildren()으로 들어가고
        // 그 hans 한 사람의 데이터 age, email, name은 getValue로 추출된다.

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {   // user테이블의 전체
                List<User> data = new ArrayList<>();    // 아답터에 입력할 데이터를 정의

                // for문의 snapshot은 hans 한사람이고, getValue는 age, email, name
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    data.add(user);
                }
                userAdapter.setData(data);
            }

            // data를 아답터에 반영하고, 아답터를 notify해준다.


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
*/