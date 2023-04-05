package com.gn.translateseas.login;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gn.translateseas.MainActivity;

public class VolleyLogin extends Volley implements Response.Listener<String>, Response.ErrorListener{
    private String url = "https://www.biosur365.com/Signslate/API/login.php";
    private RequestLogin requestLogin;
    private Activity activity;

    //Constructor
    public VolleyLogin(Activity activity, String correo, String contra){
        this.activity = activity;
        url += "?correo=" + correo + "&contra=" + contra;

        requestLogin = new RequestLogin(Request.Method.GET, url, this::onResponse, this::onErrorResponse);
    }

    //Genera un nuevo Request con RequestLogin
    public void newRequest(){
        this.newRequestQueue(this.activity.getBaseContext()).add(requestLogin);
    }

    //Se encarga de las acciones en caso de devolver un dato la API
    @Override
    public void onResponse(String response) {
        if (response.equals("0")){
            Toast.makeText(this.activity, "Correo o contraseña no coinciden", Toast.LENGTH_SHORT).show();
        }else if (response.equals("1")){
            Intent intent = new Intent(this.activity, MainActivity.class);
            this.activity.startActivity(intent);
            activity.finish();
        }
    }

    //En caso de error para ejecutar el Request lanza un mensaje
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this.activity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }


    //Clase interna para los Request
    private static class RequestLogin extends StringRequest{
        public RequestLogin(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }
}
