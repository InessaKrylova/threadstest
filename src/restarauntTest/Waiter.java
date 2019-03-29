package restarauntTest;

public class Waiter implements Runnable {
    private Restaurant restaurant;

    public void makeServe() throws InterruptedException {
        synchronized (restaurant) {
            restaurant.notifyAll();
            while(!restaurant.isOrderMadeByClient()) {
                restaurant.wait();
            }
            System.out.println("[Waiter]: Start serving order ");
            Thread.sleep(1000);
            restaurant.putOrder(restaurant.getOrderMadeByClient());
            while(!restaurant.isOrderReady()) {
                restaurant.wait();
            }
            restaurant.putOrderReceived(restaurant.getFood());
            System.out.println("[Waiter]: End serving order ");
        }
    }

    public Waiter(Restaurant r) {
        this.restaurant =r;
    }

    @Override
    public void run() {
        try {
            makeServe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}