package thro.fpk;

import java.util.LinkedList;
import java.util.Queue;

public class CoffeeShop {
    private final int MAX_CAPACITY = 5;
    private final Queue<Order> counter = new LinkedList<>();
    private final Queue<Customer> customerQueue = new LinkedList<>();

    public synchronized void placeOrder(Order order) throws InterruptedException {
        while (counter.size() == MAX_CAPACITY) {
            System.out.println("Counter is full. Barista " + order.getBarista() + " is waiting.");
            wait(); // Wait for space to become available
        }
        counter.add(order);
        System.out.println(order.getBarista() + " placed order for " + order.getCoffeeType() + " for " + order.getCustomer().getName());
        notifyAll(); // Notify waiting customers
    }

    public synchronized void joinQueue(Customer customer) {
        customerQueue.add(customer);
        System.out.println("Customer " + customer.getName() + " joined the queue.");
        notifyAll(); // Notify baristas that there is a customer to serve
    }

    public synchronized void pickUpOrder(String customerName) throws InterruptedException {
        while (true) {
            // Check if the specific customer's order is ready
            for (Order order : counter) {
                if (order.getCustomer().getName().equals(customerName)) {
                    counter.remove(order);
                    System.out.println("Customer " + customerName + " picked up " + order.getCoffeeType());
                    notifyAll(); // Notify waiting baristas that space is available
                    return; // Exit the method after picking up the order
                }
            }

            // If the customer's order is not ready, wait for notification
            System.out.println("Customer " + customerName + " is waiting for their order.");
            wait();
        }
    }


    public synchronized Customer getNextCustomer() throws InterruptedException {
        while (customerQueue.isEmpty()) {
            System.out.println("No customers in queue. Barista is waiting.");
            wait(); // Wait for customers to arrive
        }
        return customerQueue.poll();
    }

}
