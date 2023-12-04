/**
 * The {@code Node} class represents a node in a decision tree used for classification.
 * Each node contains information about the data it represents, the splitting feature,
 * and impurity measures for decision tree construction.
 */
public class Node {

    private final RatingsMap ratingsCount;
    private String splittingFeature;
    private double giniIndex;
    private double giniImpurity;

    private final DataSet data;
    private Node left;
    private Node right;
    private String label;

    /**
     * Default constructor for a node with an empty dataset.
     */
    public Node() {
        ratingsCount = new RatingsMap();
        ratingsCount.initialize();
        splittingFeature = "";
        giniIndex = 0.0;
        giniImpurity = 0.0;

        data = new DataSet();
        left = null;
        right = null;
        label = "";
    }

    /**
     * Constructor for a node with a specified dataset.
     *
     * @param data the dataset associated with the node
     */
    public Node(DataSet data) {
        ratingsCount = new RatingsMap();
        ratingsCount.initialize();
        splittingFeature = "";
        giniIndex = 0.0;
        giniImpurity = 0.0;

        this.data = data;
        left = null;
        right = null;
        label = "";
    }

    /**
     * Constructor for a node representing a split on a specific feature with associated ratings.
     *
     * @param feature the feature on which the split is performed
     */
    public Node(String feature) {
        ratingsCount = new RatingsMap();
        ratingsCount.initialize();
        splittingFeature = feature;
        giniIndex = 0.0;
        giniImpurity = 0.0;

        this.data = new DataSet();
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
     * Get the splitting feature for the node.
     *
     * @return the splitting feature for the node
     */
    public String splittingFeature() {
        return this.splittingFeature;
    }

    /**
     * Set the splitting feature for the node.
     *
     * @param f the splitting feature to set
     */
    public void setSplittingFeature(String f) {
        this.splittingFeature = f;
    }

    /**
     * Calculate the impurity of the node using Gini impurity measure.
     */
    public void calculateImpurity() {
        double gini = 1, classProbability;
        for (String f : ratingsCount.keySet()) {
            classProbability = (double) ratingsCount.get(f) / data.size();
            gini -= Math.pow(classProbability, 2);
        }
        giniImpurity = gini;
    }

    /**
     * Get the Gini index of the node.
     *
     * @return the Gini index of the node
     */
    public double giniIndex() {
        return giniIndex;
    }

    /**
     * Get the Gini impurity of the node.
     *
     * @return the Gini impurity of the node
     */
    public double giniImpurity() {
        return giniImpurity;
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
     * Check if the node is a leaf node.
     *
     * @return true if the node is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        return (left == null && right == null);
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

    public void splitBy(String feature) {
        
    }
}
