package com.example.mcbud.firebasemessage_171106;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mcbud.firebasemessage_171106.Model.Msg;
import com.example.mcbud.firebasemessage_171106.Model.User;
import com.example.mcbud.firebasemessage_171106.Util.PreferenceUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcbud on 2017-11-06.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.Holder> {
    private List<Msg> data = new ArrayList<>();
    private Context context;


    public MsgAdapter(Context context){
        this.context = context;
    }

    public void setDataAndRefresh(List<Msg> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_msg_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Msg msg = data.get(position);
        holder.msg = msg;
        holder.textMsg.setText(msg.msg);

        // 메시지 작성자 id가 내 자신과 동일하면 메시지 레이아웃을 오른쪽 정렬(default 왼쪽)
        if(msg.user_id.equals(PreferenceUtil.getUserId(context))){
            holder.itemLayout.setGravity(Gravity.RIGHT);
            holder.itemLayout.setBackgroundColor(Color.BLUE);
        }else{
            holder.itemLayout.setGravity(Gravity.LEFT);
            holder.itemLayout.setBackgroundColor(Color.WHITE);
        }
        // 메시지 타입이 파일이면 다운로드 링크를 걸어준다
        if(msg.type != null && Msg.FILE.equals(msg.type)){
            holder.btnDownload.setVisibility(View.VISIBLE);
        }else{
            holder.btnDownload.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        Msg msg;
        LinearLayout itemLayout;    // 내 메시지, 친구 메시지 정렬
        Button btnDownload;
        TextView textMsg;
        public Holder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            btnDownload = itemView.findViewById(R.id.btnDownload);
            textMsg = itemView.findViewById(R.id.textMsg);

            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fileDownload();
                }
            });
        }

        private void fileDownload(){
            try {
                String downloadDirectory = Environment.getExternalStorageDirectory().toString()
                        + File.separator + Environment.DIRECTORY_DOWNLOADS;

                File localFile = new File(downloadDirectory+"/"+msg.msg);

                StorageReference fileRef = FirebaseStorage.getInstance()
                        .getReferenceFromUrl("https://firebasestorage.googleapis.com"+msg.url);

                fileRef.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Successfully downloaded data to local file
                                Toast.makeText(context, "Download Complete!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failed download
                        e.printStackTrace();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

