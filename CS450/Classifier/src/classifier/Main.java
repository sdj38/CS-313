/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classifier;

import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.LinearNNSearch;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 * this class gathers a csv file and performs some tests and analysis on it.
 *
 * @author Stephen
 */
public class Main {
    
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Random rand = new Random();
        ReadFile reader = new ReadFile();
        reader.read( "C://Users//Stephen//Desktop//iris.csv");
        // sets data equal to what the reader class gathers
        Instances data = new Instances(reader.getData());
        // randomize the data set
        data.randomize(rand);
        Standardize standard = new Standardize();
      
        KNNClassifier knn = new KNNClassifier();

        int trainNum = (int) (data.numInstances() * .7d);
        int testNum = data.numInstances() - trainNum;
        
     
        // seperate into a training set and a irisTest set
        Instances train = new Instances(data, 0, trainNum);
        Instances test = new Instances(data, trainNum, testNum);
      
        // set the number of neighbors
        knn.setNeighbors(7);
        knn.buildClassifier(train);
        //test my kNN algorithm
        Evaluation knnEval = new Evaluation(test);
        knnEval.evaluateModel(knn, test);
        System.out.println(knnEval.toSummaryString());
          
        //wekas knn classifier to compare against
        IBk kNN = new IBk();
        kNN.buildClassifier(train);
        kNN.setKNN(7);
        Evaluation closeEval = new Evaluation(test);
        closeEval.evaluateModel(kNN,test);
        System.out.println(closeEval.toSummaryString());
        
        //read a new data set of cars
         reader.read( "C://Users//Stephen//Desktop//cars.csv");
        // sets data equal to what the reader class gathers
        data = new Instances(reader.getData());
        // randomize the data set
        data.randomize(rand);
       
        knn = new KNNClassifier();

        trainNum = (int) (data.numInstances() * .7d);
        testNum = data.numInstances() - trainNum;
        
     
        // seperate into a training set and a irisTest set
         train = new Instances(data, 0, trainNum);
         test = new Instances(data, trainNum, testNum);
      
         // set nearest neighbors
        knn.setNeighbors(15);
        knn.buildClassifier(train);
        knnEval = new Evaluation(test);
        knnEval.evaluateModel(knn, test);
        System.out.println(knnEval.toSummaryString());
          
        kNN = new IBk();
        kNN.buildClassifier(train);
        kNN.setKNN(15);
        closeEval = new Evaluation(test);
        closeEval.evaluateModel(kNN,test);
        System.out.println(closeEval.toSummaryString());
     
        
      

      
    }
    
}
