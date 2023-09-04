package com.gn.translateseas.Chat.Recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gn.translateseas.Chat.AddFriend.Amigo;
import com.gn.translateseas.Chat.ChatMessage;
import com.gn.translateseas.R;

import java.util.List;

public class RvAmigosAdapter extends RecyclerView.Adapter<RvAmigosAdapter.AmigosViewHolder> {
    private List<Amigo> amigoList;
    private Context context;
    public RvAmigosAdapter(List<Amigo> amigoList, Context context) {
        this.amigoList = amigoList;
        this.context = context;
    }

    @NonNull
    @Override
    public AmigosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_usuario, parent, false);
        return new AmigosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmigosViewHolder holder,int position) {
        holder.txtUserFriend.setText(amigoList.get(position).getUsuario());
        holder.imgProfileFriend.setImageDrawable(context.getDrawable(R.drawable.icon_user));

        holder.txtUserFriend.setOnClickListener(view -> {
            Intent i = new Intent(context, ChatMessage.class);
            i.putExtra("correo", amigoList.get(position).getCorreo());
            i.putExtra("usuario", amigoList.get(position).getUsuario());
            i.putExtra("id_destinatario", amigoList.get(position).getId());
            context.startActivity(i);
        });

        holder.imgProfileFriend.setOnClickListener(view -> {
            Intent i = new Intent(context, ChatMessage.class);
            i.putExtra("correo", amigoList.get(position).getCorreo());
            i.putExtra("usuario", amigoList.get(position).getUsuario());
            i.putExtra("id_destinatario", amigoList.get(position).getId());

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return amigoList.size();
    }

    protected static class AmigosViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgProfileFriend;
        public TextView txtUserFriend;

        public AmigosViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfileFriend = itemView.findViewById(R.id.imgProfileFriend);
            txtUserFriend = itemView.findViewById(R.id.txtUserFriend);
        }
    }

}
