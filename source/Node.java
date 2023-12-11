import java.util.ArrayList;

/**
 * The {@code Node} class represents a node in a decision tree used for classification.
 * Each node contains information about the data it represents, the splitting feature,
 * and impurity measures for decision tree construction.
 */
public class Node {

    // Ratings count for each class label in the dataset
    private final RatingsMap ratingsCount;

    // Feature used for splitting the node
    private String splitFeature;

    // Gini impurity of the node
    private double impurity;

    // Weighted Gini impurity of the child nodes
    private double weightedImpurity;

    // Dataset associated with the node
    private DataSet data;

    // Left child node
    private Node left;

    // Right child node
    private Node right;

    // Label assigned to the node
    private String label;

    /**
     * Default constructor for a node with an empty dataset.
     */
    public Node() {
        ratingsCount = new RatingsMap();
        ratingsCount.initialize();

        data = new DataSet();
        left = null;
        right = null;

        label = "";
        splitFeature = "";
        updateImpurity();
        updateWeightedImpurity();
    }

    /**
     * Constructor for a node with a specified dataset.
     *
     * @param data the dataset associated with the node
     */
    public Node(DataSet data) {
        ratingsCount = new RatingsMap();
        ratingsCount.initialize();
        this.data = new DataSet();

        for(DataRecord r : data.data()){
            add(r);
        }

        this.data = data;
        left = null;
        right = null;

        label = "";
        splitFeature = "";
        updateImpurity();
        updateWeightedImpurity();
    }

    /**
     * Get the dataset associated with the node.
     *
     * @return the dataset associated with the node
     */
    public DataSet data() {
        return data;
    }

    /**
     * Get the splitting feature for the node.
     *
     * @return the splitting feature for the node
     */
    public String splitFeature() {
        return this.splitFeature;
    }

    /**
     * Add a data record to the node's dataset and update ratings count.
     *
     * @param record the data record to be added
     */
    public void add(DataRecord record) {
        String rating = record.rating();
        ratingsCount.increment(rating);
        data.add(record);
    }

    /**
     * Get the Gini impurity of the node.
     *
     * @return the Gini impurity of the node
     */
    public double impurity() {
        return impurity;
    }

    /**
     * Calculate the impurity of the node using Gini impurity measure.
     */
    public void updateImpurity() {
        if (size() == 0) {
            weightedImpurity = Double.NaN;
        } else {
            double sum = 0.0;
            for (String rating : ratingsCount.keySet()) {
                sum += Math.pow((double) ratingsCount.get(rating) / data.size(), 2);
            }
            impurity = 1 - sum;
        }
    }

    /**
     * Get the weighted Gini impurity of the child nodes.
     *
     * @return the weighted impurity of the children
     */
    public double weightedImpurity() {
        return weightedImpurity;
    }

    /**
     * Update the weighted Gini impurity of the node's children.
     */
    public void updateWeightedImpurity() {
        if (size() == 0 || left == null || right == null) {
            weightedImpurity = Double.NaN;
        } else {
            double leftImpurity = (double) left.size() / this.size() * left.impurity();
            double rightImpurity = (double) right.size() / this.size() * right.impurity();
            weightedImpurity = leftImpurity + rightImpurity;
        }
    }

    /**
     * Get the left child node.
     *
     * @return the left child node
     */
    public Node left() {
        return left;
    }

    /**
     * Get the right child node.
     *
     * @return the right child node
     */
    public Node right() {
        return right;
    }

    /**
     * Get the label assigned to the node.
     *
     * @return the label assigned to the node
     */
    public String label() {
        return this.label;
    }

    /**
     * Get the number of records contained in a node.
     *
     * @return the size of the node
     */
    public int size() {
        return data.size();
    }

    /**
     * Check if the node is a leaf node.
     *
     * @return true if the node is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        return (left == null && right == null);
    }

    /**
     * Assign a label to the node based on the majority feature in the leaf.
     */
    public void assignLabel() {
        // Label majority feature in leaf
        String majority = "";
        int greatestCount = 0;
        for (String f : ratingsCount.keySet()) {
            int currentCount = ratingsCount.get(f);
            if (currentCount > greatestCount) {
                greatestCount = currentCount;
                majority = f;
            }
        }
        label = majority;
    }

    /**
     * Evaluate the best split among a list of candidate features.
     *
     * @param candidates the list of candidate features
     */
    public void performBestSplit(ArrayList<String> candidates) {
        String bestFeature = "";
        double bestImpurity = Double.POSITIVE_INFINITY;

        // Evaluate a split on each feature
        for (String feature : candidates) {
            // Reset existing children
            left = null;
            right = null;

            // Split into new children
            splitFeature = feature;
            splitData();

            // Find the impurity of the split
            updateWeightedImpurity();
            if (weightedImpurity < bestImpurity) {
                // If this split is the best so far, store the feature and value
                bestFeature = splitFeature;
                bestImpurity = weightedImpurity;
            }
        }

        // After evaluating all candidates, store the winning values to class fields
        splitFeature = bestFeature;
        weightedImpurity = bestImpurity;

        // Reset children and perform the winning split
        left = null;
        right = null;
        splitData();
    }

    /**
     * Splits the data into left and right children based on the splitting feature.
     */
    private void splitData() {
        // Iterate through the data to split into left and right children
        for (DataRecord record : data) {
            // If the record has the splitting feature, add it to the right child
//            if(record.get(splitFeature) == null){
//                return;
//
//            }
            if (record.get(splitFeature)) {
                if (right == null) {
                    right = new Node();
                }
                right.add(record);
            } else {
                // Otherwise, add it to the left child
                if (left == null) {
                    left = new Node();
                }
                left.add(record);
            }
        }

        // solves the exception: Cannot read field "data" because "this.right" is null
        if(left != null){
            left.updateImpurity();
        }
        if(right != null){
            right.updateImpurity();
        }


    }
}