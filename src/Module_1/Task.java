package Module_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Task {
    public static final BlockingQueue<Customer> WAITING_CUSTOMERS = new LinkedBlockingDeque<>();
    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        int customers = 10;
        int operators = 2;

        for (int i = 0; i < operators; i++) {
            new Operator(WAITING_CUSTOMERS).start();
        }

        List<Customer> customerList = new ArrayList<>();
        for (int i = 0; i < customers; i++) {
            Customer customer = new Customer(WAITING_CUSTOMERS);
            customerList.add(customer);
            customer.start();
        }

        for (Customer c : customerList) {
            try {
                c.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
