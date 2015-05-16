/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classifier;

import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.Instance;

/**
 *
 * @author Stephen
 */
public class Node {
      private Attribute data;
    private Node parent;
    private ArrayList<Instance> info;
    private ArrayList<Node> children;
    private ArrayList<Attribute> remaining;
    private double attrVal = -1;
    private int childNum = 0;
    private double entropy = 0;
    boolean set = false;
    public Node(Node parent){
        this.parent = parent;
        info = new ArrayList();
        children = new ArrayList();
        if(parent != null){
        remaining = new ArrayList();
        for(int i = 0; i < parent.getRemaining().size(); i ++){
            remaining.add(parent.getRemaining().get(i));
        }
        }
       
    }
    public void add(Node n){
        this.getChildren().add(n);
        childNum++;
    }

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }
    
        public Attribute getData() {
            return data;
        }

        public void setData(Attribute data) {
            this.data = data;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public ArrayList<Node> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<Node> children) {
            this.children = children;
        }

    public ArrayList<Instance> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Instance> info) {
        this.info = info;
    }

    public ArrayList<Attribute> getRemaining() {
        return remaining;
    }

    public void setRemaining(ArrayList<Attribute> remaining) {
        this.remaining = remaining;
    }

    public double getAttrVal() {
        return attrVal;
    }

    public void setAttrVal(double attrVal) {
        this.attrVal = attrVal;
    }

    public double getEntropy() {
        return entropy;
    }

    public void setEntropy(double entropy) {
        this.entropy = entropy;
    }

    public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }
    
    
    
}
