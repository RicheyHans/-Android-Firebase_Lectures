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

public class MainActivity_backup extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private EditText editMsg;
    private TextView textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 connection 역할. 생성해놓은 DB의 객체를 생성
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        initView();
    }

    // 리스너가 컨트롤
    @Override
    protected void onResume() {
        super.onResume();
        myRef.addValueEventListener(valueEventListener);
    }


    // 메인 액티비티 텍스트 뷰에 msg를 실시간으로 띄우기 위한 작업
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            textMsg.setText("");        // 항상 초기화 시켜준다.
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                String msg = (String)snapshot.getValue(String.class);        // 메시지만 보여주면 되므로 Value만 꺼낸다 / String.class는 스냅샷 전체를 객체화 시킬 수 있다는 의미
                // child가 발생할 때 마다 msg의 값을 갱신한다.
                textMsg.setText(textMsg.getText()+"\n"+msg);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        myRef.removeEventListener(valueEventListener);
    }

    public void send(View view){
        String msg = editMsg.getText().toString();
        if(msg == null || "".equals(msg)){      // key의 값에 아무것도 없으면 생성이 안되므로 임의값 세팅
            msg = "none";
        }

        String key = myRef.push().getKey();      // 유일한 node를 하나 생성해준다.

        myRef.child(key).setValue(msg);       // 위에서 생성한 키로 레퍼런스를 가져오고,
    }

    private void initView(){
        editMsg = (EditText) findViewById(R.id.editMsg);
        textMsg = (TextView) findViewById(R.id.textMsg);
    }
}
