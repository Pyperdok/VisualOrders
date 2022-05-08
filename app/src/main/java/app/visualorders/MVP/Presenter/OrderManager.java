package app.visualorders.MVP.Presenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import app.visualorders.MVP.Model.Order;
import app.visualorders.MVP.Model.Product;
import app.visualorders.Util.HttpClient;
import app.visualorders.MVP.View.ProgressBar;
import app.visualorders.MVP.View.IOrderView;
import app.visualorders.MVP.Presenter.ViewFactory.IOrderViewFactory;

public class OrderManager {
    private Context _context;
    private ViewGroup _parent;
    private HttpClient _client;
    private ProgressBar _progressBar;
    private IOrderViewFactory _orderViewFactory;
    private HashMap<Integer, IOrderView> _orders = new HashMap<>();

    public OrderManager(IOrderViewFactory orderViewFactory, ViewGroup parent, Context context) {
        _context = context;
        _parent = parent;
        _client = new HttpClient("dbd-mix.xyz");
        _progressBar = new ProgressBar(_context);
        _orderViewFactory = orderViewFactory;
    }

    public void Init() {
        ScheduledExecutorService getOrderInterval = Executors.newScheduledThreadPool(1);
        getOrderInterval.scheduleAtFixedRate(() ->
            _client.request("GET", "/visualorders/order")
            .then(res -> {
                String body = res.body().string();
                System.out.println(body);
                JSONArray jsonOrders = new JSONArray(body);
                for (int i = 0; i < jsonOrders.length(); i++) {
                    JSONObject jsOrder = jsonOrders.getJSONObject(i);
                    Order.Builder order = new Order.Builder().deserialize(jsOrder);
                    JSONArray basket = jsOrder.getJSONArray("basket");
                    for (int j = 0; j < basket.length(); j++) {
                        JSONObject jsProduct = basket.getJSONObject(j);
                        Product product = new Product.Builder()
                        .deserialize(jsProduct).build();
                        order.addProduct(product);
                    }
                    onOrderUpdated(order.build());
                }
            }),
        0, 5, TimeUnit.SECONDS);
    }

    private void onOrderStateChanged(Order order) {
        int id = order.getId();
        int state = order.getState().ordinal();
        String body = "{\"orderid\":"+id+",\"state\":"+state+"}";
        _progressBar.show();
        _client.request("PUT", "/visualorders/order", body)
        .then(res -> {
            onOrderUpdated(order);
            _progressBar.hide();
        });
    }

    private void onOrderUpdated(Order order) {
        int id = order.getId();
        if(_orders.containsKey(id)) {
            _orders.get(id).updateOrder(order);
            return;
        }
        IOrderView orderView = _orderViewFactory.createOrderView(order, this::onOrderStateChanged, _context);
        _orders.put(id, orderView);
        _parent.addView((View)orderView);
        //NewOrderAdded
    }
}
