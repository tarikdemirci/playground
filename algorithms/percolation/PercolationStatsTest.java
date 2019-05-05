import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PercolationStatsTest {
    private static PercolationStats percolationStats;
    @BeforeAll
    public static void beforeAll() {
        percolationStats = new PercolationStats(200, 100);
    }

    @Test
    public void testMean() {
        double expected = 0.5929934999999997;
        assertEquals(expected, percolationStats.mean(), 0.01 * expected);
    }

    @Test
    public void testStddev() {
        double expected = 0.00876990421552567;
        assertEquals(expected, percolationStats.stddev(), 0.5 * expected);
    }

    @Test
    public void testConfidenceLo() {
        double expected = 0.5912745987737567;
        assertEquals(expected, percolationStats.confidenceLo(), 0.01 * expected);
    }

    @Test
    public void testConfidenceHi() {
        double expected = 0.5947124012262428;
        assertEquals(expected, percolationStats.confidenceHi(), 0.01 * expected);
    }
}
