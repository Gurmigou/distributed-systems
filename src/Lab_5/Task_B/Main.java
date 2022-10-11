package Lab_5.Task_B;

import java.util.concurrent.CyclicBarrier;

public class Main {
    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4);
        Checker checker = new Checker(NUMBER_OF_THREADS);

        Thread firstChanger = new Thread(new Swapper("ABCDCDAABCD", barrier, checker, 0));
        Thread secondChanger = new Thread(new Swapper("AAACAACBBAC", barrier, checker, 1));
        Thread thirdChanger = new Thread(new Swapper("ACDCADCACDC", barrier, checker, 2));
        Thread fourthChanger = new Thread(new Swapper("CDABBABCDAB", barrier, checker, 3));

        firstChanger.start();
        secondChanger.start();
        thirdChanger.start();
        fourthChanger.start();
    }
}