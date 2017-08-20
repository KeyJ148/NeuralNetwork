package nn;

import hbn.HibernateUtil;
import nn.realization.xor.XOR;
import nn.structure.Layer;
import nn.structure.NeuralNetwork;
import nn.structure.Neuron;
import nn.structure.Synapse;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Start {

    public static void main(String[] args) throws IOException{
        NeuralNetwork nn = new NeuralNetwork(new XOR());


        double[] ideal = nn.getRealization().getIdealOutput(new double[] {0, 0});
        System.out.println(ideal[0]);


        ideal = nn.getRealization().getIdealOutput(new double[] {1, 0});
        System.out.println(ideal[0]);


        ideal = nn.getRealization().getIdealOutput(new double[] {0, 1});
        System.out.println(ideal[0]);


        ideal = nn.getRealization().getIdealOutput(new double[] {1, 1});
        System.out.println(ideal[0]);


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Select an action: ");
        System.out.println("1 - Generate new NN");
        System.out.println("2 - Load NN from database");
        System.out.println("3 - Work NN");
        System.out.println("4 - Teach NN");
        System.out.println("5 - Exit");
        int choose = Integer.parseInt(reader.readLine());

        switch (choose){
            case 1:
                create();
                break;
            case 2:
                load();
                break;
            case 3:
                work();
                break;
            case 4:
                teach();
                break;
            case 5:
                break;
        }

        reader.close();
    }
    
    public static void create(){
        Session session = HibernateUtil.getSession();
        List<Neuron> neurons = session.createCriteria(Neuron.class).list();
        List<Synapse> synapses = session.createCriteria(Synapse.class).list();
        for (Neuron neuron : neurons) session.delete(neuron);
        for (Synapse synapse : synapses) session.delete(synapse);
        session.flush();
        session.close();

        NeuralNetwork nn = new NeuralNetwork(new XOR());
        nn.create();
        nn.safe();
        nn.close();
    }

    public static void load(){
        NeuralNetwork nn = new NeuralNetwork(new XOR());
        nn.load();
        System.out.println("NEURONS:");
        for (Layer layer : nn.layers){
            for (Neuron neuron : layer.neurons){
                System.out.println(neuron + "\n");
            }
        }
        System.out.println();
        System.out.println("SYNAPSES:");
        for (Synapse synapse : nn.synapses){
            System.out.println(synapse + "\n");
        }
        nn.safe();
        nn.close();
    }

    public static void work(){
        double[] result1 = workSet(new double[] {0, 0});
        double[] result2 = workSet(new double[] {1, 0});
        double[] result3 = workSet(new double[] {0, 1});
        double[] result4 = workSet(new double[] {1, 1});

        int pos = result1.length-1;
        double midError = (result1[pos]+result2[pos]+result3[pos]+result4[pos])/4;
        double minError = Math.min(Math.min(result1[pos], result2[pos]), Math.min(result3[pos], result4[pos]));
        double maxError = Math.max(Math.max(result1[pos], result2[pos]), Math.max(result3[pos], result4[pos]));
        System.out.println("Min error: " + minError);
        System.out.println("Max error: " + maxError);
        System.out.println("Avg. error: " + midError);
    }

    private static double[] workSet(double[] data){
        NeuralNetwork nn = new NeuralNetwork(new XOR());
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

    public static void teach(){
        int count = 500;

        for (int i=0; i<count; i++) {
            teachEpoch();
            System.out.println(i);
        }
    }

    private static void teachEpoch(){
        teachSet(new double[] {0, 0});
        teachSet(new double[] {1, 0});
        teachSet(new double[] {0, 1});
        teachSet(new double[] {1, 1});
    }

    private static void teachSet(double[] data){
        NeuralNetwork nn = new NeuralNetwork(new XOR());
        nn.load();

        nn.work(data, true);

        nn.close();
    }
    
}
