package com.gn.translateseas.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gn.translateseas.Introduccion;
import com.gn.translateseas.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class VolleyLogin extends Volley implements Response.Listener<String>, Response.ErrorListener{
    private String url = "https://speaksign.com.mx/API/Account-User.php";
    private RequestLogin requestLogin;
    private Activity activity;
    private String correo;
    private String contra;
    public VolleyLogin(Activity activity, String correo, String contra){
        this.activity = activity;
        this.correo = correo;
        this.contra = contra;

        url += "?correo="+ correo +"&contra="+contra;
        requestLogin = new RequestLogin(Request.Method.GET, url, this::onResponse, this::onErrorResponse);
    }

    //Genera un nuevo Request con RequestLogin
    public void newRequest(){
        this.newRequestQueue(this.activity.getBaseContext()).add(requestLogin);
    }

    //Se encarga de las acciones en caso de devolver un dato la API
    @Override
    public void onResponse(String response) {
        if(response.equals("-1")){
            Toast.makeText(this.activity, "Cuenta no registrada", Toast.LENGTH_SHORT).show();
        }else{
            //Si la respuesta no es nula entonces si existe el usuario
            if(response.equals("null")){
                Toast.makeText(activity, "Correo o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                return;
            }

            //Anexamos al jsonobject los datos del correo y contra
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                jsonObject.put("correo", correo);
                jsonObject.put("contra", contra);
                guardarCuentaPreferences(jsonObject);
            }catch (Exception e){
                Log.e("LoginJson", e.getMessage());
                Toast.makeText(activity, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }

            // Revisamos si el usuario ya vio el tutorial
            if (!isViewTutorial()){ //Si es la primera vez que ve el tutorial
                Intent intentTutorial = new Intent(this.activity, Introduccion.class);
                activity.startActivity(intentTutorial);
                activity.finish();

                return;
            }

            //Iniciamos el main activity
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

    public boolean isViewTutorial(){
        SharedPreferences preferences = activity.getSharedPreferences("translate", Context.MODE_PRIVATE);
        return preferences.getBoolean("tutorial", false);
    }

    //Clase interna para los Request
    private static class RequestLogin extends StringRequest{
        public RequestLogin(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }
}
