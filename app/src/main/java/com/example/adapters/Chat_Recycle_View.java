package com.example.adapters;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_chat.R;
import com.example.application_chat.Chat;
import com.example.models.Contactos;

import java.util.ArrayList;

public class Chat_Recycle_View extends RecyclerView.Adapter<Chat_Recycle_View.ViewHolder> {
    Context context;
    ArrayList<Contactos> arrayList;

    public Chat_Recycle_View(Context context, ArrayList<Contactos> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Chat_Recycle_View.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.chat_recycleview_design,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_Recycle_View.ViewHolder holder, int position) {
        //binding the components with Contactos attributes
        Contactos contact=arrayList.get(position);
        holder.imageView.setImageResource(contact.getFoto());
        holder.textView.setText(contact.getNombre());
        holder.lastmsg.setText(contact.getLastmsg());

    if (contact.getUnseenMsgs()==0){
        holder.unseen.setVisibility(View.GONE);
        holder.lastmsg.setTextColor(Color.parseColor("#959595"));
    }else {
        holder.unseen.setVisibility(View.VISIBLE);
        holder.lastmsg.setTextColor(Color.BLACK);

        holder.unseen.setText(contact.getUnseenMsgs());
    }
    holder.root.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(view.getContext(), Chat.class);
            intent.putExtra("name",contact.getNombre());
            intent.putExtra("chat_Key",contact.getChatKey());
            intent.putExtra("id",contact.getId());
            view.getContext().startActivity(intent);
        }
    });
    }

    public void updateData( ArrayList<Contactos> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView lastmsg;
        TextView unseen;
        LinearLayout root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            textView=itemView.findViewById(R.id.nombre);
            lastmsg=itemView.findViewById(R.id.lastmsg);
            unseen=itemView.findViewById(R.id.unssenMsgs);
            root=itemView.findViewById(R.id.root);

          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), Chat.class);
                    intent.putExtra("name",textView.getText());
                   // intent.putExtra("pic",imageView.getTa);

                    view.getContext().startActivity(intent);
                }
            });*/


            //long click to share the contactos details
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //share the contactos details
                    Intent intent =new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"text");
                    intent.putExtra(Intent.EXTRA_TEXT,textView.getText().toString());
                    view.getContext().startActivity(Intent.createChooser(intent,"share via"));
                    return true;
                }
            });

        }
    }
}
