package com.example.a77011_40_08.afpahotellerie.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by 77011-40-05 on 21/03/2018.
 */

public class GenericAlertDialog {

    public GenericAlertDialog(Activity activity, String title, String message, View view, final CallGenericAlertDialog callback) {


        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(activity);

        if (message.isEmpty() && view != null) {
            alertDialogBuilder.setView(view);
        } else {
            alertDialogBuilder.setMessage(message);
        }

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false); //Mode modal

        //int id = Functions.getResourceId(context,xmlRessource, "layout");
        //View view = LayoutInflater.from(context).inflate(id,null);

        alertDialogBuilder.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }

        });

        alertDialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                callback.validate();
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public interface CallGenericAlertDialog {
        void validate();
    }


}
