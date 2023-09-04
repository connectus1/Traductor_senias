package com.gn.translateseas.Chat.AddFriend;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyRequestProfile extends Volley implements Response.Listener<String>, Response.ErrorListener{
    private String url = "https://speaksign.com.mx/API/Contacts-User.php";
    private RequestProfile requestProfile;
    private Activity activity;
    private String correo;

    public VolleyRequestProfile(String correo, Activity activity) {
        this.correo = correo;
        this.activity = activity;

        this.url += "?correo=" + correo;
        Log.e("urlRequest",this.url );
        requestProfile = new RequestProfile(Request.Method.GET, url, this::onResponse, this::onErrorResponse);
    }

    public void newRequest(){
        this.newRequestQueue(this.activity.getBaseContext()).add(requestProfile);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        if (response.equals("-1") || response.equals("null")){
            return;
        }

        try {
            Log.e("response", response);
            JSONObject jsonObject = new JSONObject(response);

            //creamos el objeto friend que contendra los datos
            Amigo amigo = new Amigo();
            amigo.setUsuario(jsonObject.getString("usuario"));
            amigo.setCorreo(correo);
            amigo.setId(jsonObject.getInt("id_usuario"));

            //Creamos y mostramos el dialogo del usuario escaneado
            DialogAddFriend dialog = new DialogAddFriend(activity, amigo);
            dialog.show();

            requestProfile.cancel();
        } catch (JSONException e) {
            Log.e("RequestProfile", "Error" + e.getMessage());
        }
    }


    private static class RequestProfile extends StringRequest{
        public RequestProfile(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
    }
}
