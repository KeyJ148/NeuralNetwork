package nn.structure;

public interface Realization {
    double getA();
    double getE();

    void create(NeuralNetwork storage);

    double funcActivation(double weight);
    double deltaOutput(double output, double ideal);
    double deltaHidden(Neuron neuron);
    double[] getIdealOutput(double[] input);
    double[][] getAllInputSets();
}
