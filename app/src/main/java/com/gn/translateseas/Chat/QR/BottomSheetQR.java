package com.gn.translateseas.Chat.QR;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.gn.translateseas.databinding.BottomDialogQrBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetQR extends BottomSheetDialog {
    private BottomDialogQrBinding binding;

    public BottomSheetQR(@NonNull Context context) {
        super(context);

        binding =BottomDialogQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GenerateQR generateQR = new GenerateQR(getCorreo(),context);
        generateQR.setQR(binding.imgQR);
    }

    private String getCorreo(){
        SharedPreferences preferences = getContext().getSharedPreferences("translate", Context.MODE_PRIVATE);
        String correo = preferences.getString("correo","");
        String contra = preferences.getString("contra","sha1");

        //String encriptado = AesUtil.encrypt(contra,correo);

        return correo;
    }
}
