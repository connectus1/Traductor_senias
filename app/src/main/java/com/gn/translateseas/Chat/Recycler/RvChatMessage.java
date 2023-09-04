package com.gn.translateseas.Chat.Recycler;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.gn.translateseas.Chat.Message.MensajeChat;
import com.gn.translateseas.R;

import java.util.List;

public class RvChatMessage extends RecyclerView.Adapter<RvChatMessage.MessageViewHolder>{
    private List<MensajeChat> chatList;
    private Context context;

    public RvChatMessage(List<MensajeChat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    public void addMensaje(MensajeChat mensaje) {
        chatList.add(mensaje);
        notifyItemInserted(chatList.size());
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mensaje, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.txtMensaje.setText(chatList.get(position).getMensaje());
        String texto = chatList.get(position).getMensaje();

        if (chatList.get(position).isFlag()){
            holder.imgProfile.setVisibility(View.VISIBLE);
            holder.background.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.purple_200)));
            holder.imgFriend.setVisibility(View.INVISIBLE);
        }else{
            holder.imgFriend.setVisibility(View.VISIBLE);
            holder.background.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.purple_500)));
            holder.imgProfile.setVisibility(View.INVISIBLE);
        }

        holder.background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        DialogTraduccion dialogTraduccion = new DialogTraduccion(context, texto);
                        dialogTraduccion.show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    protected class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView txtMensaje;
        public ImageView imgProfile;
        public LottieAnimationView imgFriend;
        public CardView background;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMensaje = itemView.findViewById(R.id.txtMensaje);
            imgProfile = itemView.findViewById(R.id.imgChatFriend);
            imgFriend = itemView.findViewById(R.id.imgChatAlumno);
            background = itemView.findViewById(R.id.cardMensaje);
        }
    }

}
