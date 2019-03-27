package restarauntTest;

public class Restaurant {
    boolean orderMadeByClient;
    boolean orderTaken;
    boolean orderReady;
    boolean orderReceived;
    long orderId;

    public Restaurant() {
    }

    public void makeRestaurant() {
        Client cl = new Client(this);
        Chef ch = new Chef(this);
        Waiter w = new Waiter(this);
        Thread t1 = new Thread(cl);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(ch);

        t1.start();
        t2.start();
        t3.start();
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

        }
        orderMadeByClient = false;
        notifyAll();
        System.out.println("get order from client #" + orderId);
        return orderId;
    }

    synchronized void putOrderMadeByClient(long l) {
        while (orderMadeByClient) {
            waitWithProcessing();
        }
        orderMadeByClient = true;
        this.orderId = l;
        notifyAll();
        System.out.println("put order client #" + l);
    }

    synchronized long getOrder() {
        while (!orderTaken) {
            waitWithProcessing();
        }
        orderTaken = false;
        notifyAll();
        System.out.println("get order from waiter #" + orderId);
        return orderId;
    }

    synchronized void putOrder(long l) {
        while (orderTaken) {
            waitWithProcessing();
        }
        orderTaken = true;
        this.orderId = l;
        notifyAll();
        System.out.println("put order to cook #" + l);
    }

    synchronized long getFood() {
        while (!orderReady) {
            waitWithProcessing();
        }
        orderReady = false;
        notifyAll();
        System.out.println("get order by waiter #" + orderId);
        return orderId;
    }

    synchronized void putFood(long n) {
        while (orderReady) {
            waitWithProcessing();
        }
        orderReady = true;
        this.orderId = n;
        notifyAll();
        System.out.println("put order by cook #" + n);
    }

    synchronized long getOrderReceived() {
        while (!orderReceived) {
            waitWithProcessing();
        }
        orderReceived = false;
        notifyAll();
        System.out.println("get order by client #" + orderId);
        return orderId;
    }

    synchronized void putOrderReceived(long l) {
        while (orderReceived) {
            waitWithProcessing();
        }
        orderReceived = true;
        this.orderId = l;
        notifyAll();
        System.out.println("put order by waiter #" + l);
    }
}

