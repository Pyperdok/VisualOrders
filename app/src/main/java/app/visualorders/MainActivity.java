package app.visualorders;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import app.visualorders.MVP.Presenter.OrderManager;
import app.visualorders.MVP.Presenter.ViewFactory.CardFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardFactory cardFactory = new CardFactory();
        OrderManager orderManager = new OrderManager(cardFactory, findViewById(R.id.LL_Orders), this);
        orderManager.Init();
    }
}