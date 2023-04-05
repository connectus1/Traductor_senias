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

public class VolleyRegister extends Volley implements Response.Listener<String>, Response.ErrorListener {
    private String url = "https://www.biosur365.com/Signslate/API/registro.php";
    private VolleyRegister.RequestRegister requestRegister;
    private Activity activity;

    //Constructor
    public VolleyRegister(Activity activity,String nombre, String correo, String contra) {
        this.activity = activity;
        this.url += "?nombre=" + nombre + "&correo="+ correo + "&contra=" + contra;

        requestRegister = new RequestRegister(Request.Method.GET, this.url,this::onResponse, this::onErrorResponse);
    }

    //Genera un nuevo Request con RequestRegister
    public void newRequest(){
        this.newRequestQueue(this.activity.getBaseContext()).add(requestRegister);
    }

    //En caso de error al ejecutar el Request lanza un mensaje
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    //Se encarga de las acciones en caso de devolver un dato la API
    @Override
    public void onResponse(String response) {
        if (response.equals("-1")){
            Toast.makeText(activity, "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
        } else if (response.equals("0")) {
            Toast.makeText(activity, "Ya existe un correo registrado", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);

            activity.finish();
        }
    }

    //Clase interna para los Request
    private static class RequestRegister extends StringRequest {
        public RequestRegister(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }
}
