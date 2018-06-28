package com.ferfig.xyzreader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.ferfig.xyzreader.data.UpdaterService;
import com.ferfig.xyzreader.ui.SnackBarAction;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Utils {

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected()) {
            return false;
        }
        return true;
    }

    public static void showSnackBar(View container, String message, String action, int duration, final SnackBarAction mCallback){

        Snackbar snackbar = Snackbar
                .make(container, message, duration)
                .setAction(action, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCallback.onPerformSnackBarAction();
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public static void StartIntentService(Context context) {
        context.startService(new Intent(context, UpdaterService.class));
    }
}
