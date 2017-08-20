package nn.gui;

import nn.realization.Realization;
import nn.structure.NeuralNetwork;

import java.io.BufferedReader;
import java.io.IOException;

public class Teacher {

    public static void teach(BufferedReader reader, Realization realization) throws IOException{
        System.out.println("Enter epoch count for teach: ");
        int count = Integer.parseInt(reader.readLine());
        System.out.println("Print an epoch every N iterations: ");
        int everyPrint = Integer.parseInt(reader.readLine());

        double[][] allSets = realization.getAllInputSets();

        NeuralNetwork nn = new NeuralNetwork(realization);
        nn.load();

        for (int epoch=0; epoch<count; epoch++) {
            for (int i = 0; i < allSets.length; i++) {
                nn.work(allSets[i], true);
            }

            if (epoch % everyPrint == 0){
                System.out.println("Epoch: " + epoch + "/" + count);
            }
        }

        nn.close();
    }
}
