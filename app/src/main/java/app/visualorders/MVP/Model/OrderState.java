package app.visualorders.MVP.Model;

import app.visualorders.R;

public enum OrderState {
    Processing(0),
    Ready(1),
    Completed(2),
    Canceled(3);
    private int _state;
    private String[] _names = new String[]{"Обработка", "Готов", "Завершен", "Отменен"};
    private int[] _colors = new int[]{R.color.yellow, R.color.limegreen, R.color.lightgreen, R.color.red};

    private OrderState(int state) {
        _state = state;
    }
    public static OrderState fromInt(int state) {
        return OrderState.values()[state];
    }
    public String getName() {
        return _names[_state];
    }
    public int getColorId() {
        return _colors[_state];
    }
}
