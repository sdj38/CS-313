/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.Id3;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

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
        reader.read("C://Users//Stephen//Desktop//Datasets//iris.csv");
        // sets data equal to what the reader class gathers
        Instances data = new Instances(reader.getData());
        // randomize the data set
        data.randomize(rand);
        Standardize standard = new Standardize();
    //    Classifier dTree = new DecisionTree();

//      System.out.println(dTree.classifyInstance(i));
        int trainNum = (int) (data.numInstances() * .7d);
        int testNum = data.numInstances() - trainNum;

        // seperate into a training set and a Test set
        Instances rtrain = new Instances(data, 0, trainNum);
        Instances rtest = new Instances(data, trainNum, testNum);
        //standarize the values
        standard.setInputFormat(rtrain);
        Instances train = Filter.useFilter(rtrain, standard);
        Instances test = Filter.useFilter(rtest, standard);
//         set the number of neighbors
          
        NeuralNetwork nn = new NeuralNetwork();
        nn.buildClassifier(train);
        Evaluation nVal = new Evaluation (test);
        nVal.evaluateModel(nn,test);
        System.out.println(nVal.toSummaryString());
//        KNNClassifier knn = new KNNClassifier();
//        knn.buildClassifier(train);
//        Evaluation kval = new Evaluation(test);
//        kval.evaluateModel(knn,test);
//        System.out.println(kval.toSummaryString());
//        
//        dTree.buildClassifier(train);
//        Evaluation eval = new Evaluation(test);
//        eval.evaluateModel(dTree, test);
//        System.out.println(eval.toSummaryString());
//
//        Id3 tree = new Id3();
//        tree.buildClassifier(train);
//         Evaluation tval = new Evaluation(test);
//        tval.evaluateModel(tree, test);
//        System.out.println(tval.toSummaryString());
      
    }

}
