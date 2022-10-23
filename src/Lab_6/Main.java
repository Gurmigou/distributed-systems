package Lab_6;

import java.security.SecureRandom;
import java.util.Arrays;

public class Main {
    private static final int[] SIZES = {10, 100, 500, 1000};

    public static void main(String[] args){
        SecureRandom random = new SecureRandom();
        long start;
        long end;
        System.out.println("N\t\tSIMPLE\t\tTAPE(2)\t\tTAPE(4)\t\tFOX(2)\t\tFOX(4)\t\tCANNON(2)\tCANNON(4)");
        for (int size : SIZES) {
            int[][] a = new int[size][size];
            int[][] b = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    a[i][j] = random.nextInt(10);
                    b[i][j] = random.nextInt(10);
                }
            }

            long[] results = new long[7];
            // --------------- 
            start = System.currentTimeMillis();

            int[][] simple = SimpleMethod.multiply(a, b);

            end = System.currentTimeMillis();
            results[0] = end - start;
            // --------------- 
            start = System.currentTimeMillis();

            int[][] tape2 = TapeMethod.multiply(a, b, 2);

            end = System.currentTimeMillis();
            results[1] = end - start;
            // --------------- 
            start = System.currentTimeMillis();

            int[][] tape4 = TapeMethod.multiply(a, b, 4);

            end = System.currentTimeMillis();
            results[2] = end - start;
            // --------------- 
            start = System.currentTimeMillis();

            int[][] fox2 = FoxMethod.multiply(a, b, 2);

            end = System.currentTimeMillis();
            results[3] = end - start;
            // --------------- 
            start = System.currentTimeMillis();

            int[][] fox4 = FoxMethod.multiply(a, b, 4);

            end = System.currentTimeMillis();
            results[4] = end - start;
            // --------------- 
            start = System.currentTimeMillis();

            int[][] cannon2 = CannonMethod.multiply(a, b, 2);

            end = System.currentTimeMillis();
            results[5] = end - start;
            // --------------- 
            start = System.currentTimeMillis();

            int[][] cannon4 = CannonMethod.multiply(a, b, 4);

            end = System.currentTimeMillis();
            results[6] = end - start;
            // ---------------            
            System.out.println(
                    "\nTESTS:"
                    + "\tTape 2: " + Arrays.deepEquals(simple, tape2)
                    + "\t\tTape 4: " + Arrays.deepEquals(simple, tape4)
                    + "\t\tFox 2: " + Arrays.deepEquals(simple, fox2)
                    + "\t\tFox 4: " + Arrays.deepEquals(simple, fox4)
                    + "\t\tCannon 2: " + Arrays.deepEquals(simple, cannon2)
                    + "\t\tCannon 4: " + Arrays.deepEquals(simple, cannon4));

            System.out.print(size);
            for (long r : results) {
                System.out.print("\t\t" + r + " ms");
            }
            System.out.println();

        }

    }

}