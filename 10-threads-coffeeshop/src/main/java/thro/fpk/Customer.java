package thro.fpk;

class Customer implements Runnable {
    private final CoffeeShop shop;
    private final String name;

    public Customer(CoffeeShop shop, String name) {
        this.shop = shop;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            // Customer joins the queue to wait for service
            shop.joinQueue(this);
            shop.pickUpOrder(name); // Wait and pick up order when ready
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Customer " + name + " was interrupted.");
        }
    }
}
