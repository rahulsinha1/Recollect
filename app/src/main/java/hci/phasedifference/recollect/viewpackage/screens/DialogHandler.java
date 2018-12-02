package hci.phasedifference.recollect.viewpackage.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogHandler {

    ConfirmDialogListener listener;
    Context applicationContext;

    public DialogHandler(Context applicationContext, ConfirmDialogListener listener) {
        this.applicationContext = applicationContext;
        this.listener = listener;

    }

    public void show(String message, String title, int reqid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogHandler.this.listener.confirmDialogAction(reqid, true);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogHandler.this.listener.confirmDialogAction(reqid, false);
            }
        });

        builder.show();
    }

    public void showOkDialog(String message, String title, int reqid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogHandler.this.listener.confirmDialogAction(reqid, true);
            }
        });
        builder.show();
    }

}
