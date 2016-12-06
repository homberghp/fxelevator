package buttons;

import javafx.scene.layout.HBox;

/**
 * A simple floor indicator.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class FloorIndictor extends HBox {

    final ElevatorButton[] indicators;

    public FloorIndictor( int floors ) {
        indicators = new ElevatorButton[ floors ];
        initialize( floors );
    }

    public FloorIndictor( int floors, double spacing ) {
        super( spacing );
        indicators = new ElevatorButton[ floors ];
        initialize( floors );
    }

    final void initialize( int floors ) {
        for ( int i = 0; i < floors; i++ ) {
            indicators[ floors-1-i ] = new ElevatorButton( floors-1-i );
        }
        this.getChildren().addAll( indicators );

        this.indicators[ 0 ].setLightOn();
    }

    private int onMask = 1;
    private int floor = 0;

    void setFloor( int f ) {
        clearLights();
        f %= indicators.length;
        onMask = 1 << f;
        indicators[ f ].setLightOn( true );
        floor = f;
        System.out.println( "floor = " + floor );
    }

    private void setBetweenFloors( int f, int g ) {
        f %= indicators.length;
        g %= indicators.length;
        clearLights();
        indicators[ f ].setLightOn();
        indicators[ g ].setLightOn();
        onMask = ( 1 << f ) | ( 1  << g  );
        floor = f;
        System.out.println( "f = " + f );
        System.out.println( "g = " + g );
        System.out.println( "floor = " + floor );
    }

    private void clearLights() {
        int m = onMask;
        int i = Integer.numberOfTrailingZeros( m );
        while ( m != 0 && i < indicators.length ) {
            indicators[ i ].setLightOff();
            m &= ~( 1 << i );
            i = Integer.numberOfTrailingZeros( m );
        }
        onMask = 0;
    }

    public int getFloor() {
        return floor;
    }

    public int goUp() {
        setFloor( floor + 1 );
        return floor;
    }

    public int goDown() {
        setFloor( floor + indicators.length - 1 );
        return floor;
    }

    public void moveUpFrom( int f ) {
        setBetweenFloors( f, f + 1 );
    }

    public void moveDownFrom( int f ) {
        setBetweenFloors( f, f + indicators.length - 1 );
    }
}
