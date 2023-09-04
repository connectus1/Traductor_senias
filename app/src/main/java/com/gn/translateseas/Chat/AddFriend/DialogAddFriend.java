package com.gn.translateseas.Chat.AddFriend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.gn.translateseas.SQLite.Database;
import com.gn.translateseas.databinding.DialogAddProfileFriendBinding;

public class DialogAddFriend extends AlertDialog {
    private DialogAddProfileFriendBinding binding;
    private Amigo amigo;
    protected DialogAddFriend(@NonNull Context context, Amigo amigo) {
        super(context);
        this.amigo = amigo;

        binding = DialogAddProfileFriendBinding.inflate(getLayoutInflater());

        this.setView(binding.getRoot());
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initComponents();
    }

    // Inicializamos los componentes
    private void initComponents(){
        binding.textView2.setText(amigo.getUsuario());
        binding.btnAceptar.setOnClickListener(clickAccept);
        binding.btnRechazar.setOnClickListener(clickIgnore);
    }

    //Agregar un usuario como amigo
    private View.OnClickListener clickAccept = view -> {
        Database database = new Database(view.getContext());
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        if (database != null){
            System.out.println(amigo.getId());

            String insert_friend = "INSERT INTO friends(id, correo, usuario) VALUES(" + amigo.getId()  + ",'" +amigo.getCorreo() + "','" + amigo.getUsuario() + "')";
            sqLiteDatabase.execSQL(insert_friend);

            database.createTableMessage(sqLiteDatabase, String.valueOf(amigo.getId()));

            this.cancel();
        }
    };

    // Rechazamos el agregar el nuevo usuario
    private View.OnClickListener clickIgnore = view -> {
        this.cancel();
    };


}
