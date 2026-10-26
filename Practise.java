public class Practise {

    static final long N = 100_000_000L;
    static final int THREAD_COUNT = 4;

    public static void main(String[] args)
            throws InterruptedException {

        // 1. Single-thread execution
        long start = System.nanoTime();

        long singleResult = calculateSum(1, N);

        long end = System.nanoTime();

        double singleTime = (end - start) / 1_000_000.0;

        System.out.println("Single Thread Result: " + singleResult);
        System.out.printf("Single Thread Time: %.2f ms%n", singleTime);


        // 2. Multithreaded execution
        long[] results = new long[THREAD_COUNT];
        Thread[] threads = new Thread[THREAD_COUNT];

        long chunkSize = N / THREAD_COUNT;

        start = System.nanoTime();

        for (int i = 0; i < THREAD_COUNT; i++) {

            long from = i * chunkSize + 1;
            long to = (i == THREAD_COUNT - 1)
                    ? N
                    : (i + 1) * chunkSize;

            final int index = i;

            threads[i] = new Thread(() -> {
                results[index] = calculateSum(from, to);
            });

            threads[i].start();
        }

        // Wait for every thread to finish
        for (Thread thread : threads) {
            thread.join();
        }

        long multiResult = 0;

        for (long result : results) {
            multiResult += result;
        }

        end = System.nanoTime();

        double multiTime = (end - start) / 1_000_000.0;

        System.out.println("\nMultithreaded Result: " + multiResult);
        System.out.printf("Multithreaded Time: %.2f ms%n", multiTime);

        System.out.printf(
            "Speedup: %.2fx%n",
            singleTime / multiTime
        );

        System.out.println(
            "Results Match: " + (singleResult == multiResult)
        );
    }

    static long calculateSum(long from, long to) {
        long sum = 0;

        for (long i = from; i <= to; i++) {
            sum += i;
        }

        return sum;
    }
}
