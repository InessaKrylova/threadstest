package restarauntTest;

public class Client implements Runnable {
    private Restaurant r;

    public void makeOrder() throws InterruptedException {
        synchronized (r) {
            r.notifyAll();
            System.out.println("Start ordering ");
            Thread.sleep(1000);
            r.putOrderMadeByClient(r.orderId);
            System.out.println("End ordering ");
            while(r.orderReceived==false)
                r.wait();
            r.getOrderReceived();
        }
    }

    public Client(Restaurant r) {
        this.r=r;
    }

    public void run() {
        try {
            makeOrder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}