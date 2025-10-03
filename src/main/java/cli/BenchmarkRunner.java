package cli;

import algorithms.BoyerMooreMajority;
import metrics.PerformanceTracker;

import java.util.Random;
import java.util.stream.IntStream;

public class BenchmarkRunner {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000}; // можно менять через args
        String csv = "benchmark_results.csv";

        for (int n : sizes) {
            runScenario(n, "random", csv, false);
            runScenario(n, "majority_present", csv, true);
            runScenario(n, "no_majority", csv, false);
        }
        System.out.println("Benchmarks finished. CSV -> " + csv);
    }

    private static void runScenario(int n, String label, String csv, boolean forceMajority) {
        int[] arr = generateArray(n, forceMajority);
        PerformanceTracker tracker = new PerformanceTracker();
        BoyerMooreMajority bm = new BoyerMooreMajority(tracker);

        long t0 = System.nanoTime();
        Integer result = bm.findMajorityElement(arr);
        long t1 = System.nanoTime();

        long timeNs = t1 - t0;
        PerformanceTracker.exportCsv(csv, n, timeNs, tracker);
        System.out.printf("n=%d scenario=%s result=%s time(ms)=%.3f comparisons=%d%n",
                n, label, result == null ? "null" : result.toString(), timeNs/1e6, tracker.getComparisons());
    }

    private static int[] generateArray(int n, boolean forceMajority) {
        Random rnd = new Random(123);
        int[] a = new int[n];
        if (n == 0) return a;

        if (forceMajority) {
            int maj = rnd.nextInt(10);
            int majCount = (int)(n * 0.6);
            for (int i = 0; i < majCount; i++) a[i] = maj;
            for (int i = majCount; i < n; i++) a[i] = rnd.nextInt(100);
            for (int i = 0; i < n; i++) {
                int j = rnd.nextInt(n);
                int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
            }
            return a;
        } else {
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(Math.max(10, n/10));
            return a;
        }
    }
}