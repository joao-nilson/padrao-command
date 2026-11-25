package padroescomportamentais.commandcart;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Integer> stock = new HashMap<>();

    public Inventory(String item, int quantity) {
        stock.put(item, quantity);
    }

    public int getStock(String item) {
        return stock.getOrDefault(item, 0);
    }

    public void reserve(String item) {
        if (getStock(item) == 0) {
            throw new IllegalStateException("Out of stock: " + item);
        }
        stock.put(item, getStock(item) - 1);
    }

    public void release(String item) {
        stock.put(item, getStock(item) + 1);
    }
}
