package nn.gui;

import hbn.HibernateUtil;
import nn.structure.Realization;
import nn.structure.Layer;
import nn.structure.NeuralNetwork;
import nn.structure.Neuron;
import nn.structure.Synapse;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Console {

    public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public Realization realization;

    public Console(Realization realization){
        this.realization = realization;
    }

    public void start(){
        try{
            boolean exit;
            do{
                exit = circle();
            } while (!exit);
        } catch (IOException e){
            e.printStackTrace();
        }

        close();
    }

    public boolean circle() throws IOException{
        System.out.println();
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
                Worker.work(reader, realization);
                break;
            case 4:
                Teacher.teach(reader, realization);
                break;
            case 5:
                return true;
        }

        return false;
    }

    public void create(){
        Session session = HibernateUtil.getSession();
        List<Neuron> neurons = session.createCriteria(Neuron.class).list();
        List<Synapse> synapses = session.createCriteria(Synapse.class).list();
        for (Neuron neuron : neurons) session.delete(neuron);
        for (Synapse synapse : synapses) session.delete(synapse);
        session.flush();
        session.close();

        NeuralNetwork nn = new NeuralNetwork(realization);
        nn.create();
        nn.safe();
        nn.close();
    }

    public void load(){
        NeuralNetwork nn = new NeuralNetwork(realization);
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

    public void close(){
        try {
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
