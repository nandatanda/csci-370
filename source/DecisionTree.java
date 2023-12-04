import java.util.ArrayList;

public class DecisionTree {

    DecisionTree(DataSet boostrappedDataSet, ArrayList<String> baggedFeatures, UserConfig settings) {
        this.bootstrappedDataSet = boostrappedDataSet;
        this.baggedFeatures = baggedFeatures;
        this.MIN_SAMPLES = settings.minSamples();
        this.MAX_DEPTH = settings.maxDepth();
        this.RATINGS_LIST = settings.ratings();
        this.minHeap = new MinHeap();
        this.root = new Node(bootstrappedDataSet);
        minHeap.insert(root);
        buildTree(this.root, 0);

    }

    private final DataSet bootstrappedDataSet;
    private final ArrayList<String> baggedFeatures;
    private final int MIN_SAMPLES;
    private final int MAX_DEPTH;

    private final ArrayList<String> RATINGS_LIST;
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
        if (depth >= MAX_DEPTH || n.data().size() <= MIN_SAMPLES) {
            n.assignLabel();
            // another scenario is when it is a leaf node but doesn't satisfy this criteria
            return;
        }
        Node parent = minHeap.removeMin();
        for (String f : baggedFeatures) {
            Node left = new Node(f);
            Node right = new Node(f);
            for (DataRecord d : n.data()) {
                if ((boolean) d.get(f)) {
                    right.add(d);
                } else {
                    left.add(d);
                }
            }
            left.calculateImpurity();
            right.calculateImpurity();
            parent.setGiniIndex(getSplitImpurity(parent, left, right));
            parent.setLeft(left);
            parent.setRight(right);
            minHeap.insert(parent);
        }

        Node bestNode = minHeap.removeMin();
        n.setLeft(bestNode.left());
        n.setRight(bestNode.right());
        if (n.left().giniImpurity() < n.giniImpurity()) {
            minHeap.insert(bestNode.left());
            //label the leaf because we are no longer splitting on the right side
            n.right().assignLabel();
            buildTree(bestNode.left(), depth + 1);
        } else {
            minHeap.insert(bestNode.right());
            //label the leaf because we are no longer splitting on left side
            n.left().assignLabel();
            buildTree(bestNode.right(), depth + 1);
        }

    }

    private double getSplitImpurity(Node parent, Node left, Node right) {
        double lWeighted = (double) left.data().size() / parent.data().size() * left.giniImpurity();
        double rWeighted = (double) right.data().size() / parent.data().size() * right.giniImpurity();
        return lWeighted + rWeighted;
    }

    public String castVote(DataRecord datapoint){
        // start form the root and loop through and get the majority label if datapoint classifies
        Node currentNode = root;
        while(!currentNode.isLeaf()){
            String feature = currentNode.splittingFeature();
            if ((boolean)datapoint.get(feature)){
                currentNode = currentNode.right();
            }
            else{
                currentNode = currentNode.left();
            }
        }
        return currentNode.label();
    }
}