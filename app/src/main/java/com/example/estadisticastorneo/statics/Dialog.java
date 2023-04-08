package com.example.estadisticastorneo.statics;

import android.content.Context;

import dmax.dialog.SpotsDialog;

public class Dialog {
    android.app.AlertDialog mDialog;
    public Dialog(Context context) {
        mDialog =new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Espere un momento")
                .setCancelable(false)
                .build();
    }
    public  void show(){
        mDialog.show();
    }
    public void dissmis(){
        mDialog.dismiss();
    }
    public android.app.AlertDialog getDialog(){
        return mDialog;
    }
}
