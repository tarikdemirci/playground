import java.util.Set;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class PercolationTest {
    private class Coordinate {
        private int row;
        private int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private Percolation percolation;
    private Set<Coordinate> illegalCoordinates;

    @BeforeEach
    public void beforeEach() {
        percolation = new Percolation(2);
        illegalCoordinates = Set.of(
                new Coordinate(-1, 1),
                new Coordinate(0, 1),
                new Coordinate(3, 1),
                new Coordinate(1, -1),
                new Coordinate(1, 0),
                new Coordinate(1, 3)
        );

    }

    @Test
    public void testSitesAreBlockedInitially() {
        assertFalse(percolation.isOpen(1, 1));
        assertFalse(percolation.isOpen(1, 2));
        assertFalse(percolation.isOpen(2, 1));
        assertFalse(percolation.isOpen(2, 2));
    }

    @Test
    public void testIllegalArgumentsAreHandled() {
        for (Coordinate c : illegalCoordinates) {
            // Test open
            assertThrows(IllegalArgumentException.class, () -> percolation.open(c.row, c.col));

            // Test isOpen
            assertThrows(IllegalArgumentException.class, () -> percolation.isOpen(c.row, c.col));

            // Test isFull
            assertThrows(IllegalArgumentException.class, () -> percolation.isFull(c.row, c.col));
        }
    }

    @Test
    public void testOpenAndIsOpen() {
        percolation.open(1, 1);
        assertTrue(percolation.isOpen(1, 1));
        assertFalse(percolation.isOpen(1, 2));
    }

    @Test
    public void testIsFull() {
        percolation.open(2, 1); // open site on bottom left
        assertFalse(percolation.isFull(2, 1));

        percolation.open(1, 2); // open site on top right
        assertFalse(percolation.isFull(2, 1));

        percolation.open(1, 1); // open site on top left right
        assertTrue(percolation.isFull(2, 1));
    }

    @Test
    public void testNumberOfOpenSites() {
        assertEquals(0, percolation.numberOfOpenSites());

        percolation.open(2, 1);
        assertEquals(1, percolation.numberOfOpenSites());

        percolation.open(2, 1);
        assertEquals(1, percolation.numberOfOpenSites());

        percolation.open(2, 2);
        assertEquals(2, percolation.numberOfOpenSites());
    }

    @Test
    public void testPercolates() {
        assertFalse(percolation.percolates());

        percolation.open(2, 1);
        assertFalse(percolation.percolates());

        percolation.open(2, 2);
        assertFalse(percolation.percolates());

        percolation.open(1, 2);
        assertTrue(percolation.percolates());
    }
}
