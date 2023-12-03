import java.util.ArrayList;

public class Node {

    private final ArrayList<DataRecord> data;

    private DataRecord ratingsDistribution;

    private String splitFeature;

    private double splitImpurity;
    private double giniImpurity;

    private Node left;
    private Node right;

    private String label;

    public Node(ArrayList<DataRecord> data) {
        this.data = data;
    }

    public Node(String feature, ArrayList<String> ratings) {
        this.splitFeature = feature;
        this.data = new ArrayList<>();
        this.ratingsDistribution = new DataRecord();
        for (String r : ratings) {
            ratingsDistribution.put(r, 0);
        }

    }

    public ArrayList<DataRecord> getDataPoints() {
        return data;
    }

    public void add(DataRecord datapoint) {
        String rating = datapoint.rating();
        int ratingTotal = (int) ratingsDistribution.get(rating);
        ratingTotal++;
        ratingsDistribution.put(rating, ratingTotal);
        data.add(datapoint);

    }

    public String getSplitFeature() {
        return this.splitFeature;
    }

    public void setSplitFeature(String f) {
        this.splitFeature = f;
    }

    public void calculateGiniImpurity() {
        double gini = 1, classProbability;
        for (String f : ratingsDistribution.keySet()) {
            classProbability = (double) ratingsDistribution.get(f) / data.size();
            gini -= Math.pow(classProbability, 2);
        }
        giniImpurity = gini;
    }

    public double getSplitImpurity() {
        return splitImpurity;
    }

    public void setSplitImpurity(double impurity) {
        this.splitImpurity = impurity;
    }

    public double getGiniImpurity() {
        return giniImpurity;
    }


    public void setGiniImpurity(double giniImpurity) {
        this.giniImpurity = giniImpurity;
    }


    public Node getLeft() {
        return this.left;
    }

    public void setLeft(Node lNode) {
        this.left = lNode;
    }

    public Node getRight() {
        return this.right;
    }

    public void setRight(Node rNode) {
        this.right = rNode;
    }


    public boolean isLeaf() {
        return (left == null && right == null);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void labelLeaf() {
        //label majority feature in leaf
        String majority = "";
        int greatestCount = 0;
        for (String f : ratingsDistribution.keySet()) {
            int currentCount = (int) ratingsDistribution.get(f);
            if (currentCount > greatestCount) {
                greatestCount = currentCount;
                majority = f;
            }
        }

        label = majority;
    }
}