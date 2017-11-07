package richey.badwords.flaming.the.firebaseminiproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import richey.badwords.flaming.the.firebaseminiproject.Model.User;
import richey.badwords.flaming.the.firebaseminiproject.Util.DialogUtil;
import richey.badwords.flaming.the.firebaseminiproject.Util.VerificationUtil;

public class SignupActivity extends AppCompatActivity {
    ActionBar actionBar;

    EditText editEmail;
    EditText editPassword;
    EditText editconfirmPassword;
    Button btnSignup;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userRef;

    boolean checkEmail = false;
    boolean checkPassword = false;
    boolean checkConfirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initFirebase();

    }

    // 계정생성(onclick)
    public void signup(View view){
        final String email = editEmail.getText().toString();
        final String password = editPassword.getText().toString();
        final String confirmPassword = editconfirmPassword.getText().toString();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // 1. 정상 등록 시 사용자 정보를 등록
                    FirebaseUser fUser = auth.getCurrentUser();
                    UserProfileChangeRequest.Builder profile = new UserProfileChangeRequest.Builder();
                    // 기능 확인 필요
                    profile.setDisplayName(email);
                    fUser.updateProfile(profile.build());

                    // 2. 정상 등록 시 verification 메일 발송
                    fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            DialogUtil.showDialog("Plase check your E-Mail inbox.", SignupActivity.this, true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            DialogUtil.showDialog("Error"+e.getMessage(), SignupActivity.this, false);
                        }
                    });
                    // 3. 유저 등록
                    String tempKey = email.replace(".", "_");
                    User user = new User(fUser.getUid(), email, "" );
                    userRef.child(tempKey).setValue(user);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                DialogUtil.showDialog("Error"+e.getMessage(), SignupActivity.this, false);
            }
        });
    }



    private void enableSignupButton(){
        if(checkEmail && checkPassword && checkConfirm){
            btnSignup.setEnabled(true);
        }else{
            btnSignup.setEnabled(false);
        }
    }

    private void initView(){
        btnSignup = findViewById(R.id.btnSignup);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editconfirmPassword = findViewById(R.id.editConfirmPassword);

        // 이메일 정규식 체크
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkEmail = VerificationUtil.isValidEmail(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 패스워드 정규식 체크
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPassword = VerificationUtil.isValidPassword(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 패스워드 확인 동일여부 체크
        editconfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = editPassword.getText().toString();
                checkConfirm = password.equals(charSequence.toString());
                enableSignupButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    // Firebase 모듈 사용
    private void initFirebase(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        // 데이터베이스 내 user 데이터베이스 생성
        userRef = database.getReference("user");
    }


}
