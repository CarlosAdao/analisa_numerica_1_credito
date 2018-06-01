package Metodo_Falsa_Posicao;

/**
 *
 * @author carlos
 */

import File.File_;
import com.towel.math.Expression;
import java.io.IOException;
import java.util.ArrayList;

public class FalsaPosicao {

    static String expressao;
    static Double a, b;
    static double precisao;
    static int iteracoes;
    static String directoryName = "Metodo Falsa Posicao";
    static ArrayList<String> resultado = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        
        String metodo = "SAIDA - MÉTODO FALSA POSICÃO\n";
        resultado.add(metodo);

        /*Pega os atributos do arquivo de entrada*/
        ArrayList<String> atributos = File_.read(directoryName);
        expressao = atributos.get(0);
        a = Double.parseDouble(atributos.get(1));
        b = Double.parseDouble(atributos.get(2));
        precisao = Float.parseFloat(atributos.get(3));

        File_.write(calculaFalsaPosicao(precisao, a, b),directoryName);
    }

    /*Introduz dentro da função a variavel x*/
    public static double calculaFuncao(double x) {

        Expression exp = new Expression(expressao);
        exp.setVariable("x", x);
        return exp.resolve();

    }

    public static ArrayList<String> calculaFalsaPosicao(double erroAbsoluto, double a, double b) {
        char sinal = 0;
        double xo = 0;
        double fa, fb;
        double erroRelativo;
        
        String legenda = "\n[It]\t[a]\t\t[b]\t\t[xo]\t\t[fa]\t\t[fb]\t\t[sinal]   [erro]\n";
        resultado.add(legenda);

        int i = 0;
        while (true) {

            /*Condição executada na primeira iteração*/
            if (i == 0) {

                fa = calculaFuncao(a);
                fb = calculaFuncao(b);
                xo = ((a * fb) - (b * fa)) / (fb - fa);
                erroRelativo = calculaFuncao(xo);
                sinal = defineSinal(fa, fb);
                
                
                String r =  i+", "+ a + ", " + fa + ", " + b + ", " + fb + ", " + xo + ", " + sinal + ", " + erroRelativo+"\n";
                resultado.add(r);
                
            } else {
                if (verificaSinal(sinal)) {
                    a = xo;
                } else {
                    b = xo;
                }
                fa = calculaFuncao(a);
                fb = calculaFuncao(b);
                xo = ((a * fb) - (b * fa)) / (fb - fa);
                erroRelativo = calculaFuncao(xo);
                sinal = defineSinal(fa, fb);

                String r = i+", "+ a + ", " + fa + ", " + b + ", " + fb + ", " + xo + ", " + sinal + ", " + erroRelativo+"\n";
                resultado.add(r);

                if (Float.parseFloat(String.valueOf(Math.abs(erroRelativo))) < erroAbsoluto) {
                    break;
                }
            }
            i++;
        }
        String saida = "\nRaiz aproximada: "+ xo;
        resultado.add(saida);
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
        return sinal == '+';
    }

}
