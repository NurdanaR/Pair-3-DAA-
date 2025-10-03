package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;

public class BoyerMooreMajority {

    private final PerformanceTracker tracker;

    public BoyerMooreMajority(PerformanceTracker tracker) {
        this.tracker = tracker == null ? new PerformanceTracker() : tracker;
    }

    public Integer findCandidate(int[] a) {
        tracker.incArrayAccess(); // read length (conceptual)
        if (a == null || a.length == 0) return null;

        Integer candidate = null;
        int count = 0;

        for (int i = 0; i < a.length; i++) {
            tracker.incArrayAccess();
            int v = a[i];

            if (count == 0) {
                candidate = v;
                count = 1;
                tracker.incAssignments(2);
            } else {
                tracker.incComparisons();
                if (candidate == v) {
                    count++;
                } else {
                    count--;
                }
                tracker.incAssignments(1);
            }
        }
        return candidate;
    }

    public boolean isMajority(int[] a, Integer candidate) {
        if (candidate == null) return false;
        int cnt = 0;
        for (int i = 0; i < a.length; i++) {
            tracker.incArrayAccess();
            if (a[i] == candidate) {
                cnt++;
            }
            tracker.incComparisons();
        }
        return cnt > a.length / 2;
    }

    public Integer findMajorityElement(int[] a) {
        Integer candidate = findCandidate(a);
        if (candidate == null) return null;
        boolean ok = isMajority(a, candidate);
        return ok ? candidate : null;
    }

    public PerformanceTracker getTracker() {
        return tracker;
    }
}