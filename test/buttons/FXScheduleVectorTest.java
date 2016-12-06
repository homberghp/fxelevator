/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hom
 */
public class FXScheduleVectorTest {

    private RequestVector sv;

    public FXScheduleVectorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        sv = new FXRequestVector("Testvector", 32);
    }

    @After
    public void tearDown() {
        sv = null;
    }

    //
    @Test
    public void testHasRequestFor() {
        assertFalse(sv.hasRequestFor(12));
        sv.add(12);
        assertTrue(sv.hasRequestFor(12));
        sv.remove(12);
        assertFalse(sv.hasRequestFor(12));
        sv.add(12);
        sv.add(12);
        assertTrue(sv.hasRequestFor(12));
        sv.remove(12);
        assertFalse(sv.hasRequestFor(12));
    }

    @Test
    public void testHasRequests() {
        assertFalse(sv.hasRequests());
        sv.add(8);
        assertTrue(sv.hasRequests());
        sv.remove(8);
        assertFalse(sv.hasRequests());

    }

    @Test
    public void testNearestBelow() {
        sv.add(12);
        sv.add(6);
        sv.add(18);
        assertEquals(12, sv.nearestBelow(14));
    }

    @Test
    public void testNearestAbove() {
        sv.add(14);
        sv.add(18);
        sv.add(0);
        assertEquals(14, sv.nearestAbove(12));
    }

    @Test
    public void testhasRequestBelow() {
        sv= new FXRequestVector(32);
        assertFalse(sv.hasRequestFor(12));
        sv.add(8);
        assertTrue(sv.hasRequestsBelow(12));
        sv.remove(8);
        sv.add(12);
        assertFalse(sv.hasRequestsBelow(12));
    }

    @Test
    public void testhasRequestAbove() {
        sv.add(44);
        assertFalse("over max floor", sv.hasRequests());
        assertFalse("nothing over max floor", sv.hasRequestsAbove(12));
        sv.remove(44);
        sv.add(12);
        assertFalse(sv.hasRequestsAbove(12));
        assertTrue(sv.hasRequestsAbove(6));
    }

    @Test
    public void testNearestTo() {
        sv.add(6);
        sv.add(12);
        System.out.println( "sv = " + sv);
        assertEquals(12, sv.nearestTo(10));
        sv.add(8);
        assertEquals(8, sv.nearestTo(10));

        sv.removeAll();
        sv.add(8);
        assertEquals(8, sv.nearestTo(10));
        assertEquals(8, sv.nearestTo(4));

    }

    @Test
    public void testInftys() {
        int atFloor = 5;
        assertEquals(Integer.MIN_VALUE, sv.nearestBelow(5));
        assertEquals(Integer.MAX_VALUE, sv.nearestAbove(5));
        assertEquals(atFloor, sv.nearestTo(atFloor));
    }

    @Test
    public void testRemoveAll() {
        sv.add(12);
        sv.removeAll();
        assertFalse(sv.hasRequests());
    }

    @Test
    public void testOffBounds() {
        sv.add(12);
        assertFalse(sv.hasRequestsAbove(40));
        assertFalse(sv.hasRequestsBelow(0));
    }

    @Test
    public void testHighestRequest() {
        sv.add(12);
        assertEquals(12, sv.highestRequest());
        sv.add(8);
        assertEquals(12, sv.highestRequest());
        sv.remove(12);
        assertEquals(8, sv.highestRequest());
        sv.remove(8);
        assertEquals(-1, sv.highestRequest());

    }

    @Test
    public void testLowestRequest() {
        sv.add(12);
        assertEquals(12, sv.lowestRequest());
        sv.add(8);
        assertEquals(8, sv.lowestRequest());
        sv.remove(12);
        assertEquals(8, sv.lowestRequest());
        sv.remove(8);
        assertEquals(-1, sv.lowestRequest());

    }
}