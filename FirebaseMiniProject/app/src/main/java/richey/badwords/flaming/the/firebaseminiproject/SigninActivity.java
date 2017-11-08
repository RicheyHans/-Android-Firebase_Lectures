package richey.badwords.flaming.the.firebaseminiproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import richey.badwords.flaming.the.firebaseminiproject.Util.DialogUtil;
import richey.badwords.flaming.the.firebaseminiproject.Util.PreferenceUtil;

public class SigninActivity extends AppCompatActivity {
    ActionBar actionBar;
    ConstraintLayout layout;

    // 파이어베이스 모듈 사용
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userRef;

    EditText editEmail;
    EditText editPassword;

    ProgressBar progressBar;
    FrameLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initFirebase();
        checkAutoSignin();
    }

    private void signin(final String email, final String password){
        // 프로그레스 바 출력
        progressLayout.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fUser = auth.getCurrentUser();
                    // Preference에 유저의 값을 저장한다
                    PreferenceUtil.setValue(getBaseContext(), "user_id", email.replace(".", "_"));
                    PreferenceUtil.setValue(getBaseContext(), "email", email);
                    PreferenceUtil.setValue(getBaseContext(), "password", password);
                    PreferenceUtil.setValue(getBaseContext(), "passwrod", password);

                    // 두 번째 로그인부터는 자동 로그인 되도록 처리
                    PreferenceUtil.setValue(getBaseContext(), "auto_sign", "true");

                    // 로그인 완료 후 메인 액티비티로 이동
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progressLayout.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                DialogUtil.showDialog("오류발생:"+e.getMessage(),SigninActivity.this, false);
            }
        });
    }

    public void signin(View view){
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        signin(email, password);
    }

    public void signup(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void initView(){
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        progressLayout = findViewById(R.id.progressLayout);
    }

    private void initFirebase(){
        auth = FirebaseAuth.getInstance();
    }

    private void checkAutoSignin(){
        if(PreferenceUtil.getString(this, "auto_sign").equals("true")){
            String email = PreferenceUtil.getString(this, "email");
            String password = PreferenceUtil.getString(this, "password");
            signin(email, password);
        }else{
            database = FirebaseDatabase.getInstance();
            userRef = database.getReference("user");
        }
    }

}
