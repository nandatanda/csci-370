import java.util.ArrayList;
import java.util.Random;
public class DecisionTree {

    //Root node of the decision
    private Node root;
    private DataSet bootstrappedDataSet;
    private int dSetLength;
    private int maxDepth;
    private int minNumSamples;
    private int votesForDataPoint;
    private ArrayList<String> featuresSelected = new ArrayList<String>();
    private ArrayList<String> baggedFeatures = new ArrayList<String>();
    private ArrayList<DataRecord> dataPointList = new ArrayList<DataRecord>();


    //Decision Tree Constructor
    DecisionTree(DataSet dSet, int minNumSamples, int maxDepth)
    {
       //Generate bootstrap
        this.dSetLength = dSet.data.size();
        //Randomize function
        this.dataPointList = dSet.data;
        this.minNumSamples = minNumSamples;
        this.maxDepth = maxDepth;
    }
/**
 * wfwrg
 */
   DecisionTree (DataSet bootstrappedDataSet, String targetFeature)
    {
        this.bootstrappedDataSet = bootstrappedDataSet;

    }







    public void displayOutput()
    {

    }
}
