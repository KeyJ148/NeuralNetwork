package nn.structure;

import java.util.HashSet;
import java.util.Set;

public class Neuron {

    public Set<Synapse> inputSynapses = new HashSet<>(); //В БД хранятся только входящие синапсисы
    public Set<Synapse> outputSynapses = new HashSet<>();//Ссылки на выходные синапсисы генерируются на основе входных от других нейронов
    public int layer;//Слой, в котором находится нейрон
    public int outputId = -1; //Id задаётся если этот нейрон является выходным, порядковая нумерация начиная с 0
    public double signal; //Сигнал равен значению сигнала на выходных синапсисах
    public double delta; //Используется при обучение сети

    public NeuralNetwork nn;

    public Neuron(){}

    public Neuron(int layer, NeuralNetwork nn){
        this.layer = layer;
        this.nn = nn;
    }

    public void work(){
        double inputWeight = getInputWeight();
        double outputSignal = nn.getRealization().funcActivation(inputWeight);
        dispatchOutputSynapses(outputSignal);
        signal = outputSignal;
    }

    private double getInputWeight(){
        double sum = 0;
        for (Synapse synapse : inputSynapses){
            sum += synapse.signal*synapse.weight;
        }

        return sum;
    }

    private void dispatchOutputSynapses(double outputSignal){
        for (Synapse synapse : outputSynapses){
            synapse.signal = outputSignal;
        }
    }

    @Override
    public String toString(){
        StringBuilder inputSynapsesIds = new StringBuilder();
        for (Synapse synapse : inputSynapses){
            inputSynapsesIds.append(synapse.getId() + ", ");
        }

        StringBuilder outputSynapsesIds = new StringBuilder();
        for (Synapse synapse : outputSynapses){
            outputSynapsesIds.append(synapse.getId() + ", ");
        }

        return "id: " + id
             + "\ninputSynapses: " + inputSynapsesIds
             + "\noutputSynapses: " + outputSynapsesIds;
    }

    //РАБОТА С БД
    public int id;

    public Set<Synapse> getInputSynapses() {
        return inputSynapses;
    }

    public void setInputSynapses(Set<Synapse> inputSynapses) {
        this.inputSynapses = inputSynapses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOutputId() {
        return outputId;
    }

    public void setOutputId(int outputId) {
        this.outputId = outputId;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
}
