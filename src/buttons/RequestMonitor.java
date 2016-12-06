package buttons;

import javafx.scene.layout.VBox;

/**
 * A simple floor indicator.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class RequestMonitor extends VBox {

    final ElevatorIndicator[] indicators;
    final RequestVector vector;

    public RequestMonitor( RequestVector vector ) {
        this.vector = vector;
        indicators = new ElevatorIndicator[ vector.getFloorCount() ];
        initialize();
    }

    final void initialize() {
        for ( int i = 0; i < indicators.length; i++ ) {
            indicators[ i ] = new ElevatorIndicator( i, vector );
        }
        this.getChildren().addAll( indicators );
    }
}
