/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.ArrayList;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Stephen
 */
public class NeuralNetwork extends Classifier {

    ArrayList<Layer> layer;
    int layers;
    int nodes;

    @Override
    public void buildClassifier(Instances i) throws Exception {
        System.out.println(i.numAttributes() + " num attributes");
        System.out.println(i.numClasses() + "num classes");
        layer = new ArrayList<Layer>();
        for (int j = 0; j < layers; j++) {
            if (j == 0){
            layer.add(new Layer(nodes, i.numAttributes() - 1, i.numClasses()));
            }
            else{
                layer.add(new Layer(nodes, layer.get(j -1).getNeurons().size(),i.numClasses()));
            }
        }
        for (int j = 0; j < 20; j++) {
            for (int d = 0; d < layer.size(); d++) {
                if (d == 0) {
                    //if going through the first time use the attributes to feed to the input layer
                    for (int l = 0; l < i.numInstances(); l++) {

                        for (int k = 0; k < layer.get(d).getNeurons().size(); k++) {
                            for( int p = 0; p < i.numAttributes()-1; p++){
                            layer.get(d).getNeurons().get(k).checkOutput(i.instance(l));
                            }
                            // layer.get(d).getNeurons().get(k).adjustWeights(i.instance(l));
                        }
                    }
                }else{
                    // go through all of the neurons in layer 'd'
                    for (int k = 0; k < layer.get(d).getNeurons().size(); k++) {
                        // get the outputs of layer 'd-1' and feed them to layer 'd'
                            for( int p = 0; p < layer.get(d-1).getNeurons().size(); p++){
                                //account for the bias node
                                if(p == 0){
                                    layer.get(d).getNeurons().get(k).nextLayer(-1,p-1);
                                }
                            layer.get(d).getNeurons().get(k).nextLayer(layer.get(d-1).getNeurons().get(p).getOutput(),p);
                            }
                            // set the total back to 0 after it process the previous layer
                            layer.get(d).getNeurons().get(k).setNextTotal(0);
                            // layer.get(d).getNeurons().get(k).adjustWeights(i.instance(l));
                        }
                                    

                }
            }
        }
          for (int d = 0; d < layer.size(); d++) {
             // System.out.println(layer.size() + "layers");
            for (int k = 0; k < layer.get(d).getNeurons().size(); k++) {
                // get the outputs of layer 'd-1' and feed them to layer 'd'
                //ln(layer.get(d).getNeurons().size() + "neurons");
                  //  System.out.println(layer.get(d).getNeurons().get(k).getOutput());
            }
        }
    }

    @Override
    public String getRevision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        int index = 0;
        double place = 0;
        double[] values = new double[instnc.numClasses()];
      
        for (int d = 0; d < layer.size(); d++) {
            //if at the input layer
            if (d == 0) {
                for (int i = 0; i < layer.get(d).getNeurons().size(); i++) {
                    layer.get(d).getNeurons().get(i).checkOutput(instnc);
                }
            }else{
                for ( int j = 0; j < layer.get(d).getNeurons().size(); j++){
                    for ( int p = 0; p < layer.get(d-1).getNeurons().size(); p++)
                     layer.get(d).getNeurons().get(j).nextLayer(layer.get(d-1).getNeurons().get(p).getOutput(),p);
                }
            }
        }
        for (int i = 0; i < layer.get(layer.size() - 1).getNeurons().size(); i++) {
            double amount = layer.get(layer.size() - 1).getNeurons().get(i).getOutput(); 
                values[layer.get(layer.size() - 1).getNeurons().get(i).getClassVal()]+= amount;

            
        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] >= place) {
                place = values[i];
                index = i;
            }
        }
        System.out.println(place);
        System.out.println(index);
        return index;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

}
