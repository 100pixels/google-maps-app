package com.example.ivansandoval.googlemaps.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.ivansandoval.googlemaps.R;

public class NetworkStatusDialog extends DialogFragment{

    private NetworkStatusDialogListener networkStatusDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Log.d("STATE","En \"onCreateDialog\"");
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.network_state_dialog_title);
            builder.setPositiveButton(R.string.network_state_dialog_positive_button, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                    Log.d("STATE","Seleccionaste \"Conectarse a una red\"");
                                networkStatusDialogListener.onDialogPositiveClick(NetworkStatusDialog.this);
                            }
                        });
            builder.setNegativeButton(R.string.network_state_dialog_negative_button, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                Log.d("STATE","Seleccionaste \"Cancelar\"");
                                networkStatusDialogListener.onDialogNegativeClick(NetworkStatusDialog.this);
                            }
                        });
            builder.setCancelable(false);
                return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            networkStatusDialogListener = (NetworkStatusDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface NetworkStatusDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

}
