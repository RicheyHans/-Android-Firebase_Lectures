package com.example.mcbud.firebasechat_171103;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mcbud.firebasechat_171103.Model.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcbud on 2017-11-06.
 */

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.Holder>{
    List<Msg> data = new ArrayList<>();

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
        holder.textName.setText(msg.name);
        // 메시지를 보낸사람 id와 내 id가 같으면
        holder.msgLayout.setGravity(Gravity.RIGHT);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        Msg msg;
        LinearLayout msgLayout;
        TextView textMsg;
        TextView textName;
        public Holder(View itemView) {
            super(itemView);
            textMsg = itemView.findViewById(R.id.textMsg);
            textName = itemView.findViewById(R.id.textName);
            msgLayout = itemView.findViewById(R.id.msgLayout);
        }
    }
}
