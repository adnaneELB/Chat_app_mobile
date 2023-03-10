package com.example.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_chat.MemoryData;
import com.example.application_chat.R;

import java.io.IOException;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyviewHolder> {
    private  List<ChatList>myChatList;
    private final Context context;

    private String userID;

    public ChatAdapter(List<ChatList> myChatList, Context context) throws IOException {
        this.myChatList = myChatList;
        this.context = context;
        this.userID= MemoryData.getData(context);
    }

    @NonNull
    @Override
    public ChatAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyviewHolder holder, int position) {
        ChatList chatList=myChatList.get(position);
        if (chatList.getId().equals(this.userID)){
            holder.sender_layout.setVisibility(View.VISIBLE);
            holder.oppo_layout.setVisibility(View.GONE);
            holder.senderMSG.setText(chatList.getMessage());
        }else {
            holder.sender_layout.setVisibility(View.GONE);
            holder.oppo_layout.setVisibility(View.VISIBLE);
            holder.oppoMSG.setText(chatList.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return myChatList.size();
    }
    public void updateAdapter(List<ChatList>myChatList){
        this.myChatList=myChatList;
    }
    static class MyviewHolder extends RecyclerView.ViewHolder {
        private LinearLayout oppo_layout,sender_layout;
        private TextView oppoMSG,senderMSG;
        private  TextView oppoTIME,senderTIME;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            oppo_layout=itemView.findViewById(R.id.oppo_laout);
            sender_layout=itemView.findViewById(R.id.sender_layout);
            oppoMSG=itemView.findViewById(R.id.oppo_text);
            senderMSG=itemView.findViewById(R.id.sender_text);
            oppoTIME=itemView.findViewById(R.id.oppo_time);
            senderTIME=itemView.findViewById(R.id.sender_time);

        }
    }
}
