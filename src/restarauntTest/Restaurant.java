package restarauntTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Restaurant {
    private boolean orderMadeByClient;
    private boolean orderTaken;
    private boolean orderReady;
    private boolean orderReceived;
    private long orderId;
    private BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

    public boolean isOrderMadeByClient() {
        return orderMadeByClient;
    }

    public void setOrderMadeByClient(boolean orderMadeByClient) {
        this.orderMadeByClient = orderMadeByClient;
    }

    public boolean isOrderTaken() {
        return orderTaken;
    }

    public void setOrderTaken(boolean orderTaken) {
        this.orderTaken = orderTaken;
    }

    public boolean isOrderReady() {
        return orderReady;
    }

    public void setOrderReady(boolean orderReady) {
        this.orderReady = orderReady;
    }

    public boolean isOrderReceived() {
        return orderReceived;
    }

    public void setOrderReceived(boolean orderReceived) {
        this.orderReceived = orderReceived;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BlockingQueue<String> getBlockingQueue() {
        return blockingQueue;
    }

    public void setBlockingQueue(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public Restaurant() {
    }

    public void makeRestaurant() {
        new Thread(new Client(this)).start();
        new Thread(new Chef(this)).start();
        new Thread(new Waiter(this)).start();
    }

    public void waitWithProcessing() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized long getOrderMadeByClient() {
        while (!orderMadeByClient) {
            waitWithProcessing();
        }
        orderMadeByClient = false;
        notifyAll();
        System.out.println("--- get order from client #" + orderId);
        return orderId;
    }

    synchronized void putOrderMadeByClient(long l) {
        while (orderMadeByClient) {
            waitWithProcessing();
        }
        orderMadeByClient = true;
        this.orderId = l;
        notifyAll();
        System.out.println("--- put order client #" + l);
    }

    synchronized long getOrder() {
        while (!orderTaken) {
            waitWithProcessing();
        }
        orderTaken = false;
        notifyAll();
        System.out.println("--- get order from waiter #" + orderId);
        return orderId;
    }

    synchronized void putOrder(long l) {
        while (orderTaken) {
            waitWithProcessing();
        }
        orderTaken = true;
        this.orderId = l;
        notifyAll();
        System.out.println("--- put order to cook #" + l);
    }

    synchronized long getFood() {
        while (!orderReady) {
            waitWithProcessing();
        }
        orderReady = false;
        notifyAll();
        System.out.println("--- get order by waiter #" + orderId);
        return orderId;
    }

    synchronized void putFood(long n) {
        while (orderReady) {
            waitWithProcessing();
        }
        orderReady = true;
        this.orderId = n;
        notifyAll();
        System.out.println("--- put order by cook #" + n);
    }

    synchronized long getOrderReceived() {
        while (!orderReceived) {
            waitWithProcessing();
        }
        orderReceived = false;
        notifyAll();
        System.out.println("--- get order by client #" + orderId);
        return orderId;
    }

    synchronized void putOrderReceived(long l) {
        while (orderReceived) {
            waitWithProcessing();
        }
        orderReceived = true;
        this.orderId = l;
        notifyAll();
        System.out.println("--- put order by waiter #" + l);
    }
}

