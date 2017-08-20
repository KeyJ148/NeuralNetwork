package nn.structure;

import hbn.HibernateUtil;
import nn.realization.Realization;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NeuralNetwork {

    public ArrayList<Layer> layers = new ArrayList<>();
    public Set<Synapse> synapses = new HashSet<>();
    private Realization realization;
    private Session session;

    public NeuralNetwork(Realization realization){
        this.realization = realization;

        session = HibernateUtil.getSession();
        session.beginTransaction();
    }

    public double[] work(double[] input, boolean teach){
        //��������� �� ������� ��������� ������ ��������
        for (int i=0; i<input.length; i++){

            boolean find = false;
            for (Neuron neuron : layers.get(0).neurons){
                for (Synapse inputSynapse : neuron.inputSynapses){
                    if (inputSynapse.inputId == i){
                        inputSynapse.signal = input[i];

                        find = true;
                        break;
                    }
                }

                if (find) break;
            }
        }

        //���������� ������ ����
        for (Layer layer : layers){
            layer.work();
        }

        //���������� ������ � �������� ��������
        int lastLayer = layers.size()-1;
        double[] output = new double[layers.get(lastLayer).neurons.size()+1];

        for (Neuron neuron : layers.get(lastLayer).neurons){
            if (neuron.outputId != -1) output[neuron.outputId] = neuron.signal;
        }

        //���������� ������
        double[] idealOutput = realization.getIdealOutput(input);

        double sum = 0;
        for (int i=0; i<output.length-1; i++){
            sum += (idealOutput[i] - output[i])*(idealOutput[i] - output[i]);
        }

        //-1 ������ ��� � �������� ���� output.lenght-1 ������, �.�. ��������� ������ ������� ������������ ��� ������ ������
        double error = sum/(output.length-1);
        output[output.length-1] = error;

        if (teach) teach(input, output);

        return output;
    }

    //�������� ���� (�������� ������ ����� ���������� work)
    private void teach(double[] input, double[] output){
        int lastLayer = layers.size()-1;
        double[] idealOutput = realization.getIdealOutput(input);

        //������� ������ ��������� ����
        for (Neuron neuron : layers.get(lastLayer).neurons){
            if (neuron.outputId != -1) neuron.delta = realization.deltaOutput(output[neuron.outputId], idealOutput[neuron.outputId]);
        }

        //������� ���� ���� ����� ������� � ���������� ������ � �����
        for (int i=layers.size()-2; i>=0; i--){
            for (Neuron neuron : layers.get(i).neurons){ //������� ���� �������� � ����
                if (i != 0) //���� ��� �� ������� ����
                    neuron.delta = realization.deltaHidden(neuron); //������� ������ ��� �������

                //������� ���� �������� ���������� ����� �������
                for (Synapse synapse : neuron.outputSynapses){
                    double grad = neuron.signal*synapse.outputNeuron.delta;
                    double E = realization.getE();
                    double A = realization.getA();
                    double deltaWeight = E*grad + A*synapse.lastDeltaWeight;

                    synapse.weight += deltaWeight;
                    synapse.lastDeltaWeight = deltaWeight;
                }
            }
        }

        session.flush();
    }

    public void load(){
        List<Neuron> neurons = session.createCriteria(Neuron.class).list();
        for (Neuron neuron : neurons){
            while (layers.size() <= neuron.layer)
                layers.add(new Layer());
            layers.get(neuron.layer).neurons.add(neuron);

            neuron.nn = this;
        }

        List<Synapse> synapsesDB = session.createCriteria(Synapse.class).list();
        for (Synapse synapse : synapsesDB){
            synapses.add(synapse);
        }

        //���������� ������, �� ����������� �� ��
        //��� outputSynapses � �������� � �������/�������� ������� � ����������
        for (Layer layer : layers) {//���������� ��� �������
            for (Neuron neuron : layer.neurons) {//�� ���� �����
                for (Synapse inputSynapse : neuron.inputSynapses) {//��� ������� �� ���� �������� ���������� ��� �������� ���������
                    inputSynapse.outputNeuron = neuron;//������� ��������� ������ � ��������� (��������� ��� �1)

                    boolean find = false;
                    for (Layer layerFind : layers) {//����� ���������� ��� ������� �� ���� �����
                        for (Neuron neuronFind : layerFind.neurons) {
                            if (neuronFind.getId() == inputSynapse.getInputNeuronId()) {//���� �������� ������ ��� ��������� �1
                                neuronFind.outputSynapses.add(inputSynapse);//��������� �������� � ������ ��������� ���������� � �������
                                inputSynapse.inputNeuron = neuronFind;//������� �������� ������ � ��������� �1

                                //��� ������ ������� ������ ������ - ����������� ����� � ������� �� ����� ������
                                //�� ����� ������ �� ���� � �� ����� �������� �����
                                find = true;
                                break;
                            }
                        }

                        if (find) break;
                    }

                }
            }
        }
    }

    public void safe(){
        for (Layer layer : layers) {//���������� ��� �������
            for (Neuron neuron : layer.neurons) {//�� ���� �����
                session.save(neuron);
            }
        }

        for (Synapse synapse : synapses){
            if (synapse.inputNeuronId == 0)
                synapse.inputNeuronId = synapse.inputNeuron.id;

            session.save(synapse);
        }

        session.flush();
    }

    public void create(){
        realization.create(this);
    }

    public Realization getRealization(){
        return realization;
    }

    public void close(){
        session.flush();
        session.getTransaction().commit();
        session.close();
    }
}
