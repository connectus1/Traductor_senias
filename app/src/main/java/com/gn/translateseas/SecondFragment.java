package com.gn.translateseas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gn.translateseas.RvImagenes.Images;
import com.gn.translateseas.RvImagenes.RvTranslate;
import com.gn.translateseas.databinding.FragmentSecondBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    //private RequestQueue queue = Volley.newRequestQueue(getContext());
    private String url = "https://www.Biosur365.com/Signslate/Api-get-images.php";
    private StringRequest request;
    private RecyclerView rvImagenes;
    private LottieAnimationView lottieTemp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();

        //Si ocurre un error durante la obtencion de la cadena en los argumentos omite el Volley
        try{
            String palabra = getArguments().getString("cadena");
            getImages(palabra);
        }catch (Exception e){
            Toast.makeText(getContext(), "Ha Ocurrido un error", Toast.LENGTH_SHORT).show();
        }

    }

    //Inicializa los componentes
    private void initComponents(){
        this.rvImagenes = binding.rvImagenes;
        this.lottieTemp = binding.lottieTemp;
    }

    //Genera un StringRequest haciendo una peticion GET  a la API con los datos recolectados
    public void getImages(String token){
        token = token.replaceAll("\\s","%20");
        request = new StringRequest(Request.Method.GET, url +"?palabra=" + token,
                response -> {
                    List<Images> _array =  JsonToArray(response);
                    RvTranslate rvObject = new RvTranslate(getContext(), _array);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

                    rvImagenes.setAdapter(rvObject);
                    rvImagenes.setLayoutManager(linearLayoutManager);

                }, error -> {

                    viewError();
                    Toast.makeText(getContext(), error.getMessage() , Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(getContext()).add(request);
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

    //Muestra el elemento lottie indicando que no hay internet
    private void viewError(){
        lottieTemp.playAnimation();
        lottieTemp.setVisibility(View.VISIBLE);
        rvImagenes.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (request != null){
            request.cancel();
        }

        binding = null;
    }

}