/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auxiliar;

import java.math.BigDecimal;

/**
 *
 * @author carlos
 */
public class Action {

    public static double arredonda(Double num) {

        int decimalPlace = 4;
        BigDecimal bd = new BigDecimal(num);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        num = bd.doubleValue();
        return num;
    }

}
