import java.util.ArrayList;

public class DecisionTree {

    DecisionTree(DataSet boostrappedDataSet, ArrayList<String> baggedFeatures, UserConfig settings) {
        this.bootstrappedDataSet = boostrappedDataSet;
        this.baggedFeatures = baggedFeatures;
        this.MIN_SAMPLES = settings.minSamples();
        this.MAX_DEPTH = settings.maxDepth();
        this.TARGET_FEATURES = settings.ratings();
        this.minHeap = new MinHeap();
        this.root = new Node(bootstrappedDataSet.asArrayList());
        minHeap.insert(root);
        buildTree(this.root, 0);

    }

    private final DataSet bootstrappedDataSet;
    private final ArrayList<String> baggedFeatures;
    private final int MIN_SAMPLES;
    private final int MAX_DEPTH;

    private final ArrayList<String> TARGET_FEATURES;
    private final MinHeap minHeap;

    private Node root;

    public DataSet getBootstrappedDataSet() {
        return this.bootstrappedDataSet;
    }

    public ArrayList<String> getBaggedFeatures() {
        return this.baggedFeatures;
    }

    public int getMinSamples() {
        return this.MIN_SAMPLES;
    }

    public int getMaxDepth() {
        return this.MAX_DEPTH;
    }

    public Node getRoot() {
        return this.root;
    }


    public void setRoot(Node rootNode) {
        this.root = rootNode;
    }

    private void buildTree(Node n, int depth) {
        if (depth >= MAX_DEPTH || n.getDataPoints().size() <= MIN_SAMPLES) {
            n.labelLeaf();
            // another scenario is when it is a leaf node but doesn't satisfy this criteria
            return;
        }
        Node parent = minHeap.removeMax();
        for (String f : baggedFeatures) {
            Node left = new Node(f, TARGET_FEATURES);
            Node right = new Node(f, TARGET_FEATURES);
            for (DataRecord d : n.getDataPoints()) {
                if ((boolean) d.get(f)) {
                    right.add(d);
                } else {
                    left.add(d);
                }
            }
            left.calculateGiniImpurity();
            right.calculateGiniImpurity();
            minHeap.insert(left);
            minHeap.insert(right);
            parent.setSplitImpurity(getSplitImpurity(parent, left, right));
            parent.setLeft(left);
            parent.setRight(right);
            minHeap.insert(parent);
        }

        Node bestNode = minHeap.removeMax();
        n.setLeft(bestNode.getLeft());
        n.setRight(bestNode.getRight());
        if (n.getLeft().getGiniImpurity() < n.getGiniImpurity()) {
            buildTree(n.getLeft(), depth + 1);
        } else {
            buildTree(n.getRight(), depth + 1);
        }

    }

    private double getSplitImpurity(Node parent, Node left, Node right) {
        double lWeighted = (double) left.getDataPoints().size() / parent.getDataPoints().size() * left.getGiniImpurity();
        double rWeighted = (double) right.getDataPoints().size() / parent.getDataPoints().size() * right.getGiniImpurity();
        return lWeighted + rWeighted;
    }


}
