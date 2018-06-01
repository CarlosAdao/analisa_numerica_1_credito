/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodo_Eliminacao_Gauss;

import Auxiliar.Action;
import File.File_;
import static Metodo_Fatoracao_LU.Fatoracao_LU.printMatriz;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class Eliminacao_Gauss {

    static String directoryName = "Metodo Eliminacao Gauss";
    static double[][] matriz;
    static int linhas;
    static int colunas;
    static String[] solucoes = {"v", "v", "v"};//vetor auxiliar usado para guarda as soluções
    static ArrayList<String> resultado = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        linhas = File_.getSizeLine(directoryName);
        colunas = File_.getSizeCol(directoryName);
        matriz = File_.readMatriz(directoryName);
        String titulo;
        
        String metodo = "SAIDA - MÉTODO DA ELIMINAÇÃO DE GAUSS\n";
        resultado.add(metodo);
        /*Chama o método para para zera a trinagulação inferior*/
        calculaEliminacaoGaus(matriz, linhas, colunas);

        titulo = "Matriz com diagonal inferio Zerada";
        printMatriz(matriz, linhas, colunas, titulo);

        /*Depois de encontradoz a matriz aumantada encontra o vetor solução*/
        encontraVetorSolucoes(matriz);
        
        /*Chama o método para imprimir a matri*/
        titulo = "\nVetor Solucão";
        printVector(solucoes, titulo);
        
        File_.write(resultado, directoryName);
        
    }

    /*inicia a execução do método*/
    public static void calculaEliminacaoGaus(double[][] matriz, int lin, int col) {
        double pivo;

        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                if ((i == j && i == j) && (j < colunas - 2)) {
                    pivo = matriz[i][j];//Encontra o pivô da linha 
                    calculaMultiplicador(pivo, i, j, matriz);
                }
            }
        }
    }

    /*Calcula o multiplicar de cada linha*/
    public static void calculaMultiplicador(double pivo, int l, int c, double[][] matriz) {

        for (int i = l + 1; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (j == c) {

                    double elemento_linha = Double.parseDouble(String.valueOf(matriz[i][j]));
                    double pv = Double.parseDouble(String.valueOf(pivo));
                    double mult = elemento_linha / pv;

                    /*Chama o método para atualizar a linha*/
                    atualizaLinha(i, l, mult, matriz);
                }
            }
        }
    }

    /*Depois de encontrar o pivô atualiza a linha*/
    public static void atualizaLinha(int linha_atualizar, int linha_pivo, double mult, double[][] matriz) {

        for (int i = 0; i < colunas; i++) {
            matriz[linha_atualizar][i] = matriz[linha_atualizar][i] - mult * matriz[linha_pivo][i];
        }
    }

    /*Após encontrar a matriz com a digonal trinangular zerada resolve o sistema*/
    public static void encontraVetorSolucoes(double[][] matriz) {
        int aux_linhas = (linhas - 1);
        int aux_colunas = (colunas - 2);
        int utima_colunas = (colunas - 1);

        while (aux_linhas >= 0) {
            if (aux_linhas == (linhas - 1)) {
                double veta = matriz[aux_linhas][aux_colunas];
                double vetb = matriz[aux_linhas][utima_colunas];
                double num = vetb / veta;
                solucoes[aux_linhas] = String.valueOf(Math.round(num));

            } else {
                double vetb = matriz[aux_linhas][utima_colunas];
                soma(aux_colunas, utima_colunas, aux_linhas, matriz, vetb);
            }

            aux_colunas--;
            aux_linhas--;
        }
    }

    /*Método usado para fazer a soma da linha junto com a multiplicação do resultado do x1,x2,x3*/
    public static void soma(int inicio, int fim, int linha, double[][] matriz, double vetb) {

        double veta = 0;
        double result = 0;
        double divisor = 0;

        for (int i = inicio; i < fim; i++) {

            if (!(solucoes[i].equalsIgnoreCase("v"))) {

                double a = matriz[linha][i] * Double.parseDouble(solucoes[i]);

                veta += a;
            } else {
                divisor = matriz[linha][i];
            }

        }
        result = (vetb - veta) / divisor;
        solucoes[linha] = String.valueOf(result);
    }
    
    public static void printMatriz(double[][] matriz, int linhas, int colunas, String titulo) {
        String t = "\n" + titulo;
        String r;
        System.out.println(t);
        resultado.add(t);
        for (int i = 0; i < linhas; i++) {
            System.out.println("");
            resultado.add("\n");
            for (int j = 0; j < colunas; j++) {
                r = "[" + Action.arredonda(matriz[i][j]) + "]";
                resultado.add(r);
                System.out.print(r);
            }
        }
    }
     public static void printVector(String[] vector, String titulo) {
        String t = "\n" + titulo;
        String r;
        resultado.add(t);
        System.out.println("\n" + titulo);

        for (String s : vector) {
            r = "[" + s + "]";
            resultado.add(r);
            System.out.println(r);
        }
    }

}
