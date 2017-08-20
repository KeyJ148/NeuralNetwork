package nn.gui;

import nn.structure.Realization;
import nn.structure.NeuralNetwork;

import java.io.BufferedReader;
import java.io.IOException;

public class Teacher {

    public static void teach(BufferedReader reader, Realization realization) throws IOException{
        System.out.println("Enter epoch count for teach (-1 for inf): ");
        int count = Integer.parseInt(reader.readLine());
        System.out.println("Print an epoch every N iterations: ");
        int everyPrint = Integer.parseInt(reader.readLine());

        if (count >= 0){
            teachManySets(realization, count, everyPrint);
        } else {
            TeachThread teachThread = new TeachThread(realization, everyPrint);
            teachThread.start();
            System.out.println("Enter any text for stop teaching: ");
            reader.readLine();
            teachThread.exit = true;
        }
    }

    private static void teachManySets(Realization realization, int count, int everyPrint){
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

    public static class TeachThread extends Thread{

        public Realization realization;
        public int everyPrint;

        public boolean exit = false;

        public TeachThread(Realization realization, int everyPrint){
            this.realization = realization;
            this.everyPrint = everyPrint;
        }

        @Override
        public void run(){
            double[][] allSets = realization.getAllInputSets();

            NeuralNetwork nn = new NeuralNetwork(realization);
            nn.load();

            int epoch = 0;
            do {
                for (int i = 0; i < allSets.length; i++) {
                    nn.work(allSets[i], true);
                }

                if (epoch % everyPrint == 0){
                    System.out.println("Epoch: " + epoch);
                }

                epoch++;
            } while (!exit);

            nn.close();
        }
    }
}
