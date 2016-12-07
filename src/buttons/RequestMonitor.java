package buttons;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A simple floor indicator.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class RequestMonitor extends HBox {

    final ElevatorIndicator[] indicators;
    final RequestVector vector;
    final String style;
    public RequestMonitor( RequestVector vector, String style ) {
        this.vector = vector;
        this.style=style;
        indicators = new ElevatorIndicator[ vector.getFloorCount() ];
        getChildren().add( new Label( vector.getName() ) );
        initialize();
    }

    public RequestMonitor( RequestVector vector ) {
        this(vector,"lit");
    }
    

    final void initialize() {
        int floors = indicators.length;
        for ( int i = 0; i < floors; i++ ) {
            indicators[ floors - 1 - i ] = new ElevatorIndicator( i, vector.bitVectorProperty() ).setLitStyleClass( style );
        }
        this.getChildren().addAll( indicators );
    }
}
