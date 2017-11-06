package richey.badwords.flaming.the.firebaseminiproject;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {
    ActionBar actionBar;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userRef;

    EditText editEmail;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void signin(final String email, final String password){

    }

    public void signin(View view){

    }

    public void signup(View view){
        
    }

    private void initView(){
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }
}
