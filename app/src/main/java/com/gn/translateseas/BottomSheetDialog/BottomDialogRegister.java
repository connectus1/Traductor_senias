package com.gn.translateseas.BottomSheetDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gn.translateseas.R;
import com.gn.translateseas.login.ActivityLogin;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomDialogRegister extends BottomSheetDialog {
    private EditText txtUsuario;
    private EditText txtCorreo;
    private EditText txtContra;
    private EditText txtTelefono;
    private Spinner spinnerLada;
    private Button btnRegister;
    private Activity activity;

    public BottomDialogRegister(@NonNull Activity activity) {
        super(activity);

        this.activity = activity;
        setContentView(R.layout.bottom_dialog_register);
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

        btnRegister.setOnClickListener(clickRegister);

    }
    //Inicializa todos los componentes
    private void initComponents(){
        txtUsuario = findViewById(R.id.txtUsuario);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);
        txtTelefono = findViewById(R.id.txtTelefono);

        spinnerLada = findViewById(R.id.spinnerLada);
        String[] _lada = getContext().getResources().getStringArray(R.array.array_lada);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, _lada);
        spinnerLada.setAdapter(adapter);

        btnRegister = findViewById(R.id.btnRegister);
    }

    private View.OnClickListener clickRegister = view -> {
        //Obtiene los tectos correspondientes
        String usuario = this.txtUsuario.getEditableText().toString();
        String correo = txtCorreo.getEditableText().toString();
        String contra = this.txtContra.getEditableText().toString();

        String telefono = this.txtTelefono.getEditableText().toString();
        String lada = this.spinnerLada.getSelectedItem().toString();

        //Limpia los textos ingresados
        if (correo.trim().equals("") || contra.trim().equals("") || usuario.toLowerCase().trim().equals("") || telefono.toLowerCase().trim().equals("")){
            Toast.makeText(activity, "Algun dato sin especificar", Toast.LENGTH_SHORT).show();
            return;
        }

        telefono = lada + telefono;
        //Llama al metodo de la clase principal
        ((ActivityLogin) activity).getRegister(usuario, correo ,contra, telefono);
    };

}
