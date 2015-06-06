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
        layer = new ArrayList<Layer>();
        for (int j = 0; j < layers; j++) {
            if (j == 0) {
                layer.add(new Layer(nodes, i.numAttributes() - 1, i.numClasses()));
            } else {
                layer.add(new Layer(nodes, layer.get(j - 1).getNeurons().size(), i.numClasses()));
            }
        }
        // # of times to go through the list of instances
        for (int j = 0; j < 1; j++) {
            // loop through instances
            for (int l = 0; l < i.numInstances(); l++) {
                // send instance down all the layers
                forwardFeed(i.instance(l));
                
                // back propogation to adjust weights
                backpropogate(i.instance(l));

            }

        }
    }
    public void forwardFeed(Instance i){
        for (int d = 0; d < layer.size(); d++) {
                    if (d == 0) {
                        //if going through the first time use the attributes to feed to the input layer

                        for (int k = 0; k < layer.get(d).getNeurons().size(); k++) {
                            for (int p = 0; p < i.numAttributes() - 1; p++) {
                                layer.get(d).getNeurons().get(k).checkOutput(i);
                            }

                        }

                    } else {
                        // go through all of the neurons in layer 'd'
                        for (int k = 0; k < layer.get(d).getNeurons().size(); k++) {
                            // get the outputs of layer 'd-1' and feed them to layer 'd'
                            for (int p = 0; p < layer.get(d - 1).getNeurons().size() + 1; p++) {
                                //account for the bias node
                                if (p == 0) {
                                    layer.get(d).getNeurons().get(k).nextLayer(-1, p);
                                }else{
                                layer.get(d).getNeurons().get(k).nextLayer(layer.get(d - 1).getNeurons().get(p -1).getOutput(), p);
                                }
                            }
                            // set the total back to 0 after it process the previous layer
                            layer.get(d).getNeurons().get(k).setNextTotal(0);
                            // layer.get(d).getNeurons().get(k).adjustWeights(i.instance(l));
                        }

                    }
                }
        
    }

    public void backpropogate(Instance i) {
        for (int d = layer.size() - 1; d > -1; d--) {
            
            for (int k = 0; k < layer.get(d).getNeurons().size(); k++) {
                // calc error for outer layer
                if (d == layer.size() - 1) {
                    layer.get(d).getNeurons().get(k).calcOuterError(i);
                    for (int v = 0; v < layer.get(d).getNeurons().get(k).getWeights().length; v++) {
                        if (v == 0) {
                            //account for bias
                            layer.get(d).getNeurons().get(k).adjustWeights(-1, v);
                        } else {
                            layer.get(d).getNeurons().get(k).adjustWeights(layer.get(d - 1).getNeurons().get(v - 1).getOutput(), v);
                        }
                    }
                } else {
                    for (int v = 0; v < layer.get(d + 1).getNeurons().size(); v++) {
                        layer.get(d).getNeurons().get(k).calcHiddenError(layer.get(d + 1).getNeurons().get(v).getError(),
                                layer.get(d + 1).getNeurons().get(v).getWeights(), k + 1);

                    }
                    layer.get(d).getNeurons().get(k).calcError();
                    // adjust weights for hidden layer
                    for (int v = 0; v < layer.get(d).getNeurons().get(k).getWeights().length; v++) {
                        if (v == 0) {
                            //account for bias
                            layer.get(d).getNeurons().get(k).adjustWeights(-1, v);
                        } else {
                            if (d == 0) {
                                layer.get(d).getNeurons().get(k).adjustWeights(i.value(v - 1), v);
                            } else {
                                layer.get(d).getNeurons().get(k).adjustWeights(layer.get(d - 1).getNeurons().get(v - 1).getOutput(), v);
                            }
                        }
                    }

                }
            }

        }
        for (int r = 0; r < layer.size(); r++) {
            for (int e = 0; e < layer.get(r).getNeurons().size(); e++) {
                layer.get(r).getNeurons().get(e).changeWeight();
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
        forwardFeed(instnc);

        for (int i = 0; i < layer.get(layer.size() - 1).getNeurons().size(); i++) {
            double amount = layer.get(layer.size() - 1).getNeurons().get(i).getOutput();
            values[layer.get(layer.size() - 1).getNeurons().get(i).getClassVal()] += amount;

        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] >= place) {
               
                place = values[i];
                index = i;
            }
        }
        return index;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

}
