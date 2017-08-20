package nn.realization;

import nn.structure.NeuralNetwork;
import nn.structure.Neuron;

public interface Realization {
    double getA();
    double getE();

    void create(NeuralNetwork storage);

    double funcActivation(double weight);
    double deltaOutput(double output, double ideal);
    double deltaHidden(Neuron neuron);
    double[] getIdealOutput(double[] input);
}
