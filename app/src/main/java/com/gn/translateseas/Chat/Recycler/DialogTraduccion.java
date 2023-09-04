package com.gn.translateseas.Chat.Recycler;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gn.translateseas.Traductor.RvTranslate;

import com.gn.translateseas.databinding.DialogTraduccionBinding;

public class DialogTraduccion extends AlertDialog{
    private DialogTraduccionBinding binding;
    private String texto;
    private VolleyImages images;
    protected DialogTraduccion(@NonNull Context context, String texto) {
        super(context);
        binding = DialogTraduccionBinding.inflate(getLayoutInflater());
        setView(binding.getRoot());

        this.texto = texto;


        //Si ocurre un error durante la obtencion de la cadena en los argumentos omite el Volley
        try{

            images = new VolleyImages(this, texto);
            images.newRequest();
        }catch (Exception e){
            Toast.makeText(getContext(), "Ha Ocurrido un error", Toast.LENGTH_SHORT).show();
        }


    }

    public void setRVImagenes(RvTranslate rvObject){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);


        binding.rvImagenes.setAdapter(rvObject);
        binding.rvImagenes.setLayoutManager(linearLayoutManager);

    }
}
