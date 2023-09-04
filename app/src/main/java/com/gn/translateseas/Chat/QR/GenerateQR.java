package com.gn.translateseas.Chat.QR;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerateQR {
    private String texto;
    private Context context;

    public GenerateQR(String texto, Context context) {
        this.texto = texto;
        this.context = context;
    }

    //Generar el código QR
    public void setQR(ImageView imageView){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(texto, BarcodeFormat.QR_CODE, 512, 512);
            Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565);
            for (int x = 0; x < 512; x++) {
                for (int y = 0; y < 512; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Toast.makeText(context, "Ha Ocurrido un error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IllegalArgumentException illegalArgumentException){
            illegalArgumentException.printStackTrace();
        }
    }
}
