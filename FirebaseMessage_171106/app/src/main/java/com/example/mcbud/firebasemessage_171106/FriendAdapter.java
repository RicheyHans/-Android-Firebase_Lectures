package com.example.mcbud.firebasemessage_171106;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mcbud.firebasemessage_171106.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcbud on 2017-11-06.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.Holder> {
    private List<User> data = new ArrayList<>();

    public void setDataAndRefresh(List<User> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User friend = data.get(position);
        holder.friend = friend;
        holder.textName.setText(friend.name);
        holder.textEmail.setText(friend.email);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        User friend;
        TextView textName;
        TextView textEmail;
        public Holder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
                    // 채팅할 친구의 아이디를 전달한다(authenticication 에 저장된 UID?)
                    intent.putExtra("friend_id", friend.id);
                    intent.putExtra("chat_id", friend.chat_id);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

