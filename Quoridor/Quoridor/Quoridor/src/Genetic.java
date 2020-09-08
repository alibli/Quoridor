import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Genetic {
    int populationSize=16;
    double[][] population=new double[populationSize][3];
    File myFile=new File("List.txt");
    String fileName="List.txt";

    public static void main(String[] args) throws FileNotFoundException {
        Genetic genetic=new Genetic();

        double[][] population=genetic.creatPopulation();
  //      genetic.readAIFile();
       /* try {
            File myObj = new File("filename.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }*/
    }


    double[][] creatPopulation(){

        Random random=new Random();
        File newFile=new File(" List1.txt");


        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j <population[i].length ; j++) {
                population[i][j]=random.nextDouble();
            }
        }

        try {

            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < populationSize; i++) {
                System.out.println();
                writer.write(population[i][0] + "\n" + population[i][1] + "\n" + population[i][2] + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Something went wrong while creating AIs.");
        }
        System.out.println("New AIs created.");
        return population;
    }


    void readAIFile() throws FileNotFoundException {
        Scanner scanner;
        scanner=new Scanner(myFile);
        System.out.println("Would you like to read from default text?");
        String line = scanner.next();
        population = new double[population.length][3];
        if (!line.equalsIgnoreCase("yes")) {
            System.out.println("Enter Path");
            myFile = new File(scanner.next());
        }
        try {
            BufferedReader input = new BufferedReader(new FileReader(myFile));
            for (int i = 0; i < populationSize * 4; i++) {
                line = input.readLine();
                population[i / 4][i % 4] = Integer.parseInt(line);
            }
            input.close();
        } catch (IOException e) {
            System.out.println("Something went wrong in reading a file.");
        }
        System.out.println("File read successful.");
    }

    void writeAIFile() throws FileNotFoundException {
        Scanner scanner=new Scanner(myFile);
        System.out.println("Would you like to write in default text?");
        String line = scanner.next();
        if (!line.equalsIgnoreCase("yes")) {
            System.out.println("Enter Path");
            myFile = new File(scanner.next());
        }
        try {
            Writer writer = new BufferedWriter(new FileWriter(myFile));
            for (int i = 0; i < populationSize; i++) {
                writer.write(population[i][0] + "\n" + population[i][1] + "\n" + population[i][2] + "\n" + population[i][3] + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Something went wrong while writing AI file.");
        }
        System.out.println("Writing AI file successful.");
    }

    void defaultReadAIFile() {
        String line;
        population = new double[populationSize][4];
        try {
            BufferedReader input = new BufferedReader(new FileReader(myFile));
            for (int i = 0; i < populationSize * 4; i++) {
                line = input.readLine();
                population[i / 4][i % 4] = Integer.parseInt(line);
            }
            input.close();
        } catch (IOException e) {
            System.out.println("Something went wrong in reading a file.");
        }
        System.out.println("Initial file read successful.");
    }



}
