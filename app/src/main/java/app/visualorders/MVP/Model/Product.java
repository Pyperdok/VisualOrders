package app.visualorders.MVP.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private String _name;
    private double _price;
    private double _size;
    private String _unit;
    private int _count;

    private Product(){};
    public String getName() {
        return _name;
    }
    public double getPrice() {
        return _price;
    }
    public double getSize() {
        return _size;
    }
    public String getUnit() {
        return _unit;
    }
    public int getCount() {
        return _count;
    }

    public boolean equals(Product product) {
        boolean name = _name.equals(product._name);
        boolean price = _price == product._price;
        boolean size = _size == product._size;
        boolean unit = _unit.equals(product._unit);
        boolean count = _count == _count;

        return name & price & size & unit & count;
    }
    public static class Builder {
        private Product _product;

        public Builder() {
            _product = new Product();
        }
        public Builder setName(String name) {
            _product._name = name;
            return this;
        }
        public Builder setPrice(double price) {
            _product._price = price;
            return this;
        }
        public Builder setSize(double size) {
            _product._size = Math.round(size*100)/100.00;
            return this;
        }
        public Builder setUnit(String unit) {
            _product._unit = unit;
            return this;
        }
        public Builder setCount(int count) {
            _product._count = count;
            return this;
        }
        public Product build() {
            return _product;
        }

        public Builder deserialize(JSONObject js) throws JSONException {
            return new Product.Builder()
            .setName(js.getString("name"))
            .setPrice(js.getDouble("price"))
            .setSize(js.getDouble("size"))
            .setUnit(js.getString("unit"))
            .setCount(js.getInt("count"));
        }
    }
}