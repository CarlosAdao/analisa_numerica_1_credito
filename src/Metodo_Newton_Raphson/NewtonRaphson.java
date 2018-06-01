/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Metodo_Newton_Raphson;

import Derivate.Df;
import File.File_;
import com.towel.math.Expression;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class NewtonRaphson {

    static String expressao;
    static String derivada;
    static Double a, b, xn;
    static double precisao;
    static int iteracoes;
    static String directoryName = "Metodo Newton Raphson";
    
    public static void main(String[] args) throws IOException {

        /*Pega os atributos do arquivo de entrada*/
        ArrayList<String> atributos = File_.read(directoryName);
        expressao = atributos.get(0);
        derivada = atributos.get(1);
        
        
        a = Double.parseDouble(atributos.get(2));
        b = Double.parseDouble(atributos.get(3));
        xn = Double.parseDouble(atributos.get(4));
        precisao = Float.parseFloat(atributos.get(5));
        
        File_.write(calculaNewtonRaphson(precisao, a, b, xn), directoryName);
    }
    
    /*Método usado para calcular a função*/
    public static double calculaFuncao(double x) {

        Expression exp = new Expression(expressao);
        exp.setVariable("x", x);
        return exp.resolve();

    }
    
    /*Método usado para calcular a derivada da função*/
    public static double calculaDerivada(double x) {

        derivada = "(6*(x^2))+(6*x)";
        Expression exp = new Expression(derivada);
        exp.setVariable("x", x);
        return exp.resolve();

    }
    
    public static ArrayList<String> calculaNewtonRaphson(double erroAbsoluto, double a, double b, double xn ) {
        iteracoes = 0;
        double fx;
        double DfDx;
        double xn1 = 0;//variavel xn + 1
        double erroRelativo;
        ArrayList<String> resultado = new ArrayList<>();
         
        
        while (true) {
        
            /*Condição executada na primeira iteração*/
            if (iteracoes == 0) {
                
                fx = calculaFuncao(xn);
                DfDx = calculaDerivada(xn);
                xn1 = xn - (fx/DfDx);
                erroRelativo = xn - xn1;
                String r = "Iteração: "+ iteracoes +"   [ " +xn + ", " + fx + ", " + DfDx +", " + xn1 +", " +"[--]" +" ]\n";
                resultado.add(r);
                
            } else {
                
                xn = xn1;
                fx = calculaFuncao(xn);
                DfDx = calculaDerivada(xn);
                xn1 = xn - (fx/DfDx);
                erroRelativo = xn - xn1;
                
                String r = "Iteração: "+ iteracoes +"   [ " +xn + ", " + fx + ", " + DfDx +", " + xn1 +", " +erroRelativo +" ]\n";
                resultado.add(r);
                
                if (Float.parseFloat(String.valueOf(Math.abs(erroRelativo))) < erroAbsoluto) {
                    break;
                }
            }
            iteracoes++;
        }
        
        return resultado;
    }
}
