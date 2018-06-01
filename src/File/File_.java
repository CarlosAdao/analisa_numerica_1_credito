package File;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class File_ {

    private static File input;
    private static File output;

    public static void write(ArrayList<String> atributos, String directoryName) throws IOException {  //passa da classe produtos para o arquivo lista de produtos 
        output = new File("" + directoryName + "/output.csv");

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        if (!output.exists()) {
            output.createNewFile();
        }

        FileWriter fw = new FileWriter(output);
        BufferedWriter bw = new BufferedWriter(fw);

        for (String s : atributos) {

            bw.write(s);
        }
        bw.close();
        fw.close();

        System.out.println("gravado com sucesso!!!");
    }

    /*Metodo usado para pegar os dados de entrada do arquivo*/
    public static ArrayList<String> read(String directoryName) {
        input = new File("" + directoryName + "/input.csv");

        ArrayList<String> atributos = new ArrayList<>();

        try {

            FileReader fr = new FileReader(input);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

            String linha = lerArq.readLine();

            while (linha != null) {

                atributos.add(linha);
                linha = lerArq.readLine();
            }

            fr.close();

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.", e.getMessage());
        }
        return atributos;
    }
    
    /*Metodo usado para ler o erro de um arquivo de entrada externo*/
    public static double readErro(String directoryName) {
        String linha = "0.0";//caso aconteça erro de leitura do arquivo retorno 0.0
        input = new File("" + directoryName + "/inputErro.csv");

        ArrayList<String> atributos = new ArrayList<>();

        try {

            FileReader fr = new FileReader(input);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

            linha = lerArq.readLine();
            
            fr.close();
            return Double.parseDouble(linha);
            
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.", e.getMessage());
        }
        return Double.parseDouble(linha);
    }
    
    
    /*Metodo usado para ler um vetor do aquivo de entrada*/
    public static double[] readVector(String directoryName) {
        double vector[] = new double[getSizeLine(directoryName)];

        input = new File("" + directoryName + "/inputVector.csv");

        try {

            FileReader fr = new FileReader(input);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));
            String linha = lerArq.readLine();

            while (linha != null) {

                String l[] = linha.split(" ");
                for (int i = 0; i < l.length; i++) {
                    vector[i] = Double.parseDouble(l[i]);
                }
                linha = lerArq.readLine();
            }

            fr.close();

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.", e.getMessage());
        }
        return vector;
    }
    /*Metodo usado para ler a matriz do aquivo de entrada*/

    public static double[][] readMatriz(String directoryName) {
        double[][] matriz;
        int linhas = getSizeLine(directoryName);
        int colunas = getSizeCol(directoryName);
        matriz = new double[linhas][colunas];
        int line = 0;

        input = new File("" + directoryName + "/input.csv");

        try {

            FileReader fr = new FileReader(input);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

            String linha = lerArq.readLine();

            while (linha != null) {

                String l[] = linha.split(" ");

                for (int i = 0; i < colunas; i++) {
                    matriz[line][i] = Integer.parseInt(l[i]);
                }
                line++;
                linha = lerArq.readLine();
            }

            fr.close();

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.", e.getMessage());
        }

        return matriz;
    }
    /*Metodo usado para pegar a quantidade de linhas da matriz do arquivo de entrada*/

    public static int getSizeLine(String directoryName) {
        int size_line = 0;
        input = new File("" + directoryName + "/input.csv");

        try {

            FileReader fr = new FileReader(input);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

            String linha = lerArq.readLine();

            while (linha != null) {

                size_line++;

                linha = lerArq.readLine();
            }

            fr.close();

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.", e.getMessage());
        }

        return size_line;
    }
    /*Metodo usado para pegar a quantidade de coluna da matriz do arquivo de entrada*/

    public static int getSizeCol(String directoryName) {
        int size_col = 0;
        input = new File("" + directoryName + "/input.csv");

        try {

            FileReader fr = new FileReader(input);
            BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

            String linha = lerArq.readLine();

            while (linha != null) {

                String[] col = linha.split(" ");
                size_col = col.length;
                linha = lerArq.readLine();
            }

            fr.close();

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.", e.getMessage());
        }
        return size_col;
    }

}
