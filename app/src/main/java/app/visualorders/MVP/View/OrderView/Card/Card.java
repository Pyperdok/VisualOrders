package app.visualorders.MVP.View.OrderView.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import app.visualorders.MVP.View.IOrderCallback;
import app.visualorders.MVP.Model.Order;
import app.visualorders.MVP.Model.OrderState;
import app.visualorders.MVP.Model.Product;
import app.visualorders.R;
import app.visualorders.MVP.View.IOrderView;

public class Card extends ConstraintLayout implements IOrderView {
    private Order _order;
    private CardDialog _dialog;
    private CardOrder _card;

    public Card(Order order, IOrderCallback orderChanged, Context context)  {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.order_card, this, true);

        TableLayout tvOrderBasket = findViewById(R.id.TB_Order);
        for(Product product : order.getProducts()) {
            tvOrderBasket.addView(new CardProduct(product, getContext()));
        }

        findViewById(R.id.IB_OrderSettings)
        .setOnClickListener(this::onSettings);

        _card = new CardOrder(this);
        _dialog = new CardDialog(this) //->
        .setOnReadyListener(this::onReady)
        .setOnCancelListener(this::onCancel)
        .setOnOrderChangedListener(orderChanged);

        updateOrder(order);
    }

    private void onReady(View view) {
        _dialog.setOrderState(OrderState.Ready);
    }
    private void onCancel(View view) {
        _dialog.setOrderState(OrderState.Canceled);
    }
    private void onSettings(View view) {
        _dialog.show();
    }

    public Order getOrder() {
        return _order;
    }
    @Override
    public void updateOrder(Order order) {
        _order = order;
        _card //->
        .setId(order.getId())
        .setDateTime(order.getTime())
        .setCashier(order.getCashier())
        .setState(order.getState());
    }
}
