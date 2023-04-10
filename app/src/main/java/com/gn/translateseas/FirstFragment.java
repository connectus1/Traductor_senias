package com.gn.translateseas;

import static android.Manifest.permission.RECORD_AUDIO;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.gn.translateseas.Dialog.DialogLottieError;
import com.gn.translateseas.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Locale;

public class FirstFragment extends Fragment {

    private SpeechRecognizer speechRecognizer = null;
    private TextView textView;
    private LottieAnimationView lottie;
    private int RecordRequestCode = 1;
    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        if (ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
        }
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        createSpeech();

        binding.btnRecording.setOnTouchListener(touchRecording);
    }

    //Inicializa los componentes
    private void initComponents() {
        lottie = binding.btnRecording;
        textView = binding.textviewFirst;
    }

    private void createSpeech(){
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        speechRecognizer.setRecognitionListener(speechListener);
    }

    //Genera un intent para reconocer el audio segun el Lenguaje configurado en el movil
    private Intent speechIntent() {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        return speechIntent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        speechRecognizer.destroy();
    }

    //Revisa que los permisos para el audio esten permitidos
    private boolean checkPermission() {
        //Verifica que el permiso RECORD_AUDIO este garantizado
        if (ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            String[] permisos = new String[]{RECORD_AUDIO};
            ActivityCompat.requestPermissions(getActivity(), permisos, RecordRequestCode);
            return false;
        }

        return true;
    }

    private void changeFragment(String cadena){
        Bundle datos = new Bundle();
        datos.putString("cadena", cadena);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment,datos);
    }

    private RecognitionListener speechListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {
            textView.setText("");
            textView.setHint("Grabando...");
        }

        @Override
        public void onRmsChanged(float v) {}

        @Override
        public void onBufferReceived(byte[] bytes) {}

        @Override
        public void onEndOfSpeech() {}

        @Override
        public void onError(int i) {
            lottie.pauseAnimation();
            lottie.setProgress(0);

            textView.setText("");
            if(speechRecognizer.ERROR_SPEECH_TIMEOUT  == i){
                DialogLottieError dialogLottieError = new DialogLottieError(getContext(), R.raw.time_out);
                dialogLottieError.setMessage("Tiempo de espera de voz agotado.");
                dialogLottieError.setMessageColor(Color.BLACK);
                dialogLottieError.show();
            }

            if (speechRecognizer.ERROR_AUDIO == i){
                DialogLottieError dialogLottieError = new DialogLottieError(getContext(), R.raw.error);
                dialogLottieError.setMessage("Error de grabacion.");
                dialogLottieError.setMessageColor(Color.BLACK);
                dialogLottieError.show();
            }
        }

        @Override
        public void onResults(Bundle bundle) {
            lottie.setProgress(0);
            lottie.pauseAnimation();
            textView.setText("");

            ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            changeFragment(data.get(0));
        }

        @Override
        public void onPartialResults(Bundle bundle) {}

        @Override
        public void onEvent(int i, Bundle bundle) {
            bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener touchRecording = (view, motionEvent) -> {


        if (checkPermission()) {
            //Si el usuario esta presionando el boton de grabar
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                lottie.playAnimation();
                speechRecognizer.startListening(speechIntent());
            }

            //Si el usuario deja de presionar la grabacion
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                lottie.pauseAnimation();
                lottie.setProgress(0);

                speechRecognizer.stopListening();
            }
        }

        return false;
    };

}