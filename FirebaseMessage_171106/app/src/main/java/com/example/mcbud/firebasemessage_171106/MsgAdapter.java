package com.example.mcbud.firebasemessage_171106;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mcbud.firebasemessage_171106.Model.Msg;
import com.example.mcbud.firebasemessage_171106.Model.User;
import com.example.mcbud.firebasemessage_171106.Util.PreferenceUtil;

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
        }else{
            holder.itemLayout.setGravity(Gravity.LEFT);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        Msg msg;
        LinearLayout itemLayout;    // 내 메시지, 친구 메시지 정렬
        TextView textMsg;
        public Holder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            textMsg = itemView.findViewById(R.id.textMsg);
        }
    }
}

