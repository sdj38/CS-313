/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
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
            Classifier dTree = new DecisionTree();

//      System.out.println(dTree.classifyInstance(i));
        int trainNum = (int) (data.numInstances() * .6d);
        int testNum = (data.numInstances() - trainNum) / 2;
        int validNum = (data.numInstances() - trainNum) / 2;

        // seperate into a training set and a Test set
        Instances rtrain = new Instances(data, 0, trainNum);
        Instances rtest = new Instances(data, trainNum, testNum);
        Instances rvalid = new Instances(data, trainNum + testNum, validNum);

        //standarize the values
        standard.setInputFormat(data);
        Instances train = Filter.useFilter(rtrain, standard);
        Instances test = Filter.useFilter(rtest, standard);
        Instances valid = Filter.useFilter(rvalid, standard);


        NeuralNetwork nn = new NeuralNetwork();
        // # of total layers in network
        nn.setLayers(2);
        // # of sets of nodes in the layer the number times the number of class values
        nn.setNodes(3);
        nn.buildClassifier(train);
        double stopCheck = 0;
        int errorCount = 0;
        for (int k = 0; k < 200; k++) {
            int total = 0;
            int correct = 0;
            for (int i = 0; i < train.numInstances(); i++) {

                nn.forwardFeed(train.instance(i));
                nn.backpropogate(train.instance(i));

            }
            // test against validation set
            for (int l = 0; l < valid.numInstances(); l++) {
                if (nn.classifyInstance(valid.instance(l)) == valid.instance(l).classValue()) {
                    correct++;
                }
                total++;
            }
            // find accuracy per epoch
            double percent = (double) correct / (double) total;
            // if over 99% stop training
           
            // if it starts getting less accurate for too long end it
            if (k > 45) {
                 if (percent >= .99) {
                break;
            }
                if (stopCheck > percent) {
                    errorCount++;
                    if (errorCount > 7) {
                        break;
                    }
                } else {

                    stopCheck = percent;
                    errorCount = 0;
                }
            }
            System.out.println("Accuracy after " + k + " repetitions" + " = " + correct + "/" + total + " " + percent + "%");

        }
        Evaluation nVal = new Evaluation(test);
        nVal.evaluateModel(nn, test);
        System.out.println(nVal.toSummaryString());
        KNNClassifier knn = new KNNClassifier();
        knn.buildClassifier(train);
        Evaluation kval = new Evaluation(test);
        kval.evaluateModel(knn, test);
        System.out.println(kval.toSummaryString());
        
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        mlp.buildClassifier(train);
       
//        
//        dTree.buildClassifier(train);
        Evaluation eval = new Evaluation(test);
        eval.evaluateModel(mlp, test);
        System.out.println(eval.toSummaryString());
//
//        Id3 tree = new Id3();
//        tree.buildClassifier(train);
//         Evaluation tval = new Evaluation(test);
//        tval.evaluateModel(tree, test);
//        System.out.println(tval.toSummaryString());

    }

}
