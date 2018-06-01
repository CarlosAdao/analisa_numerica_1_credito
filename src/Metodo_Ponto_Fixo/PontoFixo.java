/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodo_Ponto_Fixo;

import File.File_;
import com.towel.math.Expression;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class PontoFixo {

    static String expressao;
    static String expressao_isolada;
    static Double a, b;
    static double precisao;
    static int iteracoes;
    static String directoryName = "Metodo Ponto Fixo";
    static ArrayList<String> resultado = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        
        String metodo = "SAIDA - MÉTODO PONTO FIXO\n";
        resultado.add(metodo);

        /*Pega os atributos do arquivo de entrada*/
        ArrayList<String> atributos = File_.read(directoryName);
        expressao = atributos.get(0);
        expressao_isolada = atributos.get(1);
        a = Double.parseDouble(atributos.get(2));
        b = Double.parseDouble(atributos.get(3));
        precisao = Float.parseFloat(atributos.get(4));
     
        File_.write(calculaPontoFixo(precisao, a, b), directoryName);
    }
    

    /*Método usado para calcular a função*/
    public static double calculaFuncao(double x) {

        Expression exp = new Expression(expressao);
        exp.setVariable("x", x);
        return exp.resolve();

    }
    
    /*Método usado para calcular a função isolada*/
    public static double calculaFuncaoIsolada(double x) {

        Expression exp = new Expression(expressao_isolada);
        exp.setVariable("x", x);
        return exp.resolve();

    }
    
     public static ArrayList<String> calculaPontoFixo(double erroAbsoluto, double a, double b) {
        char sinal = 0;
        double xo = 0;
        double fxo;
        double erroRelativo;
        
        String legenda = "\n[It]\t[x0]\t\t[f(x0)]\n";
        resultado.add(legenda);
        
        int i = 0;
        while (true) {
        
            /*Condição executada na primeira iteração*/
            if (i == 0) {
                
                xo = calculaChuteInicial(a, b);
                fxo = calculaFuncao(xo);
                
                String r = i +"   [ " + xo + ", " + fxo +" ]\n";
                resultado.add(r);
                
            } else {
                
                xo = calculaFuncaoIsolada(xo);
                fxo = calculaFuncao(xo);
                
                String r = i +"   [ " + xo + ", " + fxo +" ]\n";
                resultado.add(r);

                if (Float.parseFloat(String.valueOf(Math.abs(fxo))) < erroAbsoluto) {
                    break;
                }
            }
            i++;
        }
        
        String saida = "\nRaiz aproximada: "+ xo;
        resultado.add(saida);
        
        return resultado;
    }
     
     /*Pega um valor para ser usado como chute inicial, por convenção estou pegando o meio do intervalo*/
    public static double calculaChuteInicial(double a, double b) {
        return (a+b)/2;
    }

}
