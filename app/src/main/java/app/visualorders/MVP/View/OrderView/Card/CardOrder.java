package app.visualorders.MVP.View.OrderView.Card;

import android.widget.TextView;
import java.text.SimpleDateFormat;

import app.visualorders.MVP.Model.OrderState;
import app.visualorders.R;

public class CardOrder {
    private SimpleDateFormat _date;
    private TextView _tvId;
    private TextView _tvDateTime;
    private TextView _tvCashier;
    private TextView _tvState;

    public CardOrder(Card card) {
        _date = new SimpleDateFormat("dd.MM.yyyy\nHH:mm");
        _tvId = (TextView)card.findViewById(R.id.TV_OrderID);
        _tvDateTime = (TextView)card.findViewById(R.id.TV_OrderDateTime);
        _tvCashier = (TextView)card.findViewById(R.id.TV_CashierName);
        _tvState = card.findViewById(R.id.TV_OrderState);
    }

    public CardOrder setId(int orderId) {
        _tvId.setText(String.valueOf(orderId));
        return this;
    }
    public CardOrder setCashier(String cashier) {
        _tvCashier.setText(cashier);
        return this;
    }
    public CardOrder setDateTime(int dateTime) {
        String date = _date.format(dateTime);
        _tvDateTime.setText(date);
        return this;
    }
    public CardOrder setState(OrderState state) {
        _tvState.setBackgroundResource(state.getColorId());
        _tvState.setText(state.getName());
        return this;
    }
}
