/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodo_Secante;

import File.File_;
import com.towel.math.Expression;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class Secante {

    static String expressao;
    static String derivada;
    static Double a, b, xn;
    static double precisao;
    static int iteracoes;
    static String directoryName = "Metodo Secante";

    public static void main(String[] args) throws IOException {

        /*Pega os atributos do arquivo de entrada*/
        ArrayList<String> atributos = File_.read(directoryName);
        expressao = atributos.get(0);
        a = Double.parseDouble(atributos.get(1));
        b = Double.parseDouble(atributos.get(2));
        precisao = Float.parseFloat(atributos.get(3));

        File_.write(calculaSecante(precisao, a, b), directoryName);
    }

    /*Método usado para calcular a função*/
    public static double calculaFuncao(double x) {

        Expression exp = new Expression(expressao);
        exp.setVariable("x", x);
        return exp.resolve();

    }

    public static ArrayList<String> calculaSecante(double erroAbsoluto, double a, double b) {
        iteracoes = 0;
        double fx0 = 0;
        double fx1 = 0;
        double x0 = 0;//variavel xn + 1
        double x1 = 0;
        double erroRelativo = 0;
        ArrayList<String> resultado = new ArrayList<>();

        while (iteracoes < 5) {

            /*Condição executada na primeira iteração*/
            if (iteracoes == 0) {
                x0 = a;
                fx0 = calculaFuncao(x0);

                String r = "Iteração: " + iteracoes + "   [ " + x0 + ", " + fx0 + ",  [--]" + " ]\n";
                resultado.add(r);

            } else if (iteracoes == 1) {
                x1 = b;
                fx1 = calculaFuncao(x1);

                String r = "Iteração: " + iteracoes + "   [ " + x1 + ", " + fx1 + ", [--]" + " ]\n";
                resultado.add(r);

            } else {
                double aux = x1;

                x1 = ((x1) - (((fx1) * ((x1) - (x0))) / ((fx1) - (fx0))));//formula da secante
                fx1 = calculaFuncao(x1);

                x0 = aux;
                fx0 = calculaFuncao(x0);

                String r = "Iteração: " + iteracoes + "   [ " + x1 + ", " + fx1 + ", [--]" + " ]\n";
                resultado.add(r);
                if (Float.parseFloat(String.valueOf(Math.abs(fx1))) < erroAbsoluto) {
                    break;
                }
            }

            iteracoes++;
        }
        
        resultado.add("Raiz aproximada: " + x1);
        
        return resultado;
    }
}
