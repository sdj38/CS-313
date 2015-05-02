/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Stephen
 */
public class KNNClassifier extends Classifier {

    Instances inputs;
    int neighbors = 15;
    ArrayList<String> compares;

    @Override
    public void buildClassifier(Instances i) throws Exception {
        this.inputs = i;
        
        
    }

    @Override
    public String getRevision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        
      // the value that will end up being returned
        double classified = 0;
        // an array to hold the distances to find the nearest neighbors
        double[] distances = new double[neighbors];
        // holds the class type of the nearest neighbors
        double[] type = new double[neighbors];

        for (int i = 0; i < inputs.numInstances(); i++) {
            double distance = 0;
            // sets the array to the size of the number of attributes to get the distances
            double[] attr = new double[inputs.numAttributes()];
            // goes through the attributes and loads the double array
            for (int j = 0; j < inputs.numAttributes() - 1 ; j++) {
               
                attr[j] = inputs.instance(i).value(j);
                
            }
            // calculate the distance of the object to be classified and
            // the other data points
            for (int k = 0; k < inputs.numAttributes() -1; k++) {
                distance += Math.abs(instnc.value(k) - attr[k]);

            }
            // find which distances are closest to get your neighbors
            for (int n = 0; n < neighbors; n++) {
                if (distance < distances[n]) {
                    distances[n] = distance;
                    type[n] = inputs.instance(i).classValue();
                    break;
                }
                if (distances[n] == 0) {
                    distances[n] = distance;
                    type[n] = inputs.instance(i).classValue();
                    break;
                }

            }

        }
       // holds the class type with the amount of times its neighbor is near by
        HashMap<Double, Integer> results = new HashMap();
        for (int i = 0; i < neighbors; i++) {
            double temp = type[i];
            if (results.containsKey(temp)) {
                results.put(temp, results.get(temp) + 1);
            } else {
                results.put(temp, 1);
            }
        }
        Map.Entry<Double, Integer> maxEntry = null;
        // loops through and finds the key with the largest value
        for (Map.Entry<Double, Integer> entry : results.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        classified = maxEntry.getKey();

        return classified;//To change body of generated methods, choose Tools | Templates.

    }

    /**
     *
     * @return
     */
    public int getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;
    }
    
}
