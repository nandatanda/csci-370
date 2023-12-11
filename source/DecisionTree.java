import java.util.ArrayList;

/**
 * The {@code DecisionTree} class represents a decision tree used for classification.
 * It contains methods to build and manipulate the decision tree.
 */
public class DecisionTree {

    // The dataset used to build the tree
    private final DataSet data;

    // The root node of the decision tree
    private Node root;

    // The list of features used for building the tree
    private final ArrayList<String> features;

    private final int maxDepth = Main.settings().maxDepth();

    private final int minSamples = Main.settings().minSamples();

    /**
     * Constructor for the DecisionTree class.
     *
     * @param bootstrappedDataSet the bootstrap dataset used for building the tree
     * @param baggedFeatures      the list of features used for building the tree
     */
    DecisionTree(DataSet bootstrappedDataSet, ArrayList<String> baggedFeatures) {
        data = bootstrappedDataSet;
        root = new Node(data);
        features = baggedFeatures;

        buildTree(this.root, 0);
    }

    /**
     * Get the dataset used for building the tree.
     *
     * @return the dataset used for building the tree
     */
    public DataSet data() {
        return data;
    }

    /**
     * Get the root node of the decision tree.
     *
     * @return the root node of the decision tree
     */
    public Node root() {
        return root;
    }

    /**
     * Get the list of features used for building the tree.
     *
     * @return the list of features used for building the tree
     */
    public ArrayList<String> features() {
        return features;
    }

    /**
     * Set the root node of the decision tree.
     *
     * @param rootNode the root node to set
     */
    public void setRoot(Node rootNode) {
        this.root = rootNode;
    }

    /**
     * Build the decision tree recursively using the best nodes for splitting.
     *
     * @param node the current node in the tree being processed
     * @param depth   the depth of the current node in the tree
     */
    private void buildTree(Node node, int depth) {
        // Base case: check if the current depth exceeds the maximum depth or the number of samples is below the threshold
        if (depth >= maxDepth || node.size() <= minSamples) {
            node.assignLabel(); // Assign a label to the leaf node
            return;
        }

        // Evaluate the best split among the remaining features
        node.performBestSplit(features);

        // Remove the splitting feature from the list of available features
        features.remove(node.splitFeature());

        // Recursively build the left and right subtrees
        if (node.left() != null) {
            buildTree(node.left(), depth + 1);
        }

        if (node.right() != null) {
            buildTree(node.right(), depth + 1);
        }

        // Restore the splitting feature to the list of available features after the recursion
        features.add(node.splitFeature());
    }

    public String castVote(DataRecord datapoint) {
        // start form the root and loop through and get the majority label if datapoint classifies
        Node currentNode = root;
        while (!currentNode.isLeaf()) {
            String feature = currentNode.splitFeature();
            if ((boolean) datapoint.get(feature)) {
                currentNode = currentNode.right();
            } else {
                currentNode = currentNode.left();
            }
        }
        return currentNode.label();
    }
}