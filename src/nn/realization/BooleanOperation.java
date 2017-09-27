package nn.realization;

import nn.structure.*;

public class BooleanOperation implements Realization {

    public static final double A = 0.3;
    public static final double E = 1.8;
    public static final int COUNT_HIDDEN_1_NEURONS = 5;
    public static final int COUNT_HIDDEN_2_NEURONS = 5;

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
        Neuron[] input = new Neuron[3];
        for (int i=0; i<input.length; i++) input[i] = new Neuron(0, storage);

        Neuron[] hidden1 = new Neuron[COUNT_HIDDEN_1_NEURONS];
        for (int i=0; i<hidden1.length; i++) hidden1[i] = new Neuron(1, storage);

        Neuron[] hidden2 = new Neuron[COUNT_HIDDEN_2_NEURONS];
        for (int i=0; i<hidden2.length; i++) hidden2[i] = new Neuron(2, storage);

        Neuron output = new Neuron(3, storage);
        output.outputId = 0;

        //Добавление созданных нейронов в общий список по слоям
        storage.layers.add(0, new Layer());
        for (int i=0; i<input.length; i++)
            storage.layers.get(0).neurons.add(input[i]);

        storage.layers.add(1, new Layer());
        for (int i=0; i<hidden1.length; i++)
            storage.layers.get(1).neurons.add(hidden1[i]);

        storage.layers.add(2, new Layer());
        for (int i=0; i<hidden2.length; i++)
            storage.layers.get(2).neurons.add(hidden2[i]);

        storage.layers.add(3, new Layer());
        storage.layers.get(3).neurons.add(output);

        //Создание синапсисов и добавление их в общий список
        //Между первым и вторым слоем
        for (int i=0; i<input.length; i++){
            for (int j=0; j<hidden1.length; j++){
                Synapse synapse = new Synapse();
                synapse.inputNeuron = input[i];
                synapse.inputNeuronId = input[i].id;
                synapse.outputNeuron = hidden1[j];

                input[i].outputSynapses.add(synapse);
                hidden1[j].inputSynapses.add(synapse);

                storage.synapses.add(synapse);
            }
        }

        //Между вторым и третим слоем
        for (int i=0; i<hidden1.length; i++){
            for (int j=0; j<hidden2.length; j++){
                Synapse synapse = new Synapse();
                synapse.inputNeuron = hidden1[i];
                synapse.inputNeuronId = hidden1[i].id;
                synapse.outputNeuron = hidden2[j];

                hidden1[i].outputSynapses.add(synapse);
                hidden2[j].inputSynapses.add(synapse);

                storage.synapses.add(synapse);
            }
        }

        //Между третим и четвертым
        for (int i=0; i<hidden2.length; i++){
            Synapse synapse = new Synapse();
            synapse.inputNeuron = hidden2[i];
            synapse.inputNeuronId = hidden2[i].id;
            synapse.outputNeuron = output;

            hidden2[i].outputSynapses.add(synapse);
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
        double type = input[2];
        boolean a = (aInt == 1);
        boolean b = (bInt == 1);
        boolean resultBool = false;

        if (type == 0) resultBool = a && b;
        if (type == 1) resultBool = a || b;
        if (type == 0.5) resultBool = a ^ b;

        double resultDouble = (resultBool) ? 1 : 0;
        double[] result = {resultDouble};
        return result;
    }

    @Override
    public double[][] getAllInputSets(){
        double[][] result =
                {{0,0, 0},
                 {0,1, 0},
                 {1,0, 0},
                 {1,1, 0},

                 {0,0, 1},
                 {0,1, 1},
                 {1,0, 1},
                 {1,1, 1},

                 {0,0, 0.5},
                 {0,1, 0.5},
                 {1,0, 0.5},
                 {1,1, 0.5}};

        return result;
    }
}
