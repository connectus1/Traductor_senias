package com.gn.translateseas.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gn.translateseas.BottomSheetDialog.BottomDialogLogin;
import com.gn.translateseas.BottomSheetDialog.BottomDialogRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.gn.translateseas.R;
import com.gn.translateseas.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityLogin extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private BottomDialogLogin bottomDialogLogin;
    private BottomDialogRegister bottomDialogRegister;
    private boolean isSaveAccount = false;

    private void changeTheme(){
        //Tiempo de espera de 2 segundos para cambiar el Theme de la aplicacion
        try {
            setTheme(R.style.Theme_TranslateSeñas_NoActionBar);
            isLogin();

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        changeTheme(); //Para cambiar el tema actual
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnShowLogin.setOnClickListener(clickShowLogin);
        binding.txtRegistro.setOnClickListener(clickShowRegister);

    }

    //Despliega BottomSheetDialog para loguear un usuario
    private final View.OnClickListener clickShowLogin = view -> {
        bottomDialogLogin = new BottomDialogLogin( this);
        bottomDialogLogin.show();
    };

    //Despliega BottomSheetDialog para registrar un nuevo usuario
    private final View.OnClickListener clickShowRegister = view -> {
        bottomDialogRegister = new BottomDialogRegister(this);
        bottomDialogRegister.show();
    };

    //Se llama desde el BottomSheetDialog y ejecuta una peticion Volley para el login del usuario
    public void getLogin(String correo, String contra){
        VolleyLogin login = new VolleyLogin(this, correo, contra);

        login.newRequest();
    }

    //Se llama desde el BottomSheetDialog y ejecuta una peticion Volley
    //para registrar la informacion del usuario
    public void getRegister(String usuario, String correo, String contra,String telefono){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("usuario", usuario);
            jsonObject.put("correo", correo);
            jsonObject.put("contra", contra);
            jsonObject.put("telefono", telefono);
        } catch (JSONException e) {
            Log.e("registro-error", e.getMessage());
            throw new RuntimeException(e);
        }

        VolleyRegister register = new VolleyRegister(this, jsonObject);
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