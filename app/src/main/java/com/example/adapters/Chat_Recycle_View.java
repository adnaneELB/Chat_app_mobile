package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application_chat.R;
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

    holder.imageView.setImageResource(arrayList.get(position).getFoto());
    holder.textView.setText(arrayList.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            textView=itemView.findViewById(R.id.nombre);
        }
    }
}
