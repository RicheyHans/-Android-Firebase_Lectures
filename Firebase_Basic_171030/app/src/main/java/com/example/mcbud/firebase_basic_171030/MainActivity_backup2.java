package com.example.mcbud.firebase_basic_171030;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity_backup2 extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference userRef;

    private EditText editMsg;
    private TextView textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 connection 역할. 생성해놓은 DB의 객체를 생성
        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();

        writeNewUser("hans", "한승범", 33, "mcbud@naver.com" );

        // userRef = rootRef.child("users"+myId);       // 다른 레퍼런스를 가져오는 방식

        // 한 번만 동작하도록 만들어진 리스너
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // user ID는 일반 메시지 등과 다르게 직접 생성하게 된다
    private void writeNewUser(String userId, String name, int age, String email) {
        User user = new User(userId, name, age, email);

        rootRef.child("users")      // 레퍼런스를 가져오고
                .child(userId)      // 추가될 노드를 생성하고
                .setValue(user);    // 추가된 노드에 값을 입력한다.

        /*
          이런 트리 구조로 데이터가 생성되는 형태(json형태로 보면 됨)
          user
          ㄴ #han1234  { - name : 한승범
                         - age : 33
                         - email : mcbud@naver.com }
         */
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    public void send(View view){

    }

    private void initView(){
        editMsg = (EditText) findViewById(R.id.editMsg);
        textMsg = (TextView) findViewById(R.id.textMsg);
    }
}


