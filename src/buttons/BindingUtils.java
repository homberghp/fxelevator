/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttons;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerExpression;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class BindingUtils {

    static BooleanBinding bindIntegerBit( IntegerExpression exp, final int bit ) {
        return new BooleanBinding() {
            {
                super.bind( exp );
            }

            @Override
            protected boolean computeValue() {
                boolean v = ( exp.get() & ( 1 << bit ) ) != 0;
//                System.out.println( "new value ="+v );
                return v;
            }
        };
    }
}
