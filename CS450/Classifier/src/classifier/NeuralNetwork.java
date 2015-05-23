/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Stephen
 */
public class NeuralNetwork extends Classifier {

    Layer layer;

    @Override
    public void buildClassifier(Instances i) throws Exception {
        System.out.println(i.numAttributes() + " num attributes");
        System.out.println(i.numClasses() + "num classes");
        layer = new Layer(3, i.numAttributes() - 1, i.numClasses());

        for (int j = 0; j < 2000; j++) {

            for (int l = 0; l < i.numInstances(); l++) {
                for (int k = 0; k < layer.getNeurons().size(); k++) {
                    layer.getNeurons().get(k).checkOutput(i.instance(l));
                    layer.getNeurons().get(k).adjustWeights(i.instance(l));
                }
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
        int place = 0;
        int[] values = new int[instnc.numClasses()];
       // System.out.println(values.length + "size");
        for (int i = 0; i < layer.getNeurons().size(); i++) {
            layer.getNeurons().get(i).checkOutput(instnc);
        //    System.out.println(layer.getNeurons().get(i).getOutput() + "neuron output" + i);
        }
        for (int i = 0; i < layer.getNeurons().size(); i++) {
            if (layer.getNeurons().get(i).getOutput() == 1) {
                values[layer.getNeurons().get(i).getClassVal()]++;

            }
        }

        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i] + "value");
            if (values[i] >= place) {
                place = values[i];
                index = i;
             //    System.out.println("change to" + index);
            }
//            if(place ==0){
//                index = 1;
//            }
        }
        System.out.println("guessed" + index);
        return index;
    }
}
