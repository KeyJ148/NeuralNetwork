package nn.realization;

import nn.gui.Console;

public class Start {

    public static void main(String[] args){
        Console console = new Console(new XOR());
        console.start();
    }
}
