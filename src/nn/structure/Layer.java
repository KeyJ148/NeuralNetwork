package nn.structure;

import java.util.HashSet;
import java.util.Set;

public class Layer {

    public Set<Neuron> neurons = new HashSet<>();

    public void work(){
        for (Neuron neuron : neurons){
            neuron.work();
        }
    }
}
