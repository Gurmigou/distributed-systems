package Lab_6;

public class TapeMethod {
    private static int[][] a;
    private static int[][] b;
    private static int processAmount;
    private static int[][] res;

    public static int[][] multiply(int[][] a, int[][] b, int processAmount) {
        TapeMethod.a = a;
        TapeMethod.b = b;
        TapeMethod.processAmount = processAmount;
        TapeMethod.res = new int[a.length][a.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                res[i][j] = 0;
            }
        }

        Thread[] tasks = new Thread[processAmount];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Thread(new Tape(i));
        }

        for (Thread tape : tasks) {
            tape.start();
        }

        for (Thread tape : tasks) {
            try {
                tape.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    private record Tape(int partIndex) implements Runnable {

        @Override
        public void run() {
            int pivot = (int) Math.ceil(a.length / (double) processAmount);
            for (int row = partIndex * pivot; row < (partIndex + 1) * pivot && row < a.length; row++) {
                int index = row;
                for (int counter = 0; counter < a.length; counter++) {
                    for (int j = 0; j < a.length; j++) {
                        res[row][index] += a[row][j] * b[j][index];
                    }
                    index = (index + 1) % a.length;
                }
            }
        }
    }
}