package com.gn.translateseas.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import com.airbnb.lottie.LottieDrawable;

public class DialogLottieError extends com.amrdeveloper.lottiedialog.LottieDialog  implements DialogInterface.OnShowListener, DialogInterface.OnDismissListener, DialogInterface.OnCancelListener {
    public DialogLottieError(Context context, int raw_res) {
        super(context);
        this.setAnimation(raw_res);

        initOptions();
    }

    private void initOptions(){
        this.setAutoPlayAnimation(true);
        this.setAnimationRepeatCount(LottieDrawable.INFINITE);
        this.setCancelable(true);
        this.setDialogBackground(Color.WHITE);

        this.setOnShowListener(this::onShow);
        this.setOnDismissListener(this::onDismiss);
        this.setOnCancelListener(this::onCancel);
    }

    //======================
    //Listener para los eventos de Dimiss, Show y Cancel
    //======================
    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    @Override
    public void onShow(DialogInterface dialogInterface) {

    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {

    }
}
