import java.util.ArrayList;
import java.util.Random;
public class DecisionTree {

    //Root node of the decision
    private Node<DataPoint> root;
    private DataSet bootStrappedDataSet;
    private int dSetLength;
    private int maxDepth;
    private int minNumSamples;
    private int votesForDataPoint;
    private ArrayList<String> featuresSelected = new ArrayList<String>();
    private ArrayList<String> baggedFeatures = new ArrayList<String>();
    private ArrayList<DataPoint> dataPointList = new ArrayList<DataPoint>();


    //Decision Tree Constructor
    DecisionTree(DataSet dSet, int minNumSamples, int maxDepth)
    {
       //Generate bootstrap
        this.dSetLength = dSet.dataPoints.size();
        //Randomize function
        this.dataPointList = dSet.dataPoints;
        this.minNumSamples = minNumSamples;
        this.maxDepth = maxDepth;
    }
/**
 * wfwrg
 */
    public buildTree(DataSet bootStrappedDataSet, String targetFeature)
    {

    }

    public void generateBootStrapDataSet(DataSet dSet)
    {
        //Randomly generate bootstrap dataset
        Random rand = new Random();
        for (int i = 0; i < this.dSetLength; i++) {
            int r = rand.nextInt(this.dSetLength);
            DataPoint current = new DataPoint();
        }
    }


    public void displayOutput()
    {

    }
}
