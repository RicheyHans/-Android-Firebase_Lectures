package richey.badwords.flaming.the.firebaseminiproject;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import richey.badwords.flaming.the.firebaseminiproject.Model.Article;
import richey.badwords.flaming.the.firebaseminiproject.Util.DialogUtil;
import richey.badwords.flaming.the.firebaseminiproject.Util.PreferenceUtil;

public class PostActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference userRef;
    String myId;

    ActionBar actionBar;
    FrameLayout progressLayout;
    ProgressBar progressBar;

    TextView textDate;
    Button btnPost;
    Button btnImg;
    EditText editSubject;
    EditText editContent;

    Calendar calendar;
    SimpleDateFormat df;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initDate();
        initFirebase();
    }

    private void initView(){
        progressLayout = findViewById(R.id.progressLayout);
        progressBar = findViewById(R.id.progressBar);

        textDate = findViewById(R.id.textDate);
        btnPost = findViewById(R.id.btnPost);
        btnImg = findViewById(R.id.btnImg);
        editSubject = findViewById(R.id.editSubject);
        editContent = findViewById(R.id.editContent);
    }

    private void initDate(){
        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(calendar.getTime());
        textDate.setText(formattedDate);
    }

    private void initFirebase(){
        myId = PreferenceUtil.getUserId(this);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
    }

    public void post(View view){
        progressLayout.setVisibility(View.VISIBLE);

        String temp = textDate.getText()+"";
        String year = temp.substring(0,4);
        String month = temp.substring(5,7);
        String day = temp.substring(8,10);
        String time = temp.substring(11,19);

        String subject = editSubject.getText().toString();
        String content = editContent.getText().toString();

        Article article = new Article(year, month, day, time, subject, content);
        userRef.child(myId).child("article").child(formattedDate+"_"+System.currentTimeMillis()).setValue(article);

        DialogUtil.showDialog("Post Completed!",PostActivity.this, true);
    }
}
