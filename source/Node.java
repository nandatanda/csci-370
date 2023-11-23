import java.util.ArrayList;


public class Node{
    private final ArrayList<DataRecord> dataPoints;
    //temporarily hard-coding to have an idea
    private String[] targetFeatureClassifications = {"E", "ET", "T", "M"};

    private int [] targetFeatureTotals = new int[]{0,0,0,0};
    private String[] randomFeatureSubset;
    public Node left = null;
    public Node right = null;

    private double giniImpurity;
    private double splittingFeatureIndex;
    public Node(ArrayList<DataRecord> dp){
        this.dataPoints = dp;
    }

    public Node(){
        this.dataPoints = new ArrayList<>();
    }

    public Node(ArrayList<DataRecord> dPoints, String[] targetFeatureClassifications, String[] randomFeatureSubset){
        this.dataPoints = dPoints;
        this.targetFeatureClassifications = targetFeatureClassifications;
        this.randomFeatureSubset = randomFeatureSubset;
        calcFeatureTotals();
        // count the number of points that classify as one of the target features E, ET, T, M
        this.giniImpurity = calcGiniImpurity();


    }

    public double getGiniImpurity() {
        return giniImpurity;
    }

    public void setGiniIndex(double giniImpurity) {
        this.giniImpurity = giniImpurity;
    }

    public void getBestSplit(){
        double bestImpurity = Double.POSITIVE_INFINITY;

        for(int i = 0; i < randomFeatureSubset.length; i++){
            double impurityOfSplit = split(randomFeatureSubset[i], bestImpurity, i);
            if (impurityOfSplit < bestImpurity){
                bestImpurity = impurityOfSplit;
            }

        }

    }

    private double split(String splittingFeature, double bestImpurity, int index){
        ArrayList<DataRecord> left = new ArrayList<>();
        ArrayList<DataRecord> right = new ArrayList<>();
        for (DataRecord current : dataPoints) {
            boolean isNegativeClass = (boolean) current.get(splittingFeature);
            if (isNegativeClass) {
                left.add(current);
            } else {
                right.add(current);
            }
        }
        // create the left & right nodes to initialize and get gini index
        Node lNode = new Node(left, targetFeatureClassifications, randomFeatureSubset);
        double leftWeightedImpurity = ( (double) left.size() / dataPoints.size() ) * lNode.getGiniImpurity();
        Node rNode = new Node(right, targetFeatureClassifications, randomFeatureSubset);
        double rightWeightedImpurity = ( (double) right.size() / dataPoints.size() ) * rNode.getGiniImpurity();
        if (leftWeightedImpurity + rightWeightedImpurity < bestImpurity){
            this.left = lNode;
            this.right = rNode;
            this.splittingFeatureIndex = index;
        }

        return leftWeightedImpurity + rightWeightedImpurity;

    }

    public double calcGiniImpurity(){
        double gini = 1, classProbability;
        for (int count : this.targetFeatureTotals) {
            classProbability = (double) count / dataPoints.size();
            gini -= Math.pow(classProbability, 2);
        }
        return gini;
    }
    public void calcFeatureTotals(){
        for (DataRecord dp : dataPoints) {
            String targetFeatureValue = (String) dp.get("esrb_rating");

            for (int j = 0; j < targetFeatureClassifications.length; j++) {
                String classification = targetFeatureClassifications[j];

                if (targetFeatureValue.equals(classification)) {
                    targetFeatureTotals[j]++;
                }
            }
        }
    }

}