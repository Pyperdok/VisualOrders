package app.visualorders.MVP.Presenter.ViewFactory;

import android.content.Context;

import app.visualorders.MVP.Model.Order;
import app.visualorders.MVP.View.IOrderCallback;
import app.visualorders.MVP.View.IOrderView;

public interface IOrderViewFactory {
    IOrderView createOrderView(Order order, IOrderCallback orderChanged, Context context);
}
