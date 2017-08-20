package nn.realization;

import nn.structure.Realization;
import nn.structure.Layer;
import nn.structure.NeuralNetwork;
import nn.structure.Neuron;
import nn.structure.Synapse;

public class XOR implements Realization {

    public static final double A = 0.9;
    public static final double E = 2.1;
    public static final int COUNT_HIDDEN_NEURONS = 4;

    @Override
    public double getA() {
        return A;
    }

    @Override
    public double getE() {
        return E;
    }

    @Override
    public void create(NeuralNetwork storage) {
        //Создание слоёв из нейронов
        Neuron[] input = new Neuron[2];
        for (int i=0; i<input.length; i++) input[i] = new Neuron(0, storage);

        Neuron[] hidden = new Neuron[COUNT_HIDDEN_NEURONS];
        for (int i=0; i<hidden.length; i++) hidden[i] = new Neuron(1, storage);

        Neuron output = new Neuron(2, storage);
        output.outputId = 0;

        //Добавление созданных нейронов в общий список по слоям
        storage.layers.add(0, new Layer());
        for (int i=0; i<input.length; i++)
            storage.layers.get(0).neurons.add(input[i]);

        storage.layers.add(1, new Layer());
        for (int i=0; i<hidden.length; i++)
            storage.layers.get(1).neurons.add(hidden[i]);

        storage.layers.add(2, new Layer());
        storage.layers.get(2).neurons.add(output);

        //Создание синапсисов и добавление их в общий список
        //Между первым и вторым слоем
        for (int i=0; i<input.length; i++){
            for (int j=0; j<hidden.length; j++){
                Synapse synapse = new Synapse();
                synapse.inputNeuron = input[i];
                synapse.inputNeuronId = input[i].id;
                synapse.outputNeuron = hidden[j];

                input[i].outputSynapses.add(synapse);
                hidden[j].inputSynapses.add(synapse);

                storage.synapses.add(synapse);
            }
        }

        //Между вторым и третим слоем
        for (int i=0; i<hidden.length; i++){
            Synapse synapse = new Synapse();
            synapse.inputNeuron = hidden[i];
            synapse.inputNeuronId = hidden[i].id;
            synapse.outputNeuron = output;

            hidden[i].outputSynapses.add(synapse);
            output.inputSynapses.add(synapse);

            storage.synapses.add(synapse);
        }

        //Входной слой
        for (int i=0; i<input.length; i++){
            Synapse synapse = new Synapse();
            synapse.outputNeuron = input[i];
            synapse.inputId = i;

            input[i].inputSynapses.add(synapse);

            storage.synapses.add(synapse);
        }
    }

    @Override
    public double funcActivation(double weight) {
        return 1.0/(1+Math.exp(-weight));
    }

    @Override
    public double deltaOutput(double output, double ideal) {
        return (ideal-output)*(1-output)*output;
    }

    @Override
    public double deltaHidden(Neuron neuron) {
        double sum = 0;
        for (Synapse synapse : neuron.outputSynapses){
            sum += synapse.weight * synapse.outputNeuron.delta;
        }

        return (1-neuron.signal)*neuron.signal*sum;
    }

    @Override
    public double[] getIdealOutput(double[] input){
        int aInt = (int) Math.round(input[0]);
        int bInt = (int) Math.round(input[1]);
        boolean a = (aInt == 1);
        boolean b = (bInt == 1);

        boolean resultBool = a^b;
        double resultDouble = (resultBool)? 1 : 0;
        double[] result = {resultDouble};
        return result;
    }

    @Override
    public double[][] getAllInputSets(){
        double[][] result = {{0,0},
                             {0,1},
                             {1,0},
                             {1,1}};

        return result;
    }
}
