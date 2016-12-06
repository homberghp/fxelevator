package buttons;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class FXRequestVector implements RequestVector {

    private final IntegerProperty bitVector = new SimpleIntegerProperty( 0 );
    @Override
    public final int get() {
        return bitVector.get();
    }

    @Override
    public final void set( int value ) {
        bitVector.set( value );
    }

    @Override
    public final IntegerProperty bitVectorProperty() {
        return bitVector;
    }

    private static final long ALL_ONES = -1;
    private final int floors;
    private final String name;

    @Override
    public String getName() {
        return name;
    }

    /**
     * Create a named schedule vector implementation.
     *
     * @param name the vector
     * @param maxFloor highest floor to support.
     */
    public FXRequestVector( String name, int maxFloor ) {
        this.name = name;
        floors = maxFloor;
        bitVectorProperty().addListener( new DebugListener());
    }

    /**
     * Create unnamed schedule vector.
     *
     * @param maxFloor highest floor to support.
     */
    public FXRequestVector( int maxFloor ) {
        this( "Unnamed vector", maxFloor );
    }

    @Override
    public void add( int floor ) {
        int bv = get();
        if ( floor <= floors ) {
            set( bv | ( 1 << floor ) );
        }
    }

    @Override
    public void remove( int floor ) {
        int bv = get();
        if ( floor <= floors ) {
            set( bv & ~( 1 << floor ) );
        }
    }

    @Override
    public void removeAll() {
       set( 0 );
    }

    @Override
    public boolean hasRequestFor( int floor ) {
        return ( ( 1 << floor ) & get() ) != 0;
    }

    @Override
    public boolean hasRequestsAbove( int floor ) {
        if ( floor >= floors ) {
            return false;
        }
        long floormask = ALL_ONES << ( floor + 1 );
        int bv = get();
        return ( bv & floormask ) != 0;
    }

    @Override
    public boolean hasRequestsBelow( int floor ) {
        if ( floor < 1 ) {
            return false;
        }
        long floormask = ~( ALL_ONES << ( floor ) );
        int bv = get();
        return ( bv & floormask ) != 0;
    }

    @Override
    public boolean hasRequests() {
        return get() != 0;
    }

    @Override
    public int nearestTo( int fromFloor ) {
        if ( !hasRequests() ) {
            return fromFloor;
        }
        int nearUp = nearestAbove( fromFloor );
        int nearDown = nearestBelow( fromFloor );
        if ( nearUp == Integer.MAX_VALUE ) {
            return nearDown;
        } else if ( nearDown == Integer.MIN_VALUE ) {
            return nearUp;
        } else if ( ( fromFloor - nearDown ) <= ( nearUp - fromFloor ) ) {
            return nearDown;
        } else {
            return nearUp;
        }
    }

    @Override
    public int nearestAbove( int fromFloor ) {
        if ( !hasRequests() || !hasRequestsAbove( fromFloor ) ) {
            return Integer.MAX_VALUE;
        }
        return Integer.numberOfTrailingZeros( get() &(-1 << fromFloor) );
    }

    @Override
    public int nearestBelow( int fromFloor ) {
        if ( !hasRequests() || !hasRequestsBelow( fromFloor ) ) {
            return Integer.MIN_VALUE;
        }
        int mask = ~(-1 << (fromFloor));
        System.out.println( "fromFloor = " + fromFloor);
        System.out.println( "m = "+Integer.toBinaryString( mask ) );
        return 31-Integer.numberOfLeadingZeros( get() & mask );
    }

    private class DebugListener implements ChangeListener<Number> {

        @Override
        public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) {
            System.out.println( "Vector " + name + " changed from "
                    + longAsBitSetString( oldValue.intValue() ) + " to "
                    + longAsBitSetString( newValue.intValue() ) );
        }
    }

    @Override
    public String toString() {
        return longAsBitSetString( get() );
    }

    /**
     * String representation.
     *
     * @param bv bit vector.
     * @return string result.
     */
    static String longAsBitSetString( long bv ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "[" );
        long mask = 1;
        for ( int i = 0; i < 64; i++ ) {
            if ( ( bv & mask ) != 0 ) {
                sb.append( " " ).append( i );
            }
            mask <<= 1;
        }
        sb.append( " ]" );
        return sb.toString();
    }

    /**
     * Get the lowest request for this requestVector. Use to compute the cost of
     * a move.
     *
     * @return -1 if request = all zeros, the bit index of the rightmost bit if
     * not
     */
    @Override
    public int lowestRequest() {
        int c = Long.numberOfTrailingZeros( get() );
        if ( c == 64 ) {
            c = -1;
        }
        return c;
    }

    /**
     * Get the lowest request for this requestVector. Use to compute the cost of
     * a move.
     *
     * @return -1 if request = all zeros, the bit index of the leftmost bit if
     * not
     */
    @Override
    public int highestRequest() {
        int c = Long.numberOfLeadingZeros( get() );
        if ( c == 64 ) {
            c = -1;
        } else {
            c = 64 - 1 - c;
        }
        return c;
    }
    
    @Override 
    public int getFloorCount(){
        return floors;
    }
}
