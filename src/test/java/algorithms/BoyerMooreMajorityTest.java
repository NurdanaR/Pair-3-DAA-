package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.Arrays;

class BoyerMooreMajorityTest {

    @Test
    void testEmpty() {
        BoyerMooreMajority bm = new BoyerMooreMajority(new PerformanceTracker());
        assertNull(bm.findMajorityElement(new int[0]));
    }

    @Test
    void testSingleElement() {
        BoyerMooreMajority bm = new BoyerMooreMajority(new PerformanceTracker());
        assertEquals(5, bm.findMajorityElement(new int[]{5}));
    }

    @Test
    void testMajorityPresent() {
        int[] a = new int[]{1,2,1,1,3,1,1};
        BoyerMooreMajority bm = new BoyerMooreMajority(new PerformanceTracker());
        assertEquals(1, bm.findMajorityElement(a));
    }

    @Test
    void testNoMajority() {
        int[] a = new int[]{1,2,3,4,5,6};
        BoyerMooreMajority bm = new BoyerMooreMajority(new PerformanceTracker());
        assertNull(bm.findMajorityElement(a));
    }

    @Test
    void randomizedProperty() {
        Random rnd = new Random(42);
        for (int t = 0; t < 100; t++) {
            int n = 50 + rnd.nextInt(150);
            int[] a = new int[n];
            boolean force = rnd.nextBoolean();
            int maj = rnd.nextInt(5);
            if (force) {
                int mcount = n/2 + 1 + rnd.nextInt(n/4);
                for (int i = 0; i < mcount; i++) a[i] = maj;
                for (int i = mcount; i < n; i++) a[i] = rnd.nextInt(10);
            } else {
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt(10);
            }
            for (int i = 0; i < n; i++) {
                int j = rnd.nextInt(n);
                int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
            }

            BoyerMooreMajority bm = new BoyerMooreMajority(new PerformanceTracker());
            Integer res = bm.findMajorityElement(a);
            if (res != null) {
                long cnt = Arrays.stream(a).filter(x -> x == res).count();
                assertTrue(cnt > n/2);
            } else {
                boolean any = false;
                for (int v = 0; v < 10; v++) {
                    long cnt = Arrays.stream(a).filter(x -> x == v).count();
                    if (cnt > n/2) any = true;
                }
                assertFalse(any);
            }
        }
    }
}