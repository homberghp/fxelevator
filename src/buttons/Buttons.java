package buttons;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
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
        final FXRequestVector upVec = new     FXRequestVector("UP requests", n );
        final FXRequestVector downVec = new   FXRequestVector("DW requests", n );
        final FXRequestVector vec = new FXRequestVector("TG requests", n );
        final RequestMonitor mon = new RequestMonitor( vec );
        final RequestMonitor upMon = new RequestMonitor( upVec );
        final RequestMonitor downMon = new RequestMonitor( downVec, "lit-blue");
        final FloorPanel[] floorPanels = new FloorPanel[ n ];
        final VBox bld = new VBox();
        for ( int f = 0; f < n; f++ ) {

            final FloorPanel floorPanel = new FloorPanel( f, upVec, downVec );
            floorPanels[  n - 1 -f ] = floorPanel;
        }
        bld.getChildren().addAll( floorPanels );
        
        for ( int i = 0; i < btn.length; i++ ) {
            final ElevatorButton b = new ElevatorButton( i );
            b.setOnAction( e -> {
                final ElevatorButton source = ( ElevatorButton ) e.getSource();
                source.toggle();
                if ( source.isLightOn() ) {
                    vec.add( source.getFloor() );
                } else {
                    vec.remove( source.getFloor() );
                }
            } );
            btn[ n - 1 - i ] = b;
        }

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
            System.out.println( "Hello btn1!" );
            fi.goUp();
            int visiting= fi.getFloor();
            vec.remove( visiting );
            upVec.remove( visiting );
        } );
        down.setOnAction( ( ActionEvent event ) -> {
            System.out.println( "Hello btn2!" );
            fi.goDown();
            int visiting= fi.getFloor();
            vec.remove( visiting );
            downVec.remove( visiting );
        } );

        VBox root = new VBox();
        HBox hbox = new HBox();
        VBox vbox2 = new VBox();
        root.setId( "root" );
        System.out.println( "root.getId() = " + root.getId() );
        vbox2.getChildren().addAll( btn );
        hbox.getChildren().addAll( bld,vbox2);
        root.getChildren().addAll( mon ,upMon,downMon, fi, up, down, hbox );

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
