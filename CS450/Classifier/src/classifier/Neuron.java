package classifier;

import java.util.Random;
import weka.core.Instance;

/**
 *
 * @author Stephen
 */
public class Neuron {

    Random rand;
    int classVal;
    int threshold;
    double[] weights;
    double output;
    double nextTotal;
    private static final double learnRate = .3;

    /**
     * Neuron will hold weights for the number of attributes, It will also hold
     * a class value to compare if it's output is correct or not.
     *
     * @param numAttr
     * @param classVal
     */
    public Neuron(int numAttr, int classVal) {
        rand = new Random();

        weights = new double[numAttr + 1];
        initializeWeights();
        this.classVal = classVal;
        this.threshold = 0;

    }

    // initialize between -1 and 1 for each weight
    private void initializeWeights() {
        for (int i = 0; i < weights.length; i++) {
            double value = (rand.nextDouble() * 2) - 1;
            weights[i] = value;
        }

    }

    // check to see if the input is greater than the threshold
    public void checkOutput(Instance i) {
        double total = 0;
 // System.out.println(weights.length + "# of weights for input layer");
        for (int j = 0; j < i.numAttributes(); j++) {
            // bias input
            if (j == 0) {
                total += weights[j] * -1;
            } else {
                total += weights[j] * i.value(j - 1);
            }
        }
       output = (1 / (1 + Math.pow(Math.E,-total)));
    }
    public void nextLayer(double d,int count){
        int inc;
        inc = count + 1;
        nextTotal += d*weights[inc];
        System.out.println(d + " incoming output " + weights[inc] + " weight value");
      //  System.out.println(inc + "count");
        //System.out.println(weights.length + "# of weights");
        if(inc == weights.length - 1){
          //  System.out.println(this + "inside output function");
        output = (1 / (1 + Math.pow(Math.E,-nextTotal)));
      //  System.out.println(output + "output");
        }
        
    }

    // if the output is incorrect adjust the weights;

    public void adjustWeights(Instance j) {
        int target;
        //System.out.println(classVal + "neuron type" + j.classValue() + "class value");
        if (classVal == (int) j.classValue()) {
            target = 1;
        } else {
            target = 0;
        }
        //   System.out.println(output + "predicted" + target + "target");
        for (int i = 0; i < weights.length; i++) {
            //  System.out.println(weights[i] + "oldweight");
            if (i == 0) {
                weights[i] = weights[i] - learnRate * (output - target) * -1;
            } else {
                weights[i] = weights[i] - learnRate * (output - target) * j.value(i - 1);
            }
            // System.out.println(weights[i] + "newweight");
        }

    }

    public double getOutput() {
        return output;
    }

    public int getClassVal() {
        return classVal;
    }

    public double getNextTotal() {
        return nextTotal;
    }

    public void setNextTotal(double nextTotal) {
        this.nextTotal = nextTotal;
    }
    

}
