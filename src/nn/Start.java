package nn;

import nn.gui.Console;
import nn.realization.xor.XOR;

public class Start {

    public static void main(String[] args){
        Console console = new Console(new XOR());
        console.start();
    }
}
