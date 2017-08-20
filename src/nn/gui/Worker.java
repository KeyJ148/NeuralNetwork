package nn.gui;

import nn.realization.Realization;
import nn.structure.NeuralNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class Worker {

    public static void work(BufferedReader reader, Realization realization) throws IOException{
        System.out.println("Enter input data:");
        System.out.println("Enter 'R' for random set.");
        System.out.println("Enter 'A' for all sets.");

        String s = reader.readLine();

        if (s.equals("R")){
            workRandom(realization);
        } else if (s.equals("A")){
            workAll(realization);
        } else {
            String[] inputStrings = s.split(" ");
            double[] input = new double[inputStrings.length];
            for (int i = 0; i < input.length; i++)
                input[i] = Double.parseDouble(inputStrings[i]);

            workSet(input, realization);
        }
    }

    private static void workAll(Realization realization){
        double minError = 1;
        double maxError = 0;
        double avgError = 0;

        double[][] allSets = realization.getAllInputSets();
        for (int i=0; i<allSets.length; i++){
            double[] result = workSet(allSets[i], realization);
            int pos = result.length-1;
            if (result[pos] < minError) minError = result[pos];
            if (result[pos] > maxError) maxError = result[pos];
            avgError += result[pos];

            System.out.println();
        }

        avgError = avgError/allSets.length;

        System.out.println("Min error: " + minError);
        System.out.println("Max error: " + maxError);
        System.out.println("Avg. error: " + avgError);
    }

    private static void workRandom(Realization realization){
        Random random = new Random();

        double[][] allSets = realization.getAllInputSets();
        workSet(allSets[random.nextInt(allSets.length)], realization);
    }

    private static double[] workSet(double[] data, Realization realization){
        NeuralNetwork nn = new NeuralNetwork(realization);
        nn.load();

        double[] result = nn.work(data, false); //В последней ячейки хранится ошибка

        System.out.print("Input: ");
        for (int i=0; i<data.length; i++) System.out.print(data[i] + " ");
        System.out.println();

        System.out.print("Result: ");
        for (int i=0; i<result.length-1; i++) System.out.print(result[i] + " ");
        System.out.println();

        System.out.println("Error: " + result[result.length-1]);

        nn.close();
        return result;
    }
}
