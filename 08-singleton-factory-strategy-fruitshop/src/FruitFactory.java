import java.util.HashMap;
import java.util.Map;

public class FruitFactory {
    private static final Map<String, Double> fruitRegistry = new HashMap<>();

    static {
        // Register default fruits
        fruitRegistry.put("apple", 1.0);
        fruitRegistry.put("orange", 1.2);
        fruitRegistry.put("banana", 0.8);
    }

    public static Fruit createFruit(String type) {
        Double price = fruitRegistry.get(type.toLowerCase());
        if (price == null) {
            throw new IllegalArgumentException("Unknown fruit type: " + type);
        }
        return new Fruit() {
            @Override
            public String getName() {
                return type.toLowerCase();
            }

            @Override
            public double getPrice() {
                return price;
            }
        };
    }

    public static void registerFruit(String type, double price) {
        fruitRegistry.put(type.toLowerCase(), price);
    }

    public static boolean isFruitRegistered(String type) {
        return fruitRegistry.containsKey(type.toLowerCase());
    }

    public static Map<String, Double> getFruitRegistry() {
        return new HashMap<>(fruitRegistry); // Return a copy to prevent modification
    }
}
