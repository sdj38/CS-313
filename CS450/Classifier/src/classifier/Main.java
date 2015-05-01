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
 * @author Stephen
 */
public class Main {
  
    public Main(){
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Random rand = new Random();
        ReadFile reader = new ReadFile();
        reader.read();
        // sets data equal to what the reader class gathers
        Instances data = new Instances(reader.getData());
        // a new classifier that always returns the first class
        HardCodeClassifier hardCode = new HardCodeClassifier();
        // randomize the data set
        data.randomize(data.getRandomNumberGenerator(data.numInstances()));
        Standardize standard = new Standardize();
        KNNClassifier knn = new KNNClassifier();
       
      //  Instance b = data.instance(rand.nextInt(150));
      //  System.out.println(b.classValue());
         
       // knn.classifyInstance(b);
      
       
//         Evaluation trainEval = new Evaluation(data);
//         
//        trainEval.evaluateModel(knn, data);
//        System.out.println(trainEval.toSummaryString());
        
        int trainNum = (int) (data.numInstances()*.7d);
        int testNum = data.numInstances() - trainNum;
        // seperate into a training set and a test set
        Instances train = new Instances(data,0,trainNum);
        Instances test = new Instances (data,trainNum,testNum);
        
         knn.buildClassifier(train);
           int correct = 0;
        int total = 0;
        for(int i = 0; i < test.numInstances(); i++){
            Instance check = test.instance(i);
          //  System.out.println(check.classValue());
            if (check.classValue() == knn.classifyInstance(check)){
                correct ++;
            }
            total ++;
        }
        Evaluation knnEval = new Evaluation(test);
      //  knnEval.evaluateModel(knn,test);
       // System.out.println(knnEval.toSummaryString());
        System.out.println(correct + "out of" + total);
//        // evaluate the training set against the hardcode
//        Evaluation trainEval = new Evaluation(train);
//        trainEval.evaluateModel(hardCode, train);
//        System.out.println(trainEval.toSummaryString());
//         // evaluate the test set against the hardcode
//        Evaluation testEval = new Evaluation(test);
//        testEval.evaluateModel(hardCode, test);
//        System.out.println(testEval.toSummaryString());
//        
//        LinearNNSearch neighborSearch = new LinearNNSearch(train);
//        Instance toIdentify;
//        
//        // TODO code application logic here
//        toIdentify = new Instance(train.instance(rand.nextInt(train.numInstances())));
//        System.out.println(toIdentify);
//        System.out.println(neighborSearch.kNearestNeighbours(toIdentify, 3));
//        
//        IBk kNN = new IBk();
//        kNN.buildClassifier(train);
//        kNN.setKNN(15);
//        System.out.println(kNN.getKNN());
//        Evaluation closeEval = new Evaluation(train);
//        closeEval.evaluateModel(kNN,train);
//        System.out.println(closeEval.toSummaryString());
        //d(p, q) = \sqrt{(p_1- q_1)^2 + (p_2 - q_2)^2+\cdots+(p_i - q_i)^2+\cdots+(p_n - q_n)^2}.
    }
    
   
    
}
