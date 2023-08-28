package com.gn.translateseas.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gn.translateseas.Introduccion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VolleyRegister extends Volley implements Response.Listener<String>, Response.ErrorListener {
    private String url = "https://speaksign.com.mx/API/Account-User.php";
    private VolleyRegister.RequestRegister requestRegister;
    private JSONObject jsonObject;
    private Activity activity;

    //Constructor
    public VolleyRegister(Activity activity,JSONObject jsonObject) {
        this.activity = activity;
        this.jsonObject = jsonObject;
        requestRegister = new RequestRegister(Request.Method.POST, this.url, this::onResponse, this::onErrorResponse){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonObject.toString().getBytes();
            }
        };
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
        if (!response.equals("ok")){
            Toast.makeText(activity, "Correo ya registrado, inicie sesion.", Toast.LENGTH_SHORT).show();
            return;
        }
        guardarCuentaPreferences(jsonObject);

        Intent intentTutorial = new Intent(this.activity, Introduccion.class);
        activity.startActivity(intentTutorial);
        activity.finish();
    }

    //Guardamos la informacion en un SharedPreferences
    public void guardarCuentaPreferences(JSONObject jsonObject){
        SharedPreferences preferences = activity.getSharedPreferences("translate", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next();
            try {
                editor.putString(key, jsonObject.getString(key));
            } catch (JSONException e) {
                Log.e("save-preferences", e.getMessage());
                throw new RuntimeException(e);
            }
        }

        editor.commit();
    }

    //Clase interna para los Request
    private static class RequestRegister extends StringRequest {
        public RequestRegister(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }
}
