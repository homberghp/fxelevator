package buttons;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.HBox;

/**
 * A simple floor indicator.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class FloorIndictor extends HBox {

    final ElevatorButton[] indicators;
    private final IntegerProperty indicatorBits = new SimpleIntegerProperty(1);
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
            ElevatorButton b = new ElevatorButton( i );
            b.onOffProperty().bind(BindingUtils.bindIntegerBit( indicatorBits, i ));
            indicators[ floors-1-i ] = b;
            
        }
        this.getChildren().addAll( indicators );
    }

    private int onMask = 1;
    private int floor = 0;
    private boolean between=false;
    void setFloor( int f ) {
        clearLights();
        f %= indicators.length;
        onMask = 1 << f;
        set(onMask);

        floor = f;
        between=false;
    }

    private void setBetweenFloors( int f, int g ) {
        f %= indicators.length;
        g %= indicators.length;
        between=true;
        onMask = ( 1 << f ) | ( 1  << g  );
        set(onMask);
        floor = f;
    }

    private void clearLights() {
        set(onMask = 0);
        floor=0;
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

    private void set( int m ) {
        indicatorBits.set(m);
    }

    public boolean isBetween() {
        return between;
    }
}
