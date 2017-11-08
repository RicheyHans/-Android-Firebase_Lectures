package com.example.mcbud.firebasemessage_171106;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mcbud.firebasemessage_171106.Model.Msg;
import com.example.mcbud.firebasemessage_171106.Util.PreferenceUtil;
import com.example.mcbud.firebasemessage_171106.Util.RealPathUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private StorageReference mStorageRef;

    DatabaseReference msgRef;
    DatabaseReference friendRef;

    RecyclerView msgList;
    MsgAdapter adapter;
    EditText editMsg;

    String friendId;
    String chatId;

    String myId = "";
    String myName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        friendId = intent.getStringExtra("friend_id");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        myId = PreferenceUtil.getUserId(this);
        myName = PreferenceUtil.getString(this,"name");

        // - node의 구조
        // / chat / myid@naver_com / friend@naver_com
        msgRef = database.getReference("chat");
        // / friend / 내아이디 / 친구아이디
        friendRef= database.getReference("friend").child(myId).child(friendId);

        // chat_id 가 생성되어 있지 않으면 생성
        friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("chat_id")){
                    chatId = (String) dataSnapshot.child("chat_id").getValue();
                }else{
                    chatId = msgRef.push().getKey();
                    //  나의 친구목록에 있는 친구의 아이디 밑에 채팅방 번호를 부여
                    friendRef.child("chat_id").setValue(chatId);
                    // 친구의 친구목록에 있는  나의 아이디 밑에 채팅방 번호를 부여
                    database.getReference("friend/"+friendId+"/"+myId+"/chat_id").setValue(chatId);
                }
                initView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference("root");

    }

    Msg msg = new Msg();

    public void send(View view){
        String text = editMsg.getText().toString();
        if(text != null && !"".equals(text)) {

            // 전역으로 선언된 msg 변수의 타입을 체크해서 파일 여부 확인
            if(msg.type != null && msg.FILE.equals(msg.type)){
                uploadFile(msg.localUri, msg.msg);
            }else{
                msg.msg = text;
                sendMsg();
            }


        }
    }

    private void sendMsg(){
        msg.user_id = myId;
        msg.name = myName;
        msg.time = System.currentTimeMillis()+"";

        // / chat / 채팅방번호 / 메시지번호
        // 메시지 번호 생성
        String msgKey = msgRef.child(chatId).push().getKey();
        // 메시지 데이터 입력
        msgRef.child(chatId).child(msgKey).setValue(msg);

        // 메시지창 지우기
        editMsg.setText("");
        // 메시지 변수 초기화
        msg.type = "";
        msg.url = "";
    }

    private static final int FILE_CHOOSER = 999;
    public void selectFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");      // 모든 종류의 파일 가져오기
        intent.addCategory(Intent.CATEGORY_OPENABLE);       // 열 수 있는 파일만 가져오기
        try {
            startActivityForResult(intent, FILE_CHOOSER);

        }catch(ActivityNotFoundException e){
            // 파일 선택 앱이 없을 경우 예외 처리
            Toast.makeText(this, "파일을 선택할 수 있는 앱이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case FILE_CHOOSER:
                if(resultCode == RESULT_OK){
                    Uri fileUri = data.getData();
                    String realPath = RealPathUtil.getRealPath(this, fileUri);
                    String fileName = realPath.substring(realPath.lastIndexOf('/')+1);

                    msg.type = Msg.FILE;
                    msg.localUri = fileUri;
                    msg.msg = fileName;

                    editMsg.setText(fileName);
                }
                break;

        }
    }


    private void uploadFile(Uri fileUri, String fileName){
        // 1. 파일을 저장하기 위한 노드 생성
        //  / root / file / ID / 282818379379234_fileName.txt
        StorageReference riversRef = mStorageRef.child("file/"+myId+"/"+System.currentTimeMillis()+"_"+fileName);
        // 2. 파일 저장
        riversRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        // 2. 메시지로 파일 이름을 전송(success이면 전송한다)

                        msg.url = downloadUrl.getPath();
                        sendMsg();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "오류 발생:"+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }


    private void initView(){
        editMsg = findViewById(R.id.editMsg);
        msgList = findViewById(R.id.msgList);
        adapter = new MsgAdapter(this);
        msgList.setAdapter(adapter);
        msgList.setLayoutManager(new LinearLayoutManager(this));

        msgRef.child(chatId).addValueEventListener(new ValueEventListener() {
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
