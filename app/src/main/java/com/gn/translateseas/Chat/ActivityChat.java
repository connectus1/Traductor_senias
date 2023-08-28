package com.gn.translateseas.Chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.gn.translateseas.R;
import com.gn.translateseas.databinding.ActivityChatBinding;

public class ActivityChat extends AppCompatActivity {
    private SharedPreferences preferences;
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initComponents();
        if (isLogin()){
            Log.e("Chat", "not login");
        }else{
            Log.e("Chat", "login");
        }

    }

    // Inicializamos los componentes
    private void initComponents(){
        preferences = getSharedPreferences("speaksign-message", Context.MODE_PRIVATE);
    }


    // Revisamos si el usuario ya ha iniciado sesion con anterioridad
    private boolean isLogin(){
        String usuario = preferences.getString("usuario", "");
        String contra = preferences.getString("contra","");

        return (usuario.isEmpty() && contra.isEmpty());
    }


}