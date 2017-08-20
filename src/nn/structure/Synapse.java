package nn.structure;

public class Synapse {

    public Neuron inputNeuron;
    public Neuron outputNeuron;

    public double weight;
    public double lastDeltaWeight;
    public double signal;

    public int inputId = -1; //Id задаётся если этот синапсис является входным, порядковая нумерация начиная с 0

    public static final double maxWeight = 3;
    public static final double minWeight = -3;

    public Synapse(){
        this(Math.random()*(maxWeight-minWeight) + minWeight);
    }

    public Synapse(double weight){
        this.weight = weight;
    }

    @Override
    public String toString(){
        return "id: " + id
                + "\ninputId: " + inputId
                + "\nweight: " + weight
                + "\nsignal: " + signal
                + "\ninputNeuron: " + ((inputNeuron != null)? inputNeuron.getId() : "null")
                + "\noutputNeuron: " + ((outputNeuron != null)? outputNeuron.getId() : "null");
    }

    //РАБОТА С БД
    public int id;
    public int inputNeuronId = -1; //Id inputNeuron для хранения в БД

    public double getLastDeltaWeight() {
        return lastDeltaWeight;
    }

    public void setLastDeltaWeight(double lastDeltaWeight) {
        this.lastDeltaWeight = lastDeltaWeight;
    }

    public int getInputNeuronId() {
        return inputNeuronId;
    }

    public void setInputNeuronId(int inputNeuronId) {
        this.inputNeuronId = inputNeuronId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInputId() {
        return inputId;
    }

    public void setInputId(int inputId) {
        this.inputId = inputId;
    }
}
