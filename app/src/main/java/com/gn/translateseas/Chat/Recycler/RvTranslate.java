package com.gn.translateseas.Chat.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gn.translateseas.R;
import com.gn.translateseas.Traductor.Images;

import java.util.List;

public class RvTranslate extends RecyclerView.Adapter<RvTranslate.ViewHolder> {
    private List<Images> _array;
    private Context context;

    public RvTranslate(Context context, List<Images> _array) {
        this.context = context;
        this._array = _array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            RequestOptions options = new RequestOptions().fitCenter();
            Glide.with(context).load(_array.get(position).getUrl()).apply(options).into(holder.img);
            holder.txt.setText(_array.get(position).getPlace());

        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public int getItemCount() {
        return _array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.imgTranslate);
            this.txt = itemView.findViewById(R.id.txtTranslate);
        }
    }
}
