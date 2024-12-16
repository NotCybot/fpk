import java.util.HashMap;
import java.util.Map;

public class FruitShopManager {
    private DiscountStrategy discountStrategy = new NoDiscountStrategy();
    private static FruitShopManager instance;
    private Map<String, Integer> stock;

    private FruitShopManager() {
        stock = new HashMap<>();
    }

    public static FruitShopManager getInstance() {
        if (instance == null) {
            instance = new FruitShopManager();
        }
        return instance;
    }

    public void addFruit(String fruitName, int quantity) {
        stock.put(fruitName, stock.getOrDefault(fruitName, 0) + quantity);
    }

    public boolean removeFruit(String fruitName, int quantity) {
        if (stock.getOrDefault(fruitName, 0) >= quantity) {
            stock.put(fruitName, stock.get(fruitName) - quantity);
            return true;
        }
        return false;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public Map<String, Integer> getStock() {
        return new HashMap<>(stock);
    }
}
