package thro.fpk;


import java.util.concurrent.ThreadLocalRandom;

class Barista implements Runnable {
    private final CoffeeShop shop;
    private final String name;

    public Barista(CoffeeShop shop, String name) {
        this.shop = shop;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Get the next customer in FIFO order
                Customer customer = shop.getNextCustomer();
                String coffeeType = "Coffee for " + customer.getName();
                Order order = new Order(customer, coffeeType, name);
                shop.placeOrder(order);
                Thread.sleep(ThreadLocalRandom.current().nextInt(2000,5001)); // Simulate time to prepare another order, between 2 and 5 seconds
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Barista " + name + " was interrupted.");
        }
    }
}
