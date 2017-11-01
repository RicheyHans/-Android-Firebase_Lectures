package com.example.mcbud.firebase_basic_171030;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcbud on 2017-10-30.
 */

// 리사이클러 뷰 아답터 구현
// 1. 데이터 구조(User 클래스)
// 2. 아이템 레이아웃
// 3. Holder
// 4. 아답터 구현


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder>{

    List<User> data = new ArrayList<>();
    Callback callback;

    public UserAdapter(Callback callback){
        this.callback = callback;
    }

    public void setDataAndRefresh(List<User> data){
        this.data = data;
        Log.d("Adapter","data========="+data.size());
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User user = data.get(position);
        Log.d("Adapter","userid========="+user.user_id);
        holder.textId.setText(user.user_id);
        holder.textName.setText(user.username);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // 리사이클러 뷰 아답터 규현
    // 1. 데이터 구조 = User.class
    // 2. 아이템 레이아웃
    // 3. Holder
    // 4. 아답터 구현

    public class Holder extends RecyclerView.ViewHolder{
        TextView textId;
        TextView textName;
        public Holder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textName = itemView.findViewById(R.id.textName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = textId.getText().toString();
                    callback.setUserid(user_id);
                    Toast.makeText(v.getContext(), "userid="+user_id,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface Callback{
        public void setUserid(String userid);
    }
}
