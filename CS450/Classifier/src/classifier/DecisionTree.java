/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.ArrayList;
import javax.swing.tree.TreeNode;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import static weka.core.Utils.log2;

/**
 *
 * @author Stephen
 */
public class DecisionTree extends Classifier {

    int attributes;
    ArrayList<Attribute> attr;
    Tree tree;
    double rootEntropy;
    double[] entropies;

    ArrayList<Instance>[] check;

    @Override
    public void buildClassifier(Instances i) throws Exception {
        // ~/\~ add escape for leaf nodes
        ArrayList<Instance> data;
        attr = new ArrayList<>();
        data = new ArrayList<>();
        // initialize data
        for (int j = 0; j < i.numInstances(); j++) {
            data.add(i.instance(j));
        }
        // find entropy for the class attribute
        rootEntropy = mainEntropy(data, data.get(0).classAttribute());
        attributes = data.get(0).numAttributes() - 1;
        // get list of attributes
        for (int j = 0; j < attributes; j++) {
            attr.add(data.get(0).attribute(j));
        }
        //start the root node set its data and attributes
        tree = new Tree();
        tree.getRoot().setInfo(data);
        tree.getRoot().setRemaining(attr);
        tree.getRoot().setEntropy(rootEntropy);
        Node ptr;
        ptr = tree.getRoot();

        buildTree(ptr);
        printTree(ptr, 0);

    }

    @Override
    public String getRevision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double classifyInstance(Instance instnc) throws Exception {
        double guess = -1;
        //  System.out.println(tree.getRoot().getData());
        int val = (int) instnc.value(tree.getRoot().getData());
        //  System.out.println(val);
        Node n = tree.getRoot();
        for (int i = 0; i < instnc.numAttributes(); i++) {

            if (n.getAttrVal() != -1) {
                guess = n.getAttrVal();
                break;
            } else {

                n = n.getChildren().get(val);
                if (n.getData() == null) {
                    guess = n.getAttrVal();
                    break;
                }
                //     System.out.println(n.getData());

                val = (int) instnc.value(n.getData());
                //    System.out.println(val);
            }
        }
        if (guess == -1) {
            guess = tree.getRoot().getInfo().get(0).numClasses() / 2;
        }
        return guess;
    }

    public void buildTree(Node ptr) {
        // if there is no data return
        if (ptr.getInfo().isEmpty()) {
            System.out.println("No Data!");
            return;
        }
        for (int i = 0; i < ptr.getRemaining().size(); i++) {
            System.out.println(ptr.getRemaining().get(i).toString());
        }
        // check to see if all class values are the same
        double classValue = ptr.getInfo().get(0).classValue();
        boolean flag = false;
        for (int i = 0; i < ptr.getInfo().size(); i++) {
            if (classValue != ptr.getInfo().get(i).classValue()) {
                flag = true;
            }
        }
        // if all classes are the same set the nodes attrval and return
        if (!flag) {
            System.out.println(ptr.getData());
            System.out.println("create leaf node " + classValue + "to parent node" + ptr.getParent().getData().toString());
            ptr.setAttrVal(classValue);
            System.out.println(ptr.getAttrVal());
            return;
        }
        if (ptr.getRemaining().isEmpty()) {
            System.out.println("no more attributes to split on");
            ptr.setAttrVal(mostLikely(ptr.getInfo()));
            return;
        }

        System.out.println("size of data" + ptr.getInfo().size());
        double[] entropyValues = new double[ptr.getRemaining().size()];
        // find entropy of the different attributes
        for (int j = 0; j < ptr.getRemaining().size(); j++) {
            entropyValues[j] = entropy(ptr.getRemaining().get(j), ptr.getInfo());
        }
        // find which attribute has the greatest info gain
        Attribute split = getSplit(entropyValues, rootEntropy, ptr.getInfo(), ptr.getRemaining());
        ptr.setData(split);
        //remove the attribute from the list of attributes to check
        ptr.getRemaining().remove(split);
        check = new ArrayList[split.numValues()];
        for (int i = 0; i < split.numValues(); i++) {
            check[i] = new ArrayList();
        }
        // split the data based on attribute value
        for (int k = 0; k < ptr.getInfo().size(); k++) {
            check[(int) ptr.getInfo().get(k).value(split)].add(ptr.getInfo().get(k));

        }

        System.out.println("creating node" + split);
        // make new nodes to put the data of each attribute value
        for (int i = 0; i < check.length; i++) {
            ptr.add(new Node(ptr));
            ptr.getChildren().get(i).setInfo(check[i]);

        }
        for (int i = 0; i < ptr.getChildNum(); i++) {
            buildTree(ptr.getChildren().get(i));

        }

    }

