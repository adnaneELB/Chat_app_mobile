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
import com.example.models.Llamadas;

import java.util.ArrayList;

public class Calls_Card_View  extends RecyclerView.Adapter<Calls_Card_View.ViewHolder> {
    Context context;
    ArrayList<Llamadas> arrayList;

    public Calls_Card_View(Context context, ArrayList<Llamadas> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Calls_Card_View.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.calls_cardview,parent,false);
        return  new Calls_Card_View.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Calls_Card_View.ViewHolder holder, int position) {

        holder.profile_image.setImageResource(arrayList.get(position).getFoto());
        holder.call_image.setImageResource(arrayList.get(position).getCallfoto());
        holder.textView.setText(arrayList.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image;
        ImageView call_image;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image=itemView.findViewById(R.id.card_img);
            call_image=itemView.findViewById(R.id.card_img_call);
            textView=itemView.findViewById(R.id.card_nombre);
        }
    }
}
