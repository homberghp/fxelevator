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
        final ElevatorButton btn1 = new ElevatorButton( 1 );
        final ElevatorButton btn2 = new ElevatorButton( 2 );
        final ElevatorButton[] btn = new ElevatorButton[ n ];
        final FXRequestVector vec = new FXRequestVector( n );
        final FXRequestVector upVec = new FXRequestVector( n );
        final FXRequestVector downVec = new FXRequestVector( n );
        final RequestMonitor mon = new RequestMonitor( vec );
        final RequestMonitor upmon = new RequestMonitor( upVec );
        final RequestMonitor downMon = new RequestMonitor( downVec );
        final FloorPanel[] floorPanels = new FloorPanel[ n ];
        final VBox bld = new VBox();
        for ( int f = 0; f < n; f++ ) {

            final FloorPanel floorPanel = new FloorPanel( f, upVec, downVec );
            floorPanels[ f ] = floorPanel;
        }
        bld.getChildren().addAll( floorPanels );
        
        for ( int i = 0; i < btn.length; i++ ) {
            final ElevatorButton b = new ElevatorButton( i );
            b.setOnAction( e -> {
                final ElevatorButton source = ( ElevatorButton ) e.getSource();
                source.toggle();
                if ( source.isLightOn() ) {
                    vec.add( source.floor );
                } else {
                    vec.remove( source.floor );
                }
            } );
            btn[ btn.length - 1 - i ] = b;
        }

        final ElevatorButton up = new ElevatorButton( "\u22b2", 0 );
        up.rotateProperty().set( 90 );
        final ElevatorButton down = new ElevatorButton( "\u22b2", 0 );
        down.rotateProperty().set( -90 );
        final FloorIndictor fi = new FloorIndictor( 12, 4.0 );
        //btn.setText( "Say 'Hello World'" );
        btn1.setLightOn( true );
        btn1.setLightOn( false );
        int f = 0;
        final BackgroundFill y = new BackgroundFill( Color.YELLOW, null, null );
        Background bg = new Background( y );
        btn1.setOnAction( ( ActionEvent event ) -> {
            System.out.println( "Hello btn1!" );
            btn2.setLightOn( !btn2.isLightOn() );
            fi.goUp();
        } );
        btn2.setOnAction( ( ActionEvent event ) -> {
            System.out.println( "Hello btn2!" );
            btn1.setLightOn( !btn1.isLightOn() );
            fi.goDown();
        } );

        up.setOnAction( ( ActionEvent event ) -> {
            up.setLightOn( !up.isLightOn() );
            fi.moveUpFrom( fi.getFloor() );
        } );
        down.setOnAction( ( ActionEvent event ) -> {
            down.setLightOn( !down.isLightOn() );
            fi.moveDownFrom( fi.getFloor() );
        } );

        VBox root = new VBox();
        HBox hbox = new HBox();
        VBox vbox2 = new VBox();
        root.setId( "root" );
        System.out.println( "root.getId() = " + root.getId() );
        vbox2.getChildren().addAll( btn );
        hbox.getChildren().addAll( bld,vbox2, mon );
        root.getChildren().addAll( btn1, btn2, fi, up, down, hbox );

        Scene scene = new Scene( root, 300, 250 );
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