    public double mostLikely(ArrayList<Instance> i) {
        double likely = -1;
        double[] parse;
        parse = new double[i.get(0).numClasses()];
        for (int j = 0; j < i.size(); j++) {
            parse[(int) i.get(j).classValue()]++;

        }
        for (int j = 0; j < parse.length; j++) {
            if (parse[j] > likely) {
                likely = j;
            }
        }

        return likely;

    }

    public double mainEntropy(ArrayList<Instance> type, Attribute a) {
        // ~/\~ add root entropy
        int[] classes = new int[type.get(0).numClasses()];

        for (int j = 0; j < type.size(); j++) {
            classes[(int) type.get(j).value(a)]++;

        }
        double entropy = 0;
        for (int j = 0; j < type.get(0).numClasses(); j++) {
            entropy += -(((double) classes[j] / type.size()) * log2((double) classes[j] / type.size()));
        }

        return entropy;
    }

    public double entropy(Attribute a, ArrayList<Instance> data) {
        check = new ArrayList[a.numValues()];
        for (int i = 0; i < a.numValues(); i++) {
            check[i] = new ArrayList();

        }
        double num = 0;
        double denom = 0;
        double entropy = 0;
        double average = 0;
        entropies = new double[a.numValues()];
        for (int k = 0; k < data.size(); k++) {
            check[(int) data.get(k).value(a)].add(data.get(k));

        }
        //create array of instances to send to next level etc...
        for (int j = 0; j < a.numValues(); j++) {
            entropy = 0;
            num = 0;
            denom = 0;
            denom = check[j].size();

            for (int l = 0; l < data.get(0).classAttribute().numValues(); l++) {
                num = 0;
                for (int i = 0; i < check[j].size(); i++) {
                    if (check[j].get(i).classValue() == (double) l) {
                        num++;
                    }
                }
                if (Double.isNaN(-((num / denom) * log2(num / denom)))) {
                    entropy += 0;
                } else {
                    entropy -= ((num / denom) * log2(num / denom));
                }

            }
            entropies[j] = entropy;
            average += ((double) check[j].size() / (double) data.size()) * (entropies[j]);

        }

        return average;

    }

    public Attribute getSplit(double[] entropic, double topEntropy, ArrayList<Instance> data, ArrayList<Attribute> remain) {
        ArrayList<Double> infoGain = new ArrayList();
        for (int i = 0; i < entropic.length; i++) {
            double average = topEntropy - entropic[i];
            infoGain.add(average);
        }
        int index = 0;
        double holder = 0;
        for (int i = 0; i < infoGain.size(); i++) {
            if (holder == 0) {
                holder = infoGain.get(i);
                index = i;

            } else if (infoGain.get(i) > holder) {
                holder = infoGain.get(i);
                index = i;
                rootEntropy = entropic[index];

            }

        }
        Attribute split;
        rootEntropy = entropic[index];
        split = remain.get(index);
        return split;

    }

    public void printTree(Node n, int level) {
        System.out.print("At level " + level + ", ");
        if (n.getChildren() == null) {
            return;
        }
        if (n.getData() == null) {
            System.out.println("leaf node: " + n.getAttrVal());
            return;
        } else {
            System.out.println("current node: " + n.getData().toString());
        }
        for (int i = 0; i < n.getChildNum(); i++) {
            printTree(n.getChildren().get(i), level + 1);
        }

    }

}
