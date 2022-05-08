package app.visualorders.MVP.View;

import app.visualorders.MVP.Model.Order;

public interface IOrderCallback {
    void execute(Order order);
}
