package com.gn.translateseas.Traductor;

import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gn.translateseas.FragmentTraduccion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class VolleyImages extends Volley implements Response.Listener<String>, Response.ErrorListener{
    private String url = "https://www.Biosur365.com/Signslate/Api-get-images.php";
    private RequestImages requestImages;
    private String token;
    private FragmentTraduccion fragment;

    public VolleyImages(@Nullable FragmentTraduccion fragment, String token) {
        this.token = token.replaceAll("\\s","%20");

        this.fragment = fragment;
        requestImages = new RequestImages(Request.Method.GET, url +"?palabra=" + this.token , this::onResponse, this::onErrorResponse);

    }

    //Genera un nuevo Request con RequestImages
    public void newRequest(){
        this.newRequestQueue(this.fragment.getActivity().getBaseContext()).add(requestImages);
    }

    @Override
    public void onErrorResponse(VolleyError error) { //En caso de error
        ((FragmentTraduccion)fragment).viewError();
        Toast.makeText(fragment.getActivity(), error.getMessage() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) { //Si regresa una respuesta
        List<Images> _array =  JsonToArray(response);
        RvTranslate rvObject = new RvTranslate(fragment.getActivity(), _array);

        ((FragmentTraduccion)fragment).setRVImagenes(rvObject);
    }

    //Convierte el JSON en un arreglo con los elementos devueltos por la API
    private List<Images> JsonToArray(String jsonString){
        List<Images> _images = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                String data = jsonArray.getString(i);
                _images.add(new Images(data, data.split("\\.")[0]));
            }
        } catch (JSONException e) {throw new RuntimeException(e);}
        return _images;
    }

    public void cancel(){
        if (requestImages != null){
            requestImages.cancel();
        }
    }

    private static class RequestImages extends StringRequest{
        public RequestImages(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        public void cancel() {
            super.cancel();
        }
    }
}
