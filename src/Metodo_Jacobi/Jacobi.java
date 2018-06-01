/*
 * Para verificar se o sistema irá convergir devemos verificar o criterio das linhas e das colunas
 *
 *
 */
package Metodo_Jacobi;

import Auxiliar.Action;
import File.File_;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class Jacobi {

    static String directoryName = "Metodo de Jacobi";
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

        String metodo = "SAIDA - MÉTODO JACOBI\n";
        resultado.add(metodo);

        File_.write(resultado, directoryName);
        matriz = File_.readMatriz(directoryName);

        titulo = "Matriz de Jacobi";
        printMatriz(matriz, linhas, colunas, titulo);
        System.out.println("");

        verificaCriterioLinha(matriz, linhas, colunas);
        verificaCriterioColuna(matriz, linhas, colunas);

        vetEntrada = File_.readVector(directoryName);
        vetxk = calculaVectorInicial(matriz, linhas, colunas, vetEntrada);//Vetor com o erro inicial 
        vetEntrada = calculaVectorInicial(matriz, linhas, colunas, vetEntrada);

        int iteracao = 1;//variável usada para guardar a quantidade de iterações 
        while (true) {
            double aux[] = new double[linhas];//Vetor usado para armazenar o vetor das subtrações
            vetxk1 = calculaJacobi(matriz, linhas, colunas, vetEntrada);//vetor que guarda o erro apos iterações

            for (int i = 0; i < vetEntrada.length; i++) {
                aux[i] = Action.arredonda((vetxk1[i] - vetEntrada[i]));
            }

            double dividendo = encontraMaiorElemento(aux);// após a subtração entre os dois vetores guarda o maior elemento em modulo   
            double divisor = encontraMaiorElemento(vetxk1);//guarda o maior valor em modulo do vetor de resultado k iteração
            double erro = dividendo / divisor;//obtem o erro a cada iteração

            /* atualiza o novo vetor solução*/
            for (int i = 0; i < vetxk1.length; i++) {
                vetEntrada[i] = vetxk1[i];
            }

            //verifica se o erro relativo encontrado é menor que a precisão
            if (erro < precisao) {
                System.out.println("\nCom a quantidade de iterações: " + iteracao + " com um Erro de : " + erro);
                break;
            }

            iteracao++;
        }
        printVector(vetxk1, "Vetor solução");
    }

    /*Método usado para verificar se o criterio de linha é satisfeito*/
    public static boolean verificaCriterioLinha(double[][] matriz, int linhas, int colunas) {
        boolean criterio = true;
        double diagonal = 0;
        double somaLinha;
        for (int i = 0; i < linhas; i++) {
            somaLinha = 0;
            for (int j = 0; j < colunas; j++) {
                if (i == j) {
                    diagonal = matriz[i][j];
                } else {
                    somaLinha += matriz[i][j];
                }
            }
            if (diagonal < somaLinha) {
                criterio = false;
            }
        }
        return criterio;
    }

    /*Método usado para verificar o criterio de coluna*/
    public static boolean verificaCriterioColuna(double[][] matriz, int linhas, int colunas) {
        boolean criterio = true;
        double diagonal = 0;
        double somaColuna;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (i == j) {

                    somaColuna = 0;
                    diagonal = matriz[i][j];
                    somaColuna = somaColuna(matriz, linhas, colunas, j);

                    if (diagonal <= somaColuna) {
                        criterio = false;
                    }
                }
            }
        }
        return criterio;
    }

    public static double somaColuna(double[][] matriz, int linhas, int colunas, int colunaAtual) {
        double somaColuna = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (colunaAtual == j && j != i) {
                    somaColuna += matriz[i][j];
                }
            }
        }
        return somaColuna;
    }

    
    /*Método usado para isolar o x de cada linha da matriz*/
    public static double[] calculaJacobi(double[][] matriz, int linhas, int colunas, double[] vetEntrada) {

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
                    double m = (matriz[i][j] * vetEntrada[j]);
                    vetb = vetb - Action.arredonda(m);
                }
            }
            vectorAux[i] = Action.arredonda(vetb / divisor);
        }
        return vectorAux;
    }

    public static double[] calculaVectorInicial(double[][] matriz, int linhas, int colunas, double[] vetEntrada) {
        double[] vectorXo = new double[linhas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (i == j) {
                    vectorXo[i] = vetEntrada[i] / matriz[i][j];
                }
            }
        }
        return vectorXo;
    }

    /*Método usado para encontrar o maior elemento de um vetro*/
    public static double encontraMaiorElemento(double[] vet) {
        double maior = Math.abs(vet[0]);
        for (double d : vet) {
            if (Math.abs(d) > maior) {
                maior = Math.abs(d);
            }
        }
        return maior;
    }

    /*encontra o maior dividendo entre a subtração dos dois vetores*/
    public static double[] calculaDividendo(double[] vet, double[] vet2, int linhas) {
        double[] vetAux = new double[linhas];
        for (int i = 0; i < vet.length; i++) {
            vetAux[i] = vet2[i] - vet[1];
        }
        return vetAux;
    }

    public static void printVector(double[] vector, String titulo) {
        String t = "\n" + titulo;
        String r;
        resultado.add(t);
        System.out.println("\n" + titulo);

        for (double s : vector) {
            r = "[" + s + "]";
            resultado.add(r);
            System.out.println(r);
        }
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

}
