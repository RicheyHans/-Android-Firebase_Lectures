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
    // firebase 내 article 생성 용도
    FirebaseDatabase database;
    DatabaseReference userRef;
    String myId;

    // post 버튼 실행 시 article 내 날짜 데이터 변수
    String temp, year, month, day, time;
    String subject;
    String content;

    // 화면 레이아웃, 프로그레스 바
    ActionBar actionBar;
    FrameLayout progressLayout;
    ProgressBar progressBar;

    // 텍스트, 버튼
    TextView textDate;
    Button btnPost;
    Button btnImg;
    EditText editSubject;
    EditText editContent;

    // postActivity 상단 날짜, 시간 출력
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

        getArticle();
        DialogUtil.showDialog("Post Completed!",PostActivity.this, true);
    }

    private void getArticle(){
        /*
           Subject, Content 공백 체크
         */
        temp = textDate.getText()+"";
        year = temp.substring(0,4);
        month = temp.substring(5,7);
        day = temp.substring(8,10);
        time = temp.substring(11,19);

        subject = editSubject.getText().toString();
        content = editContent.getText().toString();

        Article article = new Article(year, month, day, time, subject, content);
        userRef.child(myId).child("article").child(formattedDate+"_"+System.currentTimeMillis()).setValue(article);
    }
}
