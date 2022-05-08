package app.visualorders.MVP.View.OrderView.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import app.visualorders.MVP.Model.Product;
import app.visualorders.R;

public class CardProduct extends ConstraintLayout {
    public CardProduct(Product product, Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.order_card_product, this, true);

        TextView tvCount = findViewById(R.id.TV_Count);
        TextView tvProduct = findViewById(R.id.TV_Product);
        TextView tvSize = findViewById(R.id.TV_Size);

        tvCount.setText(String.valueOf(product.getCount()));
        tvProduct.setText(product.getName());

        String size = product.getSize() + " " + product.getUnit();
        tvSize.setText(size);
    }
}