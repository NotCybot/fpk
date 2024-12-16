public interface DiscountStrategy {

    double applyDiscount(double price, int quantity);
}

class NoDiscountStrategy implements DiscountStrategy {
    public double applyDiscount(double price, int quantity) {
        return price * quantity;
    }
}

class PercentageDiscountStrategy implements DiscountStrategy {
    private final double percentage;

    public PercentageDiscountStrategy(double percentage) {
        this.percentage = percentage;
    }

    public double applyDiscount(double price, int quantity) {
        return (price * quantity) * (1 - percentage / 100);
    }
}

class BuyTwoGetOneFreeStrategy implements DiscountStrategy {
    public double applyDiscount(double price, int quantity) {
        return price * (quantity - quantity / 3);
    }
}
