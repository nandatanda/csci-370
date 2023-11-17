import java.util.ArrayList;
public class DecisionTree {

    //Root node of the decision
    private Node<DataPoint> root;
    private DataSet bootStrappedDataSet;
    private int maxDepth;
    private int minNumSamples;
    private int votesForDataPoint;
    private ArrayList<String> featuresSelected = new ArrayList<String>();
    private ArrayList<String> baggedFeatures = new ArrayList<String>();

    //Decision Tree Constructor
    public DecisionTree(DataSet bootStrappedDataSet, int minNumSamples, int maxDepth)
    {
        this.bootStrappedDataSet = bootStrappedDataSet;
        this.minNumSamples = minNumSamples;
        this.maxDepth = maxDepth;
    }
/**
 * wfwrg
 */
    public buildTree(DataSet bootStrappedDataSet, String targetFeature)
    {

    }

    public void displayOutput()
    {

    }
}
