/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodo_Gauss_Seidel;

import Auxiliar.Action;
import File.File_;
import static Metodo_Jacobi.Jacobi.calculaVectorInicial;
import static Metodo_Jacobi.Jacobi.encontraMaiorElemento;
import static Metodo_Jacobi.Jacobi.printMatriz;
import static Metodo_Jacobi.Jacobi.printVector;
import static Metodo_Jacobi.Jacobi.verificaCriterioColuna;
import static Metodo_Jacobi.Jacobi.verificaCriterioLinha;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author estagio-nit
 */
public class GausSeidel {

    static String directoryName = "Metodo de Gaus Seidel";
    static double[][] matriz;
    static int linhas;
    static int colunas;
    static double precisao;
    static String titulo;
    static ArrayList<String> resultado = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        double[] vetxk;
        double[] vetxk1;
        double[] vetEntrada;

        linhas = File_.getSizeLine(directoryName);
        colunas = File_.getSizeCol(directoryName);
        matriz = File_.readMatriz(directoryName);
        precisao = File_.readErro(directoryName);

        String nome_metodo = "SAIDA - MÉTODO GAUS SEIDEL\n";
        resultado.add(nome_metodo);

        matriz = File_.readMatriz(directoryName);

        titulo = "Matriz de Gaus Seidel";
        printMatriz(matriz, linhas, colunas, titulo);
        System.out.println("");

        /*verifica se a matriz converge pelo criterio de sanssenfeld*/
        System.out.println("\nCriterio Sassenfeld: "+verificaCriterioSassenfeld(matriz, linhas, colunas));

        /*verifica se a matriz é diagonalmente dominante pelo critério das linhas*/
        System.out.println("Criterio das Linhas: "+verificaCriterioLinha(matriz, linhas, colunas));

        /*verifica se a matriz é diagonalmente dominante pelo critério das colunas*/
        System.out.println("Criterio das Colunas: "+verificaCriterioColuna(matriz, linhas, colunas));

        vetEntrada = File_.readVector(directoryName);
        vetxk = calculaVectorInicial(matriz, linhas, colunas, vetEntrada);//Vetor com o erro inicial 
        vetEntrada = calculaVectorInicial(matriz, linhas, colunas, vetEntrada);

        int iteracao = 1;//variável usada para guardar a quantidade de iterações 
        while (true) {
            double aux[] = new double[linhas];//Vetor usado para armazenar o vetor das subtrações
            vetxk1 = isolaX(matriz, linhas, colunas, vetEntrada);//vetor que guarda o erro apos iterações

            for (int i = 0; i < vetEntrada.length; i++) {
                aux[i] = Action.arredonda((vetxk1[i] - vetEntrada[i]));
            }

            double dividendo = encontraMaiorElemento(aux);// após a subtração entre os dois vetores guarda o maior elemento em modulo   
            double divisor = encontraMaiorElemento(vetxk1);//guarda o maior valor em modulo do vetor de resultado k iteração
            double erro = dividendo / divisor;//obtem o erro a cada iteração
           
            /* atualiza o novo vetor solução*/
            System.arraycopy(vetxk1, 0, vetEntrada, 0, vetxk1.length);

            //verifica se o erro relativo encontrado é menor que a precisão
            if (erro < precisao) {
                System.out.println("\nCom a quantidade de iterações: " + iteracao + " com um Erro de : " + erro);
                break;
            }

            iteracao++;
        }
        printVector(vetxk1, "Vetor solução");
    }

    /*Método usado para isolar o x de cada linha da matriz com o vetor*/
    public static double[] isolaX(double[][] matriz, int linhas, int colunas, double[] vetEntrada) {

        double veta = 0;
        double vetb = 0;
        double divisor = 0;
        double[] vectorResultadoSistema = File_.readVector(directoryName);//Guarda o vetor que que tem o resultado do sistema inicial
        double[] vectorAux = new double[linhas];

        for (int i = 0; i < linhas; i++) {
            vetb = vectorResultadoSistema[i];
            for (int j = 0; j < colunas; j++) {
                if (i == j) {
                    divisor = matriz[i][j];
                } else {
                    double m;
                    /*Esse if  execulta a pricipal diferença em  relação  ao     Método   de Jacobi, 
                     *ele faz com que seja utilizado o x atualizado para convergir mais rápidamente
                     */
                    if (j < i) {
                        m = (matriz[i][j] * vectorAux[j]);
                    } else {
                        m = (matriz[i][j] * vetEntrada[j]);
                    }
                    vetb = vetb - Action.arredonda(m);
                }
            }
            vectorAux[i] = Action.arredonda(vetb / divisor);
        }
        return vectorAux;
    }

    /*Método usado para verificar o criterio de coluna*/
    public static boolean verificaCriterioSassenfeld(double[][] matriz, int linhas, int colunas) {
        
        double divisor = 0;
        double somaColuna;
        double[] vectorAux = new double[linhas];

        for (int i = 0; i < linhas; i++) {
            somaColuna = 0;
            for (int j = 0; j < colunas; j++) {
                if (i == j) {
                    divisor = matriz[i][j];
                } else if (j > i) {

                    double nun = matriz[i][j];
                    somaColuna = somaColuna + nun;

                } else if (j < i) {

                    double nun = matriz[i][j] * vectorAux[j];
                    somaColuna = somaColuna + nun;
                }
            }
            
            vectorAux[i] = somaColuna / divisor;
        }

        /*verifica se o maior beta é menor que 1 caso seja verdadeiro a função converge*/
        return encontraMaiorElemento(vectorAux) < 1;
        
    }

}
