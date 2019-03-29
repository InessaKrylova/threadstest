import java.util.concurrent.SynchronousQueue;

public class NewTest {
    public static void main(String[] args) {

        Waiter waiter = new Waiter();
        new Thread(new Client(waiter)).start();
        new Thread(new Chief(waiter)).start();
    }
}


class Client implements Runnable {
    Waiter waiter;

    public Client(Waiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void run() {
        System.out.println("client run start");
        try {
            waiter.takeOrder();
            String food = waiter.getFood();
            System.out.println(food);
            System.out.println("client run end");
        } catch (InterruptedException e) {
            System.out.println(e);
        }

    }
}

class Waiter {
    SynchronousQueue<String> orderQueue = new SynchronousQueue<>();
    SynchronousQueue<String> foodQueue = new SynchronousQueue<>();

    public void takeOrder() throws InterruptedException {
        orderQueue.put("Order 1");
    }

    public String bringOrderToCook() throws InterruptedException {
        return orderQueue.take();
    }

    public void takeFood(String food) throws InterruptedException {
        foodQueue.put(food);
    }

    public String getFood() throws InterruptedException {
        return foodQueue.take();
    }
}

class Chief implements Runnable {
    Waiter waiter;

    public Chief(Waiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void run() {
        System.out.println("chief run start");
        String order = null;
        try {
            order = waiter.bringOrderToCook();
            System.out.println("wait while cooking" + order);
            Thread.sleep(1000);
            waiter.takeFood("Pasta");
            System.out.println("chief run end");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}