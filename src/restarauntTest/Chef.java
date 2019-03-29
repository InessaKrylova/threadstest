package restarauntTest;

public class Chef implements Runnable {
    private Restaurant restaurant;

    public void makeFood() throws InterruptedException {
        synchronized (restaurant) {
            restaurant.notifyAll();
            while(restaurant.orderTaken==false)
                restaurant.wait();
            System.out.println("[Chef]: Start making food ");
            Thread.sleep(1000);
            restaurant.putFood(restaurant.getOrder());
            System.out.println("[Chef]: End making food ");
        }
    }

    public Chef(Restaurant r) {
        this.restaurant =r;
    }

    @Override
    public void run() {
        try {
            makeFood();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
