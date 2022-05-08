package app.visualorders.MVP.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Order {
    private int _id;
    private int _timestamp;
    private OrderState _state;
    private String _cashier;
    private ArrayList<Product> _products;

    private Order() {};

    public int getId() {
        return _id;
    }
    public int getTime() {
        return _timestamp;
    }
    public OrderState getState() {
        return _state;
    }
    public String getCashier() {
        return _cashier;
    }
    public ArrayList<Product> getProducts() {
        return _products;
    }

    public void setState(OrderState state) {
        _state = state;
    }

    @Override public String toString() {
        return "Order {" +
                "_id=" + _id +
                ", _dateTime=" + _timestamp +
                ", _state=" + _state +
                ", _cashier='" + _cashier + '\'' +
                ", _products=" + _products +
                '}';
    }

    public static class Builder {
        private Order _order;

        public Builder() {
            _order = new Order();
            _order._products = new ArrayList<>();
        }
        public Builder setId(int id) {
            _order._id = id;
            return this;
        }
        public Builder setTime(int timestamp) {
            _order._timestamp = timestamp;
            return this;
        }
        public Builder setState(int state) {
            _order._state = OrderState.fromInt(state);
            return this;
        }
        public Builder setCashier(String cashier) {
            _order._cashier = cashier;
            return this;
        }
        public Builder addProduct(Product product) {
            _order._products.add(product);
            return this;
        }
        public Order build() {
            return _order;
        }

        public Builder deserialize(JSONObject js) throws JSONException {
            return new Order.Builder() //->
            .setId(js.getInt("order"))
            .setTime(js.getInt("timestamp"))
            .setCashier(js.getString("cashier"))
            .setState(js.getInt("state"));
        }
    }
}
