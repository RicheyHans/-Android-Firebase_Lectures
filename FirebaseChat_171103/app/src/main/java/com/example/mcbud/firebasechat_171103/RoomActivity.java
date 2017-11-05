package com.example.mcbud.firebasechat_171103;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.mcbud.firebasechat_171103.Util.DialogUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference userRef;

    Toolbar toolbar;
    private RelativeLayout popup;
    private EditText editEmail;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = ;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.room_sub_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_add_friend:
                popup.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_exit:

                break;
        }
        return true;
    }

    private void initView(){
        btnAdd = findViewById(R.id.btnAdd);
        popup = (RelativeLayout) findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setVisibility(View.GONE);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                auth.fetchProvidersForEmail(email).addOnSuccessListener(new OnSuccessListener<ProviderQueryResult>() {
                    @Override
                    public void onSuccess(ProviderQueryResult providerQueryResult) {
                       if(providerQueryResult.getProviders().size() > 0){
                            for(String item : providerQueryResult.getProviders(){
                                String email = item;
                                Log.d("AddFriend", "email"+email);
                           }
                        }else{
                           DialogUtil.showDialog("검색 결과가 없습니다.", RoomActivity.this, );
                       }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DialogUtil.showDialog("오류발생"+e.getMessage(), RoomActivity.this, );
                    }
                });
            }
        });
    }
}
