package com.gn.translateseas.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.gn.translateseas.Chat.Message.MensajeChat;
import com.gn.translateseas.Chat.Recycler.RvChatMessage;
import com.gn.translateseas.SQLite.Database;
import com.gn.translateseas.databinding.ActivityChatMessageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ChatMessage extends AppCompatActivity {
    private ActivityChatMessageBinding binding;
    private SQLiteDatabase sqLiteDatabase;
    private List<MensajeChat> mensajeList;
    private RvChatMessage rvChatMessage;
    private DatabaseReference firebaseDatabase;
    private String remitente;
    private String id_usuario;
    private String destinatario;
    private int id_destinatario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();
        binding.btnEnviar.setOnClickListener(clickSend);
    }

    private void initComponents() {
        remitente = getSharedPreferences("translate", Context.MODE_PRIVATE).getString("correo", "");
        id_usuario = getSharedPreferences("translate", Context.MODE_PRIVATE).getString("id_usuario", "");

        destinatario = this.getIntent().getExtras().getString("correo");
        id_destinatario = this.getIntent().getExtras().getInt("id_destinatario");


        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Chat/" + id_usuario);
        firebaseDatabase.addChildEventListener(mensajesListener);

        Database database = new Database(this);
        sqLiteDatabase = database.getWritableDatabase();

        mensajeList = new ArrayList<>();
        getMessageDB();

        binding.btnEnviar.setImageTintList(null);

        rvChatMessage = new RvChatMessage(mensajeList, this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvChatMessage.registerAdapterDataObserver(adapterDataObserver);

        binding.rvChat.setLayoutManager(l);
        binding.rvChat.setAdapter(rvChatMessage);
        binding.rvChat.scrollToPosition(rvChatMessage.getItemCount()-1);

        binding.tittleChat.setText(this.getIntent().getExtras().getString("usuario","Speaksign"));
    }

    private void getMessageDB(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM speaksign_friend_" + id_destinatario +" ORDER BY id_mensaje ASC",null);
        if (cursor.moveToFirst()){
            do {
                String mensaje = cursor.getString(1);
                String fecha = cursor.getString(2);
                boolean flag = (cursor.getInt(3) == 1);

                mensajeList.add(new MensajeChat(mensaje,fecha,flag));
            }while (cursor.moveToNext());
        }
    }

    private View.OnClickListener clickSend = view -> {
        String message = binding.edtxtMensaje.getText().toString();

        if (!message.trim().isEmpty()){
            Mensaje mensaje = new Mensaje(id_destinatario, destinatario, remitente, getFecha("dd-mm-YYYY"), message);

            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                    .getReference("Chat/" + id_destinatario)
                    .push()
                    .setValue(mensaje).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            sqLiteDatabase.execSQL("INSERT INTO speaksign_friend_" + id_destinatario +"(mensaje,fecha,flag) " +
                                    "VALUES('" + mensaje.getMensaje() + "','" + mensaje.getFecha() + "',0)");
                            rvChatMessage.addMensaje(new MensajeChat(mensaje.getMensaje(), mensaje.getFecha(), false));
                            binding.edtxtMensaje.setText("");
                        }
                    });
        }
    };


    @SuppressLint("SimpleDateFormat")
    private static String getFecha(String formato) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;

        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        return sdf.format(date);
    }

    private ChildEventListener mensajesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Mensaje mensaje = dataSnapshot.getValue(Mensaje.class);
            if (!mensaje.getRemitente().equals(remitente)){
                rvChatMessage.addMensaje(new MensajeChat(mensaje.getMensaje(), mensaje.getFecha(), true));
                sqLiteDatabase.execSQL("INSERT INTO speaksign_friend_"+ id_destinatario +"(mensaje,fecha,flag) " +
                        "VALUES('" + mensaje.getMensaje() + "','" + mensaje.getFecha() + "',1)");

                firebaseDatabase.child(dataSnapshot.getKey()).removeValue();
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            binding.rvChat.scrollToPosition(rvChatMessage.getItemCount() -1 );
        }
    };

}