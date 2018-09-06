import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int numberOfCases;
    static int gridSize;
    static int modelsInGrid;
    static boolean legal = false;
    static int fashionPoints;
    static int modifications;

    static String[][] grid;
    static List<String> results;
    static List<String> figures;

    static String outputFilePath = "output2.out";


    public static void main(String[] args) {

        File file = new File("./src/SmallInput.txt");

        Scanner scan = null;
        try {
            scan = new Scanner(file); // Create scanner
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        numberOfCases = scan.nextInt();

        try {
            eachCase(scan);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void eachCase(Scanner scan) throws Exception {

        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
        for (int j = 0; j <numberOfCases; j++) {
            fashionPoints = 0;
            modifications = 0;
            results = new ArrayList();
            gridSize = scan.nextInt();
            modelsInGrid = scan.nextInt();
            fillGrid(scan);
            figures=new ArrayList(Arrays.asList("o", "+", "x"));
            for (int i = 0; i < gridSize; i++) {
                //for (int k = 0; k < gridSize; k++) {

                change(0, i);

                //}
            }
            figures=new ArrayList(Arrays.asList("+","x"));
            if(gridSize>1){
                for (int i = 0; i < gridSize; i++) {
                    //for (int k = 0; k < gridSize; k++) {
                    change(gridSize-1,i);
                    //}
                }
            }
            figures=new ArrayList(Arrays.asList("o","+","x"));
            for (int i = 1; i < gridSize-1 ; i++) {
                for (int k = 0; k < gridSize ; k++) {
                    change(i, k);
                }
            }

            print(bufferedWriter, j);
          for (int i = 0; i < gridSize-1; i++) {
              for (int k = 0; k < gridSize-1; k++) {
                    System.out.print(grid[i][k] + " ");
                }
                System.out.println("");
            }
        }

        bufferedWriter.close();
    }

    private static void print(BufferedWriter bufferedWriter, int j) throws IOException {
        System.out.println("Case #" + (j + 1) + ": " + fashionPoints + " " + modifications + "\n");
        bufferedWriter.write("Case #" + (j + 1) + ": " + fashionPoints + " " + modifications + "\n");
        for (String result : results) {
            System.out.println(result);
            bufferedWriter.write(result);
        }
    }

    private static void fillGrid(Scanner scan) {
        grid = new String[gridSize][gridSize];
        for (String[] row : grid) {
            Arrays.fill(row, ".");
        }
        for (int i = 0; i < modelsInGrid; i++) {
            String figure = scan.next();
            grid[scan.nextInt() - 1][scan.nextInt() - 1] = figure;
        }
    }

    private static void change(int x, int y) {

//            if (grid[x][y].equals(".")){
        String anterior = grid[x][y];
        for (String newCase : figures) {
            grid[x][y] = newCase;
            if (validation(x, y, newCase)) {
                if (newCase.equals("o")) {
                    fashionPoints++;
                }
                fashionPoints++;
                if (!grid[x][y].equals(anterior)) {
                    results.add(newCase + " " + (x + 1) + " " + (y + 1) + "\n");
                    modifications++;
                }
                break;
            } else {

                    grid[x][y] = anterior;
            }
        } // for each fig

    }

    private static boolean validation(int x, int y, String newCase) {
        if (validarHorizontal(x) || newCase.equals("+")) {
            if (validarVertical(y) || newCase.equals("+")) {
                Pair<Integer, Integer> start = inicialPointDer(new Pair<Integer, Integer>(x, y));
                if (validarDiagonalDer(start.getKey(), start.getValue()) || newCase.equals("x")) {
                    start = inicialPointDer(new Pair<Integer, Integer>(x, y));
                    if (validarDiagonalIzq(start.getKey(), start.getValue()) || newCase.equals("x")) {
                        return true;
                    } // validacion diagonal izq
                } // validacion diagonal der
            } // Validacion vertical
        } //validacion horizontal
        return false;
    }

    private static Pair<Integer, Integer> inicialPointDer(Pair<Integer, Integer> cord) {
        if (cord.getKey() != 0 && cord.getValue() != 0) {
            cord = inicialPointDer(new Pair<Integer, Integer>(cord.getKey() - 1, cord.getValue() - 1));
        }
        return cord;
    }

    private static Pair<Integer, Integer> inicialPointIzq(Pair<Integer, Integer> cord) {
        if (cord.getKey() != 0 && cord.getValue() != 0) {
            cord = inicialPointDer(new Pair<Integer, Integer>(cord.getKey() - 1, cord.getValue() + 1));
        }
        return cord;
    }

    private static boolean validarDiagonalDer(int x, int y) {
        int cont = 0;
        while (x < gridSize && y < gridSize) {
            if (!grid[x][y].equals("x") && !grid[x][y].equals(".")) {
                cont++;
            }
            x++;
            y++;
        }
        if (cont > 1) {
            return false;
        }
        return true;
    }

    private static boolean validarDiagonalIzq(int x, int y) {
        int cont = 0;
        while (x >= 0 && y < gridSize) {
            if (!grid[x][y].equals("x") && !grid[x][y].equals(".")) {
                cont++;
            }
            x--;
            y++;
        }
        if (cont > 1) {
            return false;
        }
        return true;
    }

    private static boolean validarHorizontal(int y) {
        int cont = 0;
        for (int i = 0; i < gridSize; i++) {
            if (!grid[y][i].equals("+") && !grid[y][i].equals(".")) {
                cont++;
            }
        }
        if (cont > 1) {
            return false;
        }
        return true;
    }

    private static boolean validarVertical(int x) {
        int cont = 0;
        for (int i = 0; i < gridSize; i++) {
            if (!grid[i][x].equals("+") && !grid[i][x].equals(".")) {
                cont++;
            }
        }
        if (cont > 1) {
            return false;
        }
        return true;
    }


}
