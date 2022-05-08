package app.visualorders.MVP.Presenter.ViewFactory;

import android.content.Context;

import app.visualorders.MVP.Model.Order;
import app.visualorders.MVP.View.OrderView.Card.Card;
import app.visualorders.MVP.View.IOrderCallback;
import app.visualorders.MVP.View.IOrderView;

public class CardFactory implements IOrderViewFactory {
    @Override
    public IOrderView createOrderView(Order order, IOrderCallback orderChanged, Context context) {
        return new Card(order, orderChanged, context);
    }
}

