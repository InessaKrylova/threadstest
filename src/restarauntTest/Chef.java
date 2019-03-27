package restarauntTest;

public class Chef implements Runnable {
    private Restaurant r;

    public void makeFood() throws InterruptedException {
        synchronized (r) {
            r.notifyAll();
            while(r.orderTaken==false)
                r.wait();
            System.out.println("Start making food ");
            Thread.sleep(1000);
            r.putFood(r.getOrder());
            System.out.println("End making food ");
        }
    }

    public Chef(Restaurant r) {
        this.r=r;
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
