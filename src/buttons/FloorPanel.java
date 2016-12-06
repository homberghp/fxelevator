/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class FloorPanel extends VBox {

    final RequestVector upVector;
    final RequestVector downVector;
    final int floor;
    public FloorPanel( int floor, RequestVector upVector, RequestVector downVector ) {
        this.floor=floor;
        this.upVector = upVector;
        this.downVector = downVector;
        initialize();
    }

    
    final void initialize() {
        ElevatorButton up = createUpButton( floor );
        ElevatorButton down =createDownButton( floor );
        getChildren().addAll(up,down);
    }

    private ElevatorButton createDownButton(int fl) {
        final ElevatorButton down = new ElevatorButton( "\u22b2", fl );
        down.rotateProperty().set( -90 );
        down.setOnAction( ( ActionEvent event ) -> {
            ElevatorButton b = (ElevatorButton) event.getSource();
            downVector.add(b.getFloor());
        } );
        down.onOffProperty().bind(downVector.createFloorBinding( fl ) );
        return down;
    }

    private ElevatorButton createUpButton(int fl ) {
        final ElevatorButton up = new ElevatorButton( "\u22b2", fl );
        up.rotateProperty().set( 90 );
        up.setOnAction( ( ActionEvent event ) -> {
            ElevatorButton b = (ElevatorButton) event.getSource();
            upVector.add(b.getFloor());
        } );
        up.onOffProperty().bind(upVector.createFloorBinding( fl ) );
        return up;
    }

}
