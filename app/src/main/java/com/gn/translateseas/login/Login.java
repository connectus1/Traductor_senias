package com.gn.translateseas.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gn.translateseas.BottomSheetDialog.BottomDialogLogin;
import com.gn.translateseas.BottomSheetDialog.BottomDialogRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;

import com.gn.translateseas.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLoginBinding binding;
    private BottomDialogLogin bottomDialogLogin;
    private BottomDialogRegister bottomDialogRegister;
    private boolean isSaveAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnShowLogin.setOnClickListener(clickShowLogin);
        binding.txtRegistro.setOnClickListener(clickShowRegister);
    }

    //Despliega BottomSheetDialog para loguear un usuario
    private View.OnClickListener clickShowLogin = view -> {
        bottomDialogLogin = new BottomDialogLogin( this);
        bottomDialogLogin.show();

    };

    //Despliega BottomSheetDialog para registrar un nuevo usuario
    private View.OnClickListener clickShowRegister = view -> {
        bottomDialogRegister = new BottomDialogRegister(this);
        bottomDialogRegister.show();
    };

    //Se llama desde el BottomSheetDialog y ejecuta una peticion Volley para el login del usuario
    public void getLogin(String correo, String contra){
        VolleyLogin login = new VolleyLogin(this, correo,contra);
        login.newRequest();
    };

    //Se llama desde el BottomSheetDialog y ejecuta una peticion Volley
    //para registrar la informacion del usuario
    public void getRegister(String nombre, String correo, String contra){
        VolleyRegister register = new VolleyRegister(this, nombre, correo,contra);
        register.newRequest();
    }

    //Verifica si el usuario ya ha guardado datos de inicio de sesion.
    private void isLogin(){
        SharedPreferences preferences = getSharedPreferences("translate", Context.MODE_PRIVATE);
        if (preferences.getString("correo", null) != null){
            String correo = preferences.getString("correo", null);
            String contra = preferences.getString("contra",null);

            getLogin(correo,contra); //Llama al metodo GetLogin para que se inicie automaticamente
        }

    }

    public boolean isSaveAccount() {
        return isSaveAccount;
    }

    public void setSaveAccount(boolean saveAccount) {
        isSaveAccount = saveAccount;
    }

    @Override
    public void finish() {
        super.finish();

         //Revisa si algun BottomSheetDialog se encuentra abierto, en caso de ser asi los cierra
        if (bottomDialogRegister != null)
            bottomDialogRegister.dismiss();

        if (bottomDialogLogin != null)
            bottomDialogLogin.dismiss();

    }
}