package com.gn.translateseas.BottomSheetDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.gn.translateseas.R;
import com.gn.translateseas.login.Login;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomDialogLogin extends BottomSheetDialog {
    private Switch swRecuerda;
    private EditText txtCorreo;
    private EditText txtContra;
    private Button btnLogin;
    private Activity activity;
    public BottomDialogLogin(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;

        setContentView(R.layout.bottom_dialog_login);
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();

        //Inicializa los listener para los objetos
        btnLogin.setOnClickListener(clickLogin);
        swRecuerda.setOnCheckedChangeListener(changeRecuerda);
    }

    //Inicializa los componentes
    private void initComponents(){
        txtContra = findViewById(R.id.txtContra);
        txtCorreo = findViewById(R.id.txtCorreo);
        btnLogin = findViewById(R.id.btnLogin);
        swRecuerda = findViewById(R.id.swRecuerdame);
    }

    //Extrae la informacion de los editext de los BottomDialog y manda a llamar el metodo getLogin
    private View.OnClickListener clickLogin = view ->{
        String correo = this.txtCorreo.getEditableText().toString();
        String contra = this.txtContra.getEditableText().toString();

        ((Login) activity).getLogin(correo ,contra);
    };

    //Listener del switch para saber si guardar o no la informacion de inicio de sesion.
    private CompoundButton.OnCheckedChangeListener changeRecuerda = (compoundButton, isChecked) -> {
        ((Login)activity).setSaveAccount(isChecked);
    };

}
