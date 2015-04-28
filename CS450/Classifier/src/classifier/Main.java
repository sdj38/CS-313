/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classifier;

import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 * this class gathers a csv file and performs some tests and analysis on it.
 * @author Stephen
 */
public class Main {
  
    public Main(){
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ReadFile reader = new ReadFile();
        reader.read();
        // sets data equal to what the reader class gathers
        Instances data = new Instances(reader.getData());
        // a new classifier that always returns the first class
        HardCodeClassifier hardCode = new HardCodeClassifier();
        // randomize the data set
        data.randomize(data.getRandomNumberGenerator(data.numInstances()));
        
        int trainNum = (int) (data.numInstances()*.7d);
        int testNum = data.numInstances() - trainNum;
        // seperate into a training set and a test set
        Instances train = new Instances(data,0,trainNum);
        Instances test = new Instances (data,trainNum,testNum);
        // evaluate the training set against the hardcode
        Evaluation trainEval = new Evaluation(train);
        trainEval.evaluateModel(hardCode, train);
        System.out.println(trainEval.toSummaryString());
         // evaluate the test set against the hardcode
        Evaluation testEval = new Evaluation(test);
        testEval.evaluateModel(hardCode, test);
        System.out.println(testEval.toSummaryString());
        
        // TODO code application logic here
    }
    
   
    
}
