public interface OrderCommand {
    void execute();
}

/**
 * Following classes are implementations of OrderCommand
 */

class BuyFruitCommand implements OrderCommand {
    private final String fruitName;
    private final int quantity;
    private final FruitShopManager manager;
    double price;

    public BuyFruitCommand(String fruitName, int quantity,  double price) {
        this.fruitName = fruitName;
        this.quantity = quantity;
        this.manager = FruitShopManager.getInstance();
        this.price = price;
    }

    public void execute() {
        if (manager.removeFruit(fruitName, quantity)) {
            System.out.println("Bought " + quantity + " " + fruitName + "(s) for $" + price * quantity);
        } else {
            System.out.println("Insufficient stock for " + fruitName);
        }
    }
}
