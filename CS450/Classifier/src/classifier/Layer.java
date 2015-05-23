/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.ArrayList;
import weka.core.Instance;

/**
 * creates a layer of neurons makes one neuron for each class multiple times
 * depending on what is pass in as a parameter
 * @author Stephen
 */
public class Layer {

    ArrayList<Neuron> neurons = new ArrayList<>();

    public Layer(int numSets, int numAttributes, int numClasses) {
        for (int k = 0; k < numSets; k++) {
            for (int j = 0; j < numClasses; j++) {
                neurons.add(new Neuron(numAttributes, j));
            }
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }
    

}
