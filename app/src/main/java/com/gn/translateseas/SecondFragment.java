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
import com.gn.translateseas.Traductor.RvTranslate;
import com.gn.translateseas.Traductor.VolleyImages;
import com.gn.translateseas.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private RecyclerView rvImagenes;
    private LottieAnimationView lottieTemp;
    private VolleyImages images;
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
            images = new VolleyImages(this, palabra);
            images.newRequest();

        }catch (Exception e){
            Toast.makeText(getContext(), "Ha Ocurrido un error", Toast.LENGTH_SHORT).show();
        }

    }

    //Inicializa los componentes
    private void initComponents(){
        this.rvImagenes = binding.rvImagenes;
        this.lottieTemp = binding.lottieTemp;
    }

    public void setRVImagenes(RvTranslate rvObject){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        rvImagenes.setAdapter(rvObject);
        rvImagenes.setLayoutManager(linearLayoutManager);

    }

    //Muestra el elemento lottie indicando que no hay internet
    public void viewError(){
        lottieTemp.playAnimation();
        lottieTemp.setVisibility(View.VISIBLE);
        rvImagenes.setVisibility(View.INVISIBLE);
    }

    //Finaliza el fragment
    private View.OnClickListener clickReturn = View ->{
        onDetach();
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        images.cancel();
        binding = null;
    }


}