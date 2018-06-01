package Metodo_Fatoracao_LU;

import Auxiliar.Action;
import File.File_;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class Fatoracao_LU {

    static String directoryName = "Metodo Fatoracao LU";
    static double[][] matrizU;
    static double[][] matrizL;
    static int linhas;
    static int colunas;
    static String[] vxSolucao = {"v", "v", "v"};//vetor auxiliar usado para guarda as soluções
    static String[] vySolucao = {"v", "v", "v"};//vetor auxiliar usado para guarda as soluções
    static double[] vector;
    static ArrayList<String> resultado = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        linhas = File_.getSizeLine(directoryName);
        colunas = File_.getSizeCol(directoryName);
        matrizU = File_.readMatriz(directoryName);
        matrizL = new double[linhas][colunas];
        vector = File_.readVector(directoryName);
        String titulo;

        inicializaMatriL();
        encontraPivo(matrizU, linhas, colunas);
        
        String metodo = "SAIDA - MÉTODO DA FATORAÇÃO LU\n";
        resultado.add(metodo);
        
        titulo = "Matriz L";
        printMatriz(matrizL, (linhas), (colunas - 1), titulo);

        titulo = "\nMatriz U";
        printMatriz(matrizU, (linhas), (colunas - 1), titulo);

        /*Chamo o método para encontrar o vertor de soluções y*/
        encontraVetorYSolucoes(matrizL);
        titulo = "\nVetor Y";
        printVector(vySolucao, titulo);

        /*Chamo o método para montar a matriz do novo sistema*/
        double[][] sistema = montaSistema(matrizU, vySolucao, linhas, (colunas));

        /*Chamo o método para encontrar o vertor de soluções X*/
        encontraVetorXSolucoes(sistema);
        titulo = "\nVetor X";
        printVector(vxSolucao, titulo);

        File_.write(resultado, directoryName);

    }

    public static void encontraPivo(double[][] matriz, int lin, int col) {
        double pivo;

        for (int i = 0; i < lin; i++) {
            for (int j = 0; j < col; j++) {
                if ((i == j && i == j) && (j < colunas - 1)) {
                    pivo = matriz[i][j];//Encontra o pivô da linha 
                    calculaMultiplicador(pivo, i, j, matriz);
                }
            }
        }
    }

    public static void calculaMultiplicador(double pivo, int l, int c, double[][] matriz) {

        for (int i = l + 1; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (j == c) {

                    double elemento_linha = Double.parseDouble(String.valueOf(matriz[i][j]));
                    double pv = Double.parseDouble(String.valueOf(pivo));
                    double mult = elemento_linha / pv;

                    if (i > j) {

                        matrizL[i][j] = mult;
                    }

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
    /*Depois de encontrar o pivô atualiza a linha*/

    public static void inicializaMatriL() {

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if ((i == j && i == j)) {
                    matrizL[i][j] = 1;
                } else if (i < j) {
                    matrizL[i][j] = 0;
                } else {
                    matrizL[i][j] = -5;
                }
            }
        }
    }

    /*Após encontrar a matriz com a digonal trinangular zerada resolve o sistema*/
    public static void encontraVetorYSolucoes(double[][] matriz) {

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (i == 0 && j == 0) {
                    double veta = matriz[i][j];
                    double vetb = vector[i];
                    double num = vetb / veta;
                    vySolucao[i] = String.valueOf(Math.round(num));

                } else if (i == j) {
                    somaY(matrizL, i);
                }
            }
        }
    }

    /*Método usado para fazer a soma da linha junto com a multiplicação do resultado do x1,x2,x3*/
    public static void somaY(double[][] matriz, int linha) {

        double veta = 0;//Defino o primeiro divisor como sendo o primeiro elemento da diagonal
        double vetb = 0;
        double result = 0;
        double divisor = 0;

        int cont = 1;

        for (int j = 0; j < colunas; j++) {

            if (linha == j) {
                vetb = vector[linha];
                divisor = matriz[linha][j];
                result = (vetb - veta) / divisor;
                result = Action.arredonda(result);//classe usada para arredondar um número
                vySolucao[linha] = String.valueOf(result);
                veta = 0;

            } else if (j < linha) {
                double num = matriz[linha][j];//guarda o número na posisão da matriz
                double num2 = Double.parseDouble(vySolucao[j]);//guarda o multiplicador do vetor soluções
                double s = (num * num2);
                veta += (num * num2);//guarda a soma de cada linha 
            }
        }

    }

    /*Após encontrar a matriz com a digonal trinangular zerada resolve o sistema*/
    public static void encontraVetorXSolucoes(double[][] matriz) {

        for (int i = (linhas - 1); i >= 0; i--) {
            for (int j = (colunas); j >= 0; j--) {
                if (i == (linhas - 1) && j == (linhas - 1)) {
                    double veta = matriz[i][j];
                    double vetb = Double.parseDouble(vySolucao[i]);
                    double num = vetb / veta;
                    vxSolucao[i] = String.valueOf(Math.round(num));

                } else if (i == j) {
                    somaX(matriz, i);
                }
            }
        }
    }


    /*Método usado para fazer a soma da linha junto com a multiplicação do resultado do x1,x2,x3*/
    public static void somaX(double[][] matriz, int linha) {
        double veta = 0;
        double vetb = 0;
        double result = 0;
        double divisor = 0;

        for (int j = (linhas - 1); j >= 0; j--) {

            if (linha == j) {
                vetb = Double.parseDouble(vySolucao[linha]);
                divisor = matriz[linha][j];
                result = (vetb - veta) / divisor;
                result = Action.arredonda(result);//classe usada para arredondar um número
                vxSolucao[linha] = String.valueOf(result);
                veta = 0;

            } else if (j > linha) {

                double num = matriz[linha][j];//guarda o número na posisão da matriz
                double num2 = Double.parseDouble(vxSolucao[j]);//guarda o multiplicador do vetor soluções
                double s = (num * num2);
                veta += (num * num2);//guarda a soma de cada linha       
            }
        }
    }
    /*método usado para montar o novo sistema com a matriz U e vetor y*/

    public static double[][] montaSistema(double[][] matrizU, String[] vySolucao, int linhas, int colunas) {

        double[][] sistema = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < (colunas); j++) {
                if (j < colunas) {
                    sistema[i][j] = Action.arredonda(matrizU[i][j]);
                } else {
                    sistema[i][j] = Action.arredonda(Double.parseDouble(vySolucao[i]));
                }
            }
        }
        return sistema;
    }

    public static void printMatriz(double[][] matriz, int linhas, int colunas, String titulo) {
        String t = "\n" + titulo;
        String r;
        System.out.println(t);
        resultado.add(t);
        for (int i = 0; i < linhas; i++) {
            System.out.println("");
            resultado.add("\n");
            for (int j = 0; j < (colunas + 1); j++) {
                r = "[" + Action.arredonda(matriz[i][j]) + "]";
                resultado.add(r);
                System.out.println(r);
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
