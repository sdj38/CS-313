/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classifier;

import java.util.ArrayList;
import weka.core.Attribute;

/**
 *
 * @author Stephen
 */
    public class Tree {
private Node root;

public Tree() {
    root = new Node(null);
    
   
}

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

   
    


}
    

