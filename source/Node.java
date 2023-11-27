import java.util.ArrayList;


public class Node {
    private final ArrayList<DataRecord> dataPoints;

    private int numberOfSamples;

    public ArrayList<DataRecord> getDataPoints() {
        return dataPoints;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    private String[] targetFeatureClassifications = {"E", "ET", "T", "M"};

    private int[] targetFeatureTotals = new int[]{0, 0, 0, 0};
    private int splittingFeatureIndex = 0;
    public Node left = null;
    public Node right = null;

    private int depth;

    private double giniImpurity;

    public Node(ArrayList<DataRecord> dp) {
        this.dataPoints = dp;
    }

    public Node() {
        this.dataPoints = new ArrayList<>();
    }

    public Node(ArrayList<DataRecord> dPoints, int depth) {
        this.dataPoints = dPoints;
        this.numberOfSamples = dPoints.size();

        this.depth = depth;

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

    public void getBestSplit(ArrayList<String> splittingFeatures, int MIN_SAMPLES, int MAX_DEPTH) {
        if (numberOfSamples < MIN_SAMPLES || depth < MAX_DEPTH) {
            return;
        }

        double lowestImpurity = Double.POSITIVE_INFINITY;

        for (int i = 0; i < splittingFeatures.size(); i++) {
            double impurityOfSplit = split(splittingFeatures.get(i), lowestImpurity, i);
            if (impurityOfSplit < lowestImpurity) {
                lowestImpurity = impurityOfSplit;
            }
        }
        //remove the that index from the bootstrapped features
        splittingFeatures.remove(splittingFeatureIndex);
        left.getBestSplit(splittingFeatures, MIN_SAMPLES, MAX_DEPTH);
        right.getBestSplit(splittingFeatures, MIN_SAMPLES, MAX_DEPTH);

    }


    public double split(String splittingFeature, double bestImpurity, int index) {
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
        Node lNode = new Node(left, depth + 1);
        Node rNode = new Node(right, depth + 1);
        double leftWeightedImpurity = ((double) left.size() / dataPoints.size()) * lNode.getGiniImpurity();
        double rightWeightedImpurity = ((double) right.size() / dataPoints.size()) * rNode.getGiniImpurity();
        if (leftWeightedImpurity + rightWeightedImpurity < bestImpurity) {
            this.left = lNode;
            this.right = rNode;
            this.splittingFeatureIndex = index;
            return leftWeightedImpurity + rightWeightedImpurity;
        }


        return bestImpurity;

    }

    public double calcGiniImpurity() {
        double gini = 1, classProbability;
        for (int count : this.targetFeatureTotals) {
            classProbability = (double) count / dataPoints.size();
            gini -= Math.pow(classProbability, 2);
        }
        return gini;
    }

    public void calcFeatureTotals() {
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