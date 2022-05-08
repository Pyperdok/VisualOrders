package app.visualorders.MVP.View;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import app.visualorders.R;

public class ProgressBar extends FrameLayout {
    private AlertDialog _dialog;
    public ProgressBar(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.progressbar, this, true);
        _dialog = new AlertDialog //->
        .Builder(context)
        .setView(this)
        .setCancelable(false)
        .create();
        _dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    public void show() {
        _dialog.show();
    }
    public void hide() {
        _dialog.hide();
    }
}
