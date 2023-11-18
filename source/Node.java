import java.util.ArrayList;
import java.util.Objects;


public class Node{
    private final ArrayList<DataPoint> dataPoints;
    //temporarily hard-coding to have an idea
    private String[] targetFeatureClassifications = {"E", "ET", "T", "M"};

    private int [] targetFeatureTotals = new int[]{0,0,0,0};
    private String[] randomFeatureSubset;
    public Node left = null;
    public Node right = null;

    private double giniIndex;
    public Node(ArrayList<DataPoint> dp){
        this.dataPoints = dp;
    }

    public Node(){
        this.dataPoints = new ArrayList<>();
    }

    public Node(ArrayList<DataPoint> dPoints, String[] targetFeatureClassifications, String[] randomFeatureSubset){
        this.dataPoints = dPoints;
        this.targetFeatureClassifications = targetFeatureClassifications;
        this.randomFeatureSubset = randomFeatureSubset;
        // count the number of points that classify as one of the target features E, ET, T, M
        calcFeatureTotals();
        calcGiniIndex();


    }

    public double getGiniIIndex() {
        return giniIndex;
    }

    public void setGiniIndex(double giniImpurity) {
        this.giniIndex = giniImpurity;
    }

    public void getBestSplit(){
        // starts at the parent
        double bestIndex = this.giniIndex;
        for(String feature : randomFeatureSubset){
            split(feature, bestIndex);
        }
        //it should split based on the best feature

    }

    private void split(String splittingFeature, double bestIndex){

        ArrayList<DataPoint> left = new ArrayList<>();
        ArrayList<DataPoint> right = new ArrayList<>();
        // separate two based on the threshold
        for (DataPoint current : dataPoints) {
            boolean isNegativeClass = (boolean) current.getData().get(splittingFeature);
            if (isNegativeClass) {
                left.add(current);
            } else {
                right.add(current);
            }
        }

        Node lNode = new Node(left, targetFeatureClassifications, randomFeatureSubset);
        Node rNode = new Node(right, targetFeatureClassifications, randomFeatureSubset);
        if (lNode.getGiniIIndex() + rNode.getGiniIIndex() < bestIndex){
            this.left = lNode;
            this.right = rNode;
        }

    }


    public double calcGiniIndex(){
        double gini = 1, classProbability;
        for (int count : this.targetFeatureTotals) {
            classProbability = (double) count / dataPoints.size();
            gini -= Math.pow(classProbability, 2);
        }
        return gini;
    }
    public int[] calcFeatureTotals(){
        int[] tempCounts = new int[]{0,0,0,0};
        for(int i = 0; i < dataPoints.size(); i++){
            DataPoint dp = dataPoints.get(i);
            String targetFeatureValue = (String) dp.getData().get("esrb_rating");

            // check each datapoint, if its of a certain target feature, increment feature array
            for(int j = 0; j < targetFeatureClassifications.length; j++){
                String targetFeature = targetFeatureClassifications[j];
                String dpFeatureValue = (String) dataPoints.get(j).getData().get("esrb_rating");
               if (targetFeatureValue.equals(dpFeatureValue)){
                   //add to the featureCount corresponding with that feature
                   tempCounts[i]++;
               }
            }
        }
        return tempCounts;
    }

}