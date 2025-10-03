package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class PerformanceTracker {
    private long comparisons = 0;
    private long assignments = 0;
    private long arrayAccesses = 0;

    public void incComparisons() { comparisons++; }
    public void incAssignments() { assignments++; }
    public void incAssignments(long k) { assignments += k; }
    public void incArrayAccess() { arrayAccesses++; }
    public long getComparisons() { return comparisons; }
    public long getAssignments() { return assignments; }
    public long getArrayAccesses() { return arrayAccesses; }

    public static void exportCsv(String path, long n, long timeNs, PerformanceTracker t) {
        try (PrintWriter w = new PrintWriter(new FileWriter(path, true))) {
            w.printf("%s,%d,%d,%d,%d,%d%n", Instant.now().toString(), n, timeNs,
                    t.getComparisons(), t.getAssignments(), t.getArrayAccesses());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        comparisons = 0;
        assignments = 0;
        arrayAccesses = 0;
    }
}