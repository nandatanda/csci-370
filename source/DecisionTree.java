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

    // MinHeap to store the best nodes for splitting
    private final MinHeap bestNodes;

    /**
     * Constructor for the DecisionTree class.
     *
     * @param bootstrappedDataSet the bootstrap dataset used for building the tree
     * @param baggedFeatures     the list of features used for building the tree
     */
    DecisionTree(DataSet bootstrappedDataSet, ArrayList<String> baggedFeatures) {
        data = bootstrappedDataSet;
        root = new Node(data);
        features = baggedFeatures;
        bestNodes = new MinHeap();

        bestNodes.insert(root);
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
     * Get the MinHeap storing the best nodes for splitting.
     *
     * @return the MinHeap storing the best nodes for splitting
     */
    public MinHeap bestNodes() {
        return bestNodes;
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
     * @param newNode the current node in the tree being processed
     * @param depth   the depth of the current node in the tree
     */
    private void buildTree(Node newNode, int depth) {
        int min_samples = Main.settings().minSamples();
        int max_depth = Main.settings().maxDepth();

        // Base case: if depth exceeds the maximum depth or the number of samples is below the threshold, assign a label to the node
        if (depth >= max_depth || newNode.data().size() <= min_samples) {
            newNode.assignLabel();
            // Another scenario is when it is a leaf node but doesn't satisfy this criteria
            return;
        }

        // Get the best node from the MinHeap
        Node parent = bestNodes.removeMin();

        // Iterate through features and create left and right child nodes
        for (String feature : features) {
            Node left = new Node(feature);
            Node right = new Node(feature);

            // Split the records based on the feature
            for (DataRecord record : newNode.data()) {
                if (record.get(feature)) {
                    right.add(record);
                } else {
                    left.add(record);
                }
            }

            // Calculate impurity for left and right nodes
            left.calculateImpurity();
            right.calculateImpurity();

            bestNodes.insert(parent);
        }

        // Get the best node from the MinHeap
        Node bestNode = bestNodes.removeMin();

        // Determine whether to go left or right based on impurity
        if (newNode.left().giniImpurity() < newNode.giniImpurity()) {
            bestNodes.insert(bestNode.left());
            // Label the leaf because we are no longer splitting on the right side
            newNode.right().assignLabel();
            buildTree(bestNode.left(), depth + 1);
        } else {
            bestNodes.insert(bestNode.right());
            // Label the leaf because we are no longer splitting on the left side
            newNode.left().assignLabel();
            buildTree(bestNode.right(), depth + 1);
        }
    }

    private double getSplitImpurity(Node parent, Node left, Node right) {
        double lWeighted = (double) left.data().size() / parent.data().size() * left.giniImpurity();
        double rWeighted = (double) right.data().size() / parent.data().size() * right.giniImpurity();
        return lWeighted + rWeighted;
    }

    public String castVote(DataRecord datapoint) {
        // start form the root and loop through and get the majority label if datapoint classifies
        Node currentNode = root;
        while (!currentNode.isLeaf()) {
            String feature = currentNode.splittingFeature();
            if ((boolean) datapoint.get(feature)) {
                currentNode = currentNode.right();
            } else {
                currentNode = currentNode.left();
            }
        }
        return currentNode.label();
    }
}