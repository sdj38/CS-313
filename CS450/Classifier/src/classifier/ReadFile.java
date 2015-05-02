/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classifier;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * This class reads a file from a csv file and store it in a Instances to be
 * able to perform analysis on.
 * @author Stephen
 */
public class ReadFile {
    CSVLoader loader;
    DataSource source;
    Instances data;
    public ReadFile(){
        
    }
    /*
    This function will read in from a csv file and store it into an Instaces class
    */
    public void read(String fileName){
            data = null;
            
        try {
            // this loads a csv file
            loader = new CSVLoader();
            loader.setSource(new File(fileName));
            // set instances data to what the csv reads
            data = loader.getDataSet();
            //set the class to the last set of attributes for an instance
            if(data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);

        } catch (IOException e) {
            System.out.println("unable to find " + fileName);
        }
   // System.out.print(data);
    
}   

    public Instances getData() {
        return data;
    }

    public void setData(Instances irisData) {
        this.data = irisData;
    }
    
}