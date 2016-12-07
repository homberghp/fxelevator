package buttons;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;

/**
 * Helper interface in Elevator schedule.
 *
 * RequestVector maintains a dataset to administer the requests identified by
 * integers. The current implementation supports up to 64 distinct requests. The
 * RequestVector does not count the requests per identifier, i.e. it operates as
 * a bitset. Particular for the elevator are the methods hasRequestAbove() and
 * hasRequestBelow() that help advising a direction of movement.
 *
 * @author hom
 */
public interface RequestVector {

    /**
     * Add a request for a floor.
     *
     * @param floor of request
     */
    void add( int floor );

    /**
     * Remove a request for a floor.
     *
     * @param floor of request
     */
    void remove( int floor );

    /**
     * Remove all requests.
     */
    void removeAll();

    /**
     * Is there a request for this floor?
     *
     * @param floor the floor to visit
     * @return true if must visit floor
     */
    boolean hasRequestFor( int floor );

    /**
     * Are there any request of this type above floor.
     *
     * @param floor to test for
     * @return true if request above
     */
    boolean hasRequestsAbove( int floor );

    /**
     * Are there any request of this type below floor.
     *
     * @param floor to test for
     * @return true if request below
     */
    boolean hasRequestsBelow( int floor );

    /**
     * Are there requests at all?
     *
     * @return true if there are requests
     */
    boolean hasRequests();

    /**
     * Return request nearest to floor or floor if no request outside floor. On
     * a tie (distance in both directions is the same), down takes precedence.
     *
     * @param floor from which to calculate
     * @return nearest floor in any direction or current floor if this is only
     * floor.
     */
    int nearestTo( int floor );

    /**
     * Return request nearest above floor.
     *
     * @param fromFloor from which to calculate
     * @return nearest floor in any direction or Integer.MAXVALUE if none
     * exists.
     */
    int nearestAbove( int fromFloor );

    /**
     * Return request nearest below floor not equal to or Integer.MINVALUE if
     * none available.
     *
     * @param fromFloor from which to calculate
     * @return nearest floor in down direction or Integer.MINVALUE if none
     * exists.
     */
    int nearestBelow( int fromFloor );

    /**
     * Get the lowest request for this requestVector. Use to compute the cost of
     * a move.
     *
     * @return -1 if request = all zeros, the bit index of the rightmost bit if
     * not
     */
    int lowestRequest();

    /**
     * Get the lowest request for this requestVector. Use to compute the cost of
     * a move.
     *
     * @return -1 if request = all zeros, the bit index of the leftmost bit if
     * not
     */
    int highestRequest();

    /**
     * Get the underlying property for binding.
     *
     * @return the property
     */
    IntegerProperty bitVectorProperty();

    /**
     * The vector as an integer.
     *
     * @return the bit packed in one integer.
     */
    int get();

    /**
     * set a bitString.
     *
     * @param value to set.
     */
    void set( int value );

    int getFloorCount();

    default BooleanBinding createFloorBinding( final int floor ) {
        return BindingUtils.bindIntegerBit( this.bitVectorProperty(), floor );
    }

    String getName();
}
