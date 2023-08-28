package com.gn.translateseas.Chat;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyMensaje  extends Volley implements Response.Listener<String>, Response.ErrorListener{
    private String url = "https://www.Biosur365.com/Signslate/Api-get-images.php;"; //<- Cambiar la API
    private RequestMessage requestMessage;
    private Context context;

    public VolleyMensaje(Context context) {
        this.context = context;
        requestMessage = new RequestMessage(Request.Method.POST, url, this::onResponse, this::onErrorResponse);
    }

    public void newRequest(){
        this.newRequestQueue(context).add(requestMessage);
    }

    public void cancel(){
        if (requestMessage != null){
            requestMessage.cancel();
            requestMessage = null;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }

    private static class RequestMessage extends StringRequest{

        public RequestMessage(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        public void cancel() {
            super.cancel();
        }
    }
}
