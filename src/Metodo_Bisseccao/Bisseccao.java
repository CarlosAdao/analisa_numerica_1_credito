/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodo_Bisseccao;

import File.File_;
import com.towel.math.Expression;
import java.io.IOException;
import java.util.ArrayList;

public class Bisseccao {

    static String expressao;
    static int a, b;
    static double precisao;
    static int iteracoes;
    static String directoryName = "Metodo Bisseccao";
    static ArrayList<String> resultado = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        /*Pega os atributos do arquivo de entrada*/
        ArrayList<String> atributos = File_.read(directoryName);
        expressao = atributos.get(0);
        a = Integer.parseInt(atributos.get(1));
        b = Integer.parseInt(atributos.get(2));
        precisao = Float.parseFloat(atributos.get(3));

        iteracoes = Integer.parseInt(calculaQuantidadeIteracoes(a, b, precisao));

        File_.write(calculaBisseccao(iteracoes, a, b), directoryName);
    }

    /*Introduz dentro da função a variavel x*/
    public static double calculaFuncao(double x) {

        Expression exp = new Expression(expressao);
        exp.setVariable("x", x);
        return exp.resolve();
    }

    /*Metodo usado para calcular a quantidade de iterações*/
    public static String calculaQuantidadeIteracoes(int a, int b, double p) {
        String expressao = "(log(" + b + "-" + a + ") - log(" + precisao + "))/log(" + 2 + ")";
        Expression exp = new Expression(expressao);
        exp.setVariable("a", a);
        exp.setVariable("b", b);
        exp.setVariable("p", p);
        return String.valueOf((Math.round(exp.resolve())));
    }

    public static ArrayList<String> calculaBisseccao(int iteracao, double a, double b) {
        char sinal = 0;
        double xo = 0;
        double erro;
        double fa, fb;
        
        String titulo = "[MÉTODO DA BISSECÇÃO]\n";
        String legenda = "\n[It]\t[a]\t\t[b]\t\t[xo]\t\t[fa]\t\t[fb]\t\t[sinal]   [erro]\n";
        resultado.add(titulo);
        resultado.add(legenda);

        for (int i = 0; i <= iteracao; i++) {

            /*Condição executada na primeira iteração*/
            if (i == 0) {
                xo = (a + b) / 2;
                fa = calculaFuncao(a);
                fb = calculaFuncao(xo);
                sinal = defineSinal(fa, fb);
                String r = i + ", " + a + ", " + b + ", " + xo + ", " + fa + ", " + fb + ", " + sinal + ", " + "[--]\n";
                resultado.add(r);

            } else {
                if (verificaSinal(sinal)) {
                    a = xo;
                } else {
                    b = xo;
                }
                xo = (a + b) / 2;
                fa = calculaFuncao(a);
                fb = calculaFuncao(xo);
                sinal = defineSinal(fa, fb);
                erro = b - a;
                String r = i + ", " + a + ", " + b + ", " + xo + ", " + fa + ", " + fb + ", " + sinal + ", " + erro + "\n";
                resultado.add(r);

            }

        }
        resultado.add("\nA raiz aproximada é: " + xo);
        return resultado;
    }

    public static char defineSinal(double fa, double fb) {
        if (fa >= 0 && fb >= 0) {
            return '+';
        } else if (fa <= 0 && fb <= 0) {
            return '+';
        } else {
            return '-';
        }

    }

    /*quando o sinal for positivo retorna verdadeiro, negativo retorna falso*/
    public static boolean verificaSinal(char sinal) {
        if (sinal == '+') {
            return true;
        } else {
            return false;
        }
    }

}
