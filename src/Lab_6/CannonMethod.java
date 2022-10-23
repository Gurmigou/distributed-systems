package Lab_6;

public class CannonMethod {
    private static int[][] a;
    private static int[][] b;
    private static int processAmount;
    private static int[][] res;

    public static int[][] multiply(int[][] a, int[][] b, int processAmount) {
        CannonMethod.a = a;
        CannonMethod.b = b;
        CannonMethod.processAmount = processAmount;
        CannonMethod.res = new int[a.length][a.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                res[i][j] = 0;
            }
        }

        Thread[] tasks = new Thread[processAmount];

        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Thread(new Task(i));
        }

        for (Thread task : tasks) {
            task.start();
        }

        for (Thread task : tasks) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    private record Task(int partIndex) implements Runnable {

        @Override
        public void run() {
            int pivot = (int) Math.ceil(a.length / (double) processAmount);
            for (int row = partIndex * pivot; row < (partIndex + 1) * pivot && row < a.length; row++) {
                int a_j = row;
                int b_i = row;
                for (int counter = 0; counter < a.length; counter++) {
                    for (int i = 0; i < a.length; i++) {
                        res[row][i] += a[row][a_j] * b[b_i][i];
                    }

                    a_j = (a.length + a_j - 1) % a.length;
                    b_i = (a.length + b_i - 1) % a.length;
                }
            }
        }
    }
}