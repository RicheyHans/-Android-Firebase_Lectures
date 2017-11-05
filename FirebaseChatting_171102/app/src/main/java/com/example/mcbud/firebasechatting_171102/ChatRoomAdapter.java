package com.example.mcbud.firebasechatting_171102;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mcbud on 2017-11-02.
 */

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.Holder> {

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
