package com.example.mcbud.firebase_basic2_171031;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // firebase 인증 모듈 사용을 위한 전역 변수
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;


    private EditText editEmail;
    private EditText editPassword;

    private EditText signEmail;
    private EditText signPassword;

    private TextView infoEmail;
    private TextView infoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 인증 관련 인스턴스 확보
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        signEmail = findViewById(R.id.signEmail);
        signPassword = findViewById(R.id.signPassword);

        infoEmail = findViewById(R.id.infoEmail);
        infoPassword = findViewById(R.id.infoPassword);


    }

    // 로그인 된 유저가 있으면 firebase에서 가져와서 화면에 반영한다(Update UI는 직접 처리)
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    // 사용자를 등록(signup)
    public void signup(View view){

        // @하나, . 하나가 필수, 영문, 숫자, _ , /
        String email = editEmail.getText().toString();
        // 특수문자 한 개 이상, !, @, #, $, %, ^, &, *
        // 영문, 숫자
        String password = editPassword.getText().toString();

        // validation check
        // 정규 표현식(textview를 하이드 시켜놓고, 잘못되면 visible했다가 에디트 클릭 시 없앰)

        if(!isValidEmail(email)){
            infoEmail.setText("이메일 형식이 잘못됐습니다.");
            infoEmail.setVisibility(View.VISIBLE);
            return;
        }

        if(!isValidPassword(password)){
            infoPassword.setText("비밀번호 형식이 잘못됐습니다.");
            infoPassword.setVisibility(View.VISIBLE);
            return;
        }


        // firebase의 인증 모듈로 사용자를 생성하는 함수
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { // 완료 확인 리스너
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    /*
                     사용자 등록이 확인된 후, 다음 액티비티로 이동시킨다.
                     */
                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(MainActivity.this, "이메일 발송 완료", Toast.LENGTH_SHORT).show();
                                }
                            });
                    // 내 디바이스에 맞는 토큰 생성
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    Log.d("MSG", "token="+refreshedToken);
                    User data = new User(user.getUid(), refreshedToken, user.getEmail());

                    // 데이터베이스에 사용자 정보 추가
                    userRef.child(user.getUid()).setValue(data);

                } else {
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
                        // 첫 문자열, . 두 번째 문자열  \\w영문 전체

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public static boolean isValidPassword(String password) {
        boolean err = false;
        // 영문자와 숫자만 허용하는 형태
        // String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        String regex = "^[a-zA-z0-9]{8,}$";    // 여기에 *이 없으면 딱 한 문자만 가능하다. 현재는 공백도 되고 복수도 된다.
        // 첫 문자열, . 두 번째 문자열  \\w영문 전체

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    // 사용자 로그인 처리(signin)
    public void signin(View view){
        String email = signEmail.getText().toString();
        String password = signPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // 이메일 검증 확인
                        if(user.isEmailVerified()){
                            // 다음 페이지로 이동
                            Intent intent = new Intent(MainActivity.this,StorageActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this
                                    ,"이메일을 확인하셔야 합니다"
                                    , Toast.LENGTH_SHORT ).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("FB",e.getMessage());
                }
            });
    }


    // 로그인 되었을 때 사용 가능
    public void getUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // 이메일 검증 완료 여부 확인(인증메일 발송 후 확인 버튼 눌러야 true)
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }
}
