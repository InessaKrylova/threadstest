package restarauntTest;

public class Client implements Runnable {
    private Restaurant restaurant;

    public void makeOrder() throws InterruptedException {
        synchronized (restaurant) {
            restaurant.notifyAll();
            System.out.println("[Client]: Start ordering ");
            Thread.sleep(1000);
            restaurant.putOrderMadeByClient(restaurant.orderId);
            System.out.println("[Client]: End ordering ");
            while(restaurant.orderReceived==false)
                restaurant.wait();
            restaurant.getOrderReceived();
        }
    }

    public Client(Restaurant r) {
        this.restaurant =r;
    }

    public void run() {
        try {
            makeOrder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}