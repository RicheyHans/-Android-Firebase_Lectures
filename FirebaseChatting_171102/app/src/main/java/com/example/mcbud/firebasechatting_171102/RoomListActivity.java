package com.example.mcbud.firebasechatting_171102;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

public class RoomListActivity extends AppCompatActivity {

    FrameLayout popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        popup = findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                popup.setVisibility(View.GONE);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setVisibility(View.VISIBLE);

            }
        });
    }

}
