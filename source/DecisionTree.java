import com.sun.source.tree.BinaryTree;

import java.util.ArrayList;

public class DecisionTree {

    private final int MAX_DEPTH;
    private final int MIN_SAMPLES;
    private Node root;

    private int height;


    private DataSet bootstrappedDataSet;
    private ArrayList<String> baggedFeatures;
    private int votesForDataPoint;




    //Decision Tree Constructor
    DecisionTree(DataSet boostrappedDataSet, ArrayList<String> baggedFeatures, int MIN_SAMPLES, int MAX_DEPTH)
    {
       //Generate bootstrap
        this.bootstrappedDataSet = boostrappedDataSet;
        this.baggedFeatures = baggedFeatures;
        this.MIN_SAMPLES = MIN_SAMPLES;
        this.MAX_DEPTH = MAX_DEPTH;
        //this.root = new Node(bootstrappedDataSet.getData(), 0);
        this.root.getBestSplit(baggedFeatures, MIN_SAMPLES, MAX_DEPTH);
    }
/**
 * wfwrg
 */









    public void displayOutput()
    {

    }
}
