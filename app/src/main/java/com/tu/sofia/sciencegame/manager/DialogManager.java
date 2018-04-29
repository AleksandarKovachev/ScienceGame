package com.tu.sofia.sciencegame.manager;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;

/**
 * Created by Aleksandar Kovachev on 29.04.2018 г..
 */
public class DialogManager {

    public static void showAlertDialog(Context context, String title, String message, String buttonText) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonText, (dialog, which) -> dialog.dismiss()).show();
    }

}
