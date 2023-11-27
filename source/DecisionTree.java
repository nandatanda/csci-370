

import java.sql.Array;
import java.util.ArrayList;

public class DecisionTree {

    DecisionTree(DataSet boostrappedDataSet, ArrayList<String> baggedFeatures, UserConfig settings) {
        //Generate bootstrap
        this.bootstrappedDataSet = boostrappedDataSet;
        this.baggedFeatures = baggedFeatures;
        this.MIN_SAMPLES = settings.minSamples();
        this.MAX_DEPTH = settings.maxDepth();
        this.TARGET_FEATURES_VALUES = settings.ratings();
        this.root = buildTree(MAX_DEPTH, baggedFeatures, bootstrappedDataSet.getData());

    }

    private final ArrayList<String> TARGET_FEATURES_VALUES;
    private final DataSet bootstrappedDataSet;
    private final ArrayList<String> baggedFeatures;
    private Node root;
    private final int MIN_SAMPLES;
    private final int MAX_DEPTH;


    public Node getRoot() {
        return this.root;
    }

    public int getMinSamples() {
        return this.MIN_SAMPLES;
    }

    public int getMaxDepth() {
        return this.MAX_DEPTH;
    }

    public DataSet getBootstrappedDataSet() {
        return this.bootstrappedDataSet;
    }

    public ArrayList<String> getBaggedFeatures() {
        return this.baggedFeatures;
    }

    public void setRoot(Node rootNode) {
        this.root = rootNode;
    }


    private Node buildTree(int maxDepth, ArrayList<String> baggedFeatures, ArrayList<DataRecord> samplesInNode) {
        if (maxDepth < 0 || samplesInNode.size() < MIN_SAMPLES) return null;
        Node currentNode = new Node(samplesInNode);

        String feature = getBestFeature(baggedFeatures, samplesInNode);
        // Gets left and right split
        ArrayList<ArrayList<DataRecord>> split = getSplit(feature, samplesInNode);
        baggedFeatures.remove(feature);

        if (maxDepth > 0 && samplesInNode.size() > MIN_SAMPLES) {
            currentNode.setLeft(buildTree(maxDepth - 1, baggedFeatures, split.get(0)));
            currentNode.setRight(buildTree(maxDepth - 1, baggedFeatures, split.get(1)));
            if (currentNode.isLeaf()) {
                // label leaf
//                currentNode.labelLeaf(calculateFeatureDistribution(left));
            }
        }
        return currentNode;
    }


    private String getBestFeature(ArrayList<String> candidateFeatures, ArrayList<DataRecord> datapoints) {
        double lowestImpurity = Double.POSITIVE_INFINITY;
        String bestFeature = "";
        for (String candidateFeature : candidateFeatures) {
            double currentImpurity = getSplitImpurity(candidateFeature, datapoints);
            if (currentImpurity < lowestImpurity) {
                lowestImpurity = currentImpurity;
                bestFeature = candidateFeature;
            }
        }
        return bestFeature;
    }


    private double getSplitImpurity(String splittingFeature, ArrayList<DataRecord> datapoints) {
        ArrayList<ArrayList<DataRecord>> split = getSplit(splittingFeature, datapoints);
        double lWeightedImpurity = (double) split.get(0).size() / datapoints.size() * calculateGiniImpurity(split.get(0));
        double rWeightedImpurity = (double) split.get(1).size() / datapoints.size() * calculateGiniImpurity(split.get(1));
        return lWeightedImpurity + rWeightedImpurity;
    }

    public ArrayList<ArrayList<DataRecord>> getSplit(String feature, ArrayList<DataRecord> samples) {
        ArrayList<DataRecord> left = new ArrayList<>();
        ArrayList<DataRecord> right = new ArrayList<>();
        for (DataRecord current : samples) {
            boolean positiveClassification = (boolean) current.get(feature);
            if (positiveClassification) {
                right.add(current);
            } else {
                left.add(current);
            }
        }
        ArrayList<ArrayList<DataRecord>> split = new ArrayList<>();
        split.add(left);
        split.add(right);
        return split;

    }


    public double calculateGiniImpurity(ArrayList<DataRecord> datapoints) {
        int[] featureDistribution = calculateFeatureDistribution(datapoints);
        double gini = 1, classProbability;
        for (int count : featureDistribution) {
            classProbability = (double) count / datapoints.size();
            gini -= Math.pow(classProbability, 2);
        }
        return gini;
    }

    public int[] calculateFeatureDistribution(ArrayList<DataRecord> datapoints) {
        int[] targetFeatureTotals = new int[TARGET_FEATURES_VALUES.size()];
        for (DataRecord r : datapoints) {
            String current = (String) r.get("esrb_rating");
            for (int j = 0; j < TARGET_FEATURES_VALUES.size(); j++) {
                String targetFeatureValue = TARGET_FEATURES_VALUES.get(j);
                if (current.equals(targetFeatureValue)) {
                    targetFeatureTotals[j]++;
                }
            }
        }
        return targetFeatureTotals;

    }


}
