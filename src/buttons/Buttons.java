package buttons;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class Buttons extends Application {

    @Override
    public void start( Stage primaryStage ) {
        int n = 8;
        final ElevatorButton[] btn = new ElevatorButton[ n ];
        final FXRequestVector upVec = new FXRequestVector( "UP requests", n );
        final FXRequestVector downVec = new FXRequestVector( "DW requests", n );
        final FXRequestVector vec = new FXRequestVector( "TG requests", n );
        final RequestMonitor mon = new RequestMonitor( vec, "lit-red" );
        final RequestMonitor upMon = new RequestMonitor( upVec );
        final RequestMonitor downMon = new RequestMonitor( downVec, "lit-blue" );
        final FloorPanel[] floorPanels = new FloorPanel[ n ];
        final VBox bld = new VBox();
        for ( int f = 0; f < n; f++ ) {

            final FloorPanel floorPanel = new FloorPanel( f, upVec, downVec );
            floorPanels[ n - 1 - f ] = floorPanel;
            final ElevatorButton b = new ElevatorButton( f );
            b.setOnAction( e -> {
                final ElevatorButton source = ( ElevatorButton ) e.getSource();
                vec.add( source.getFloor());
            } );
            b.onOffProperty().bind(BindingUtils.bindIntegerBit( vec.bitVectorProperty(), f) );
            btn[ n - 1 - f ] = b;
            floorPanel.add( b, 1, 1 );
        }
        bld.getChildren().addAll( floorPanels );

        final ElevatorButton up = new ElevatorButton( "\u22b2", 0 );
        up.rotateProperty().set( 90 );
        final ElevatorButton down = new ElevatorButton( "\u22b2", 0 );
        down.rotateProperty().set( -90 );
        final FloorIndictor fi = new FloorIndictor( n, 4.0 );
        //btn.setText( "Say 'Hello World'" );
        int f = 0;
        final BackgroundFill y = new BackgroundFill( Color.YELLOW, null, null );
        Background bg = new Background( y );

        up.setOnAction( ( ActionEvent event ) -> {
            if ( fi.isBetween() ) {
                fi.goUp();
            } else {
                fi.moveUpFrom( fi.getFloor() );
            }
            up.toggle();
            int visiting = fi.getFloor();
            vec.remove( visiting );
            upVec.remove( visiting );
        } );
        down.setOnAction( ( ActionEvent event ) -> {
            if ( fi.isBetween() ) {
                fi.goDown();
            } else {
                fi.moveDownFrom( fi.getFloor() );
            }
            down.toggle();
            int visiting = fi.getFloor();
            vec.remove( visiting );
            downVec.remove( visiting );
        } );

        VBox root = new VBox();
        HBox hbox = new HBox();
        HBox hbox2 = new HBox();
        root.setId( "root" );
        hbox.getChildren().addAll( bld);
        Region r1 = new Region();
        hbox2.setHgrow( r1, Priority.ALWAYS);
        Region r2 = new Region();
        hbox2.setHgrow( r2, Priority.ALWAYS);
        hbox2.getChildren().addAll(up,r1,fi,r2,down);
        
        root.getChildren().addAll( mon, upMon, downMon,hbox2,hbox );

        Scene scene = new Scene( root, 300, 650 );
        scene.getStylesheets().add( "buttons/style.css" );

        primaryStage.setTitle( "Hello World!" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) {
        launch( args );
    }

}
