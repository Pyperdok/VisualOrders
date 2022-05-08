package app.visualorders.MVP.View.OrderView.Card;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.Button;


import androidx.constraintlayout.widget.ConstraintLayout;

import app.visualorders.MVP.View.IOrderCallback;
import app.visualorders.MVP.Model.Order;
import app.visualorders.MVP.Model.OrderState;
import app.visualorders.R;

public class CardDialog extends ConstraintLayout {
    private Card _card;
    private AlertDialog _dialog;
    private Button _buttonReady;
    private Button _buttonCancel;
    private IOrderCallback _stateChanged;

    public CardDialog(Card card) {
        super(card.getContext());
        LayoutInflater inflater = LayoutInflater.from(card.getContext());
        inflater.inflate(R.layout.order_card_modal_settings, this, true);
        _card = card;
        _dialog = new AlertDialog //->
        .Builder(card.getContext())
        .setView(this)
        .create();
        Window window = _dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(R.color.transparent);

        _buttonReady = findViewById(R.id.BT_Ready);
        _buttonCancel = findViewById(R.id.BT_Cancel);
    }

    public CardDialog setOnReadyListener(OnClickListener l) {
        _buttonReady.setOnClickListener(l);
        return this;
    }
    public CardDialog setOnCancelListener(OnClickListener l) {
        _buttonCancel.setOnClickListener(l);
        return this;
    }
    public CardDialog setOnOrderChangedListener(IOrderCallback callback) {
        _stateChanged = callback;
        return this;
    }
    public void setOrderState(OrderState state) {
        Order order = _card.getOrder();
        order.setState(state);
        _stateChanged.execute(order);
        hide();
    }

    public void hide() {_dialog.hide();}
    public void show() {
        _dialog.show();
    }

}
