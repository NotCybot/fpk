
import java.util.LinkedList;
import java.util.Queue;
public class OrderProcessor {
    private final Queue<OrderCommand> orderQueue;
    public OrderProcessor() {
        this.orderQueue = new LinkedList<>();
    }
    public void addOrder(OrderCommand command) {
        orderQueue.add(command);
    }
    public void processOrders() {
        while (!orderQueue.isEmpty()) {
            orderQueue.poll().execute();
        }
    }
}