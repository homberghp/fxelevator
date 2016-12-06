package buttons;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Simple styled button with a boolean property that will turn the light on or
 * off.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ElevatorButton extends Button {

    private final BooleanProperty lightOn = new SimpleBooleanProperty( false );
    private final int floor;

    public int getFloor() {
        return floor;
    }

    public boolean isLightOn() {
        return lightOn.get();
    }

    public void setLightOn( boolean value ) {
        lightOn.set( value );
    }

    public void setLightOn() {
        lightOn.set( true );
    }

    public void setLightOff() {
        lightOn.set( false );
    }

    public BooleanProperty onOffProperty() {
        return lightOn;
    }

    public ElevatorButton( int floor ) {
        this( "" + floor, floor );
        initialize();
    }

    public ElevatorButton( String text, int floor ) {
        super( text );
        this.floor = floor;
        initialize();
    }

    public ElevatorButton( String text, Node graphic, int floor ) {
        super( text, graphic );
        this.floor = floor;
        initialize();
    }

    final void initialize() {
        getStyleClass().addAll( "elevator-button", "off" );
        lightOn.addListener( this::changed);
    }

    private void changed( ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue ) {
        if ( newValue ) {
            getStyleClass().remove( "off" );
            getStyleClass().add( "on" );
        } else {
            getStyleClass().remove( "on" );
            getStyleClass().add( "off" );

        }

    }

    public void toggle() {
        lightOn.set( !lightOn.get() );
    }
}
