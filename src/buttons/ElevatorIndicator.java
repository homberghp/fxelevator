package buttons;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

/**
 * Simple styled button with a boolean property that will turn the light on or
 * off.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ElevatorIndicator extends Label {

    private final BooleanProperty lightOn = new SimpleBooleanProperty( false );
    final int floor;
    private RequestVector vector = null;

    public boolean isLightOn() {
        return lightOn.get();
    }

    public ElevatorIndicator( int floor, RequestVector vector ) {
        super( "" + floor );
        this.vector=vector;
        this.floor = floor;
        initialize();
    }

    final void initialize() {
        getStyleClass().addAll( "indicator-label", "off" );
        if ( vector != null ) {
            BooleanBinding bb = vector.createFloorBinding( floor );
            this.lightOn.bind( bb );
        }
        lightOn.addListener( this::changed );

    }

    private void changed( ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue ) {
        if ( newValue ) {
            getStyleClass().remove( "off" );
            getStyleClass().add( "lit" );
        } else {
            getStyleClass().remove( "lit" );
            getStyleClass().add( "off" );

        }

    }

    public void toggle() {
        lightOn.set( !lightOn.get() );
    }
}
